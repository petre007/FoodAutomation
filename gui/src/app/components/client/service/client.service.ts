import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Foods } from '../common/foods';
import { environment } from 'src/environments/environment.development';

@Injectable({
  providedIn: 'root'
})
export class ClientService {

  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type':'application/json',
      'token': localStorage.getItem("token") || ''
    })
  }

  constructor(private http: HttpClient) { }

  public getAllFoods(): Observable<Foods[]>{
    return this.http.get<Foods[]>(environment.apiUrl+environment.getAllFoods, this.httpOptions);
  }
}
