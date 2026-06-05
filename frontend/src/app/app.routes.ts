import { Routes } from '@angular/router';

import { OrganizationsComponent } from './organizations/organizations.component';

export const routes: Routes = [
  { path: '', component: OrganizationsComponent },
  { path: 'organizations', component: OrganizationsComponent },
  { path: '**', redirectTo: '' }
];
