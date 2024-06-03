import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Orders } from '../common/orders';
import { environment } from 'src/environments/environment.development';

@Injectable({
  providedIn: 'root'
})
export class EmployeeService {

  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type':'application/json',
      'token': localStorage.getItem("token") || ''
    })
  }

  constructor(private http: HttpClient) { }

  public getAllOrders(): Observable<Orders[]> {
    return this.http.get<Orders[]>(environment.apiUrl+environment.getAllOrders, this.httpOptions);
  }

}
