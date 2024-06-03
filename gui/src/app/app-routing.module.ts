import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthComponent } from './components/auth/component/auth/auth.component';
import { AdminComponent } from './components/admin/admin/admin.component';
import { ClientComponent } from './components/client/component/client/client.component';
import { EmployeeComponent } from './components/employee/component/employee/employee.component';

const routes: Routes = [
  {path: 'auth', component:AuthComponent},
  {path: 'admin', component:AdminComponent},
  {path: 'client', component:ClientComponent},
  {path: 'employee', component:EmployeeComponent},
];


@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
