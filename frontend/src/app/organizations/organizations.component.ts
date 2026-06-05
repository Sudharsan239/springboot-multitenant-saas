import { Component, OnInit, inject } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { DatePipe } from '@angular/common';
import { HttpErrorResponse } from '@angular/common/http';

import { ErrorDetails } from '../models/error-details';
import { Organization } from '../models/organization';
import { OrganizationService } from '../services/organization.service';

@Component({
  selector: 'app-organizations',
  imports: [ReactiveFormsModule, DatePipe],
  templateUrl: './organizations.component.html',
  styleUrl: './organizations.component.css'
})
export class OrganizationsComponent implements OnInit {
  private readonly organizationService = inject(OrganizationService);
  private readonly formBuilder = inject(FormBuilder);

  organizations: Organization[] = [];
  loading = false;
  submitting = false;
  errorMessage = '';
  successMessage = '';

  form = this.formBuilder.group({
    name: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(100)]]
  });

  ngOnInit(): void {
    this.loadOrganizations();
  }

  loadOrganizations(): void {
    this.loading = true;
    this.errorMessage = '';

    this.organizationService.getAll().subscribe({
      next: (organizations) => {
        this.organizations = organizations;
        this.loading = false;
      },
      error: () => {
        this.errorMessage = 'Could not load organizations. Is the Spring Boot API running on port 8080?';
        this.loading = false;
      }
    });
  }

  onSubmit(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    const name = this.form.value.name?.trim();
    if (!name) {
      return;
    }

    this.submitting = true;
    this.errorMessage = '';

    this.organizationService.create(name).subscribe({
      next: (created) => {
        this.organizations = [created, ...this.organizations];
        this.form.reset();
        this.submitting = false;
      },
      error: () => {
        this.errorMessage = 'Could not create organization. Check the name (3–100 characters) and try again.';
        this.submitting = false;
      }
    });
  }

  onDelete(id: number): void {
    this.errorMessage = '';
    this.successMessage = '';

    this.organizationService.delete(id).subscribe({
      next: (response) => {
        this.organizations = this.organizations.filter((org) => org.id !== id);
        this.successMessage = response.message;
      },
      error: (error: HttpErrorResponse) => {
        this.errorMessage = this.getApiErrorMessage(error, 'Could not delete organization.');
      }
    });
  }

  private getApiErrorMessage(error: HttpErrorResponse, fallback: string): string {
    const body = error.error as ErrorDetails | null;
    return body?.message ?? fallback;
  }
}
