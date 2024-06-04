import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import {MatCardModule} from '@angular/material/card';
import { MatInputModule } from '@angular/material/input';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HttpClientModule } from '@angular/common/http';
import {MatFormFieldModule} from '@angular/material/form-field';
import { FormsModule } from '@angular/forms';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { AuthComponent } from './components/auth/component/auth/auth.component';
import { EmployeeComponent } from './components/employee/component/employee/employee.component';
import { ClientComponent } from './components/client/component/client/client.component';
import {MatIconModule} from '@angular/material/icon';
import {MatDividerModule} from '@angular/material/divider';
import {MatButtonModule} from '@angular/material/button';
import {MatCheckboxModule} from '@angular/material/checkbox';
import {MatButtonToggleModule} from '@angular/material/button-toggle';
import { AdminComponent } from './components/admin/component/admin/admin.component';
import { NavbarComponent } from './components/admin/component/navbar/navbar.component';
import { ConfigComponent } from './components/admin/component/config/config.component';
import {MatGridListModule} from '@angular/material/grid-list';
import { CanvasJSAngularChartsModule } from '@canvasjs/angular-charts';



@NgModule({
  declarations: [
    AppComponent,
    AuthComponent,
    EmployeeComponent,
    ClientComponent,
    AdminComponent,
    NavbarComponent,
    ConfigComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    BrowserAnimationsModule,
    FormsModule,
    MatFormFieldModule,
    MatCardModule,
    MatInputModule,
    MatButtonModule, 
    MatDividerModule, 
    MatIconModule,
    MatCheckboxModule,
    MatButtonToggleModule,
    MatGridListModule,
    CanvasJSAngularChartsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
