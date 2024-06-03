import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment.development';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type':'application/json'
    })
  }

  constructor(private http: HttpClient) { }

  public login(username: string, password: string): Observable<UserResponse> {
    const loginRequest= {
      username: username,
      password: password
    }
    return this.http.post<UserResponse>(environment.apiUrl+environment.loginEnpoint, JSON.stringify(loginRequest), this.httpOptions)
  }

  
}

export interface UserResponse {
  token: string;
  roles: string;
}