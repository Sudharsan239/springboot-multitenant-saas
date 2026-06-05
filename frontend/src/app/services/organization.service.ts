import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, map } from 'rxjs';

import { environment } from '../../environments/environment';
import { ApiMessage } from '../models/api-message';
import { Organization } from '../models/organization';
import { Page } from '../models/page';

@Injectable({ providedIn: 'root' })
export class OrganizationService {
  private readonly http = inject(HttpClient);
  private readonly baseUrl = `${environment.apiUrl}/organizations`;

  getAll(): Observable<Organization[]> {
    return this.http
      .get<Page<Organization>>(this.baseUrl, { params: { size: '100' } })
      .pipe(map((page) => page.content));
  }

  create(name: string): Observable<Organization> {
    return this.http.post<Organization>(this.baseUrl, { name });
  }

  delete(id: number): Observable<ApiMessage> {
    return this.http.delete<ApiMessage>(`${this.baseUrl}/${id}`);
  }
}
