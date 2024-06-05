import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { UserInfo } from '../common/user-info';
import { environment } from 'src/environments/environment.development';

@Injectable({
  providedIn: 'root'
})
export class UsersService {

  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type':'application/json',
      'token': localStorage.getItem("token") || ''
    })
  }

  constructor(private http: HttpClient) { }

  public getAll(): Observable<UserInfo[]> {
    return this.http.get<UserInfo[]>(environment.apiUrl+environment.getAllUsers, this.httpOptions);
  }

  public addUser(username: string, password: string, role: string): void {
    const addUserBody = {
      username: username,
      password: password,
      role: role
    }
    this.http
    .post(environment.apiUrl+environment.addUser, JSON.stringify(addUserBody), this.httpOptions)
    .subscribe(response=> {
      
    });
    location.reload()
    alert("User added")
  }

  public deleteUser(id:number | undefined): void {
    const httpOptionsDelete = {
      headers: new HttpHeaders({
        'Content-Type':'application/json',
        'token': localStorage.getItem("token") || '',
      }),
      body: id
    }
    this.http.delete(environment.apiUrl+environment.deleteUser, httpOptionsDelete).subscribe(response=>{

    });
    alert("User deleted")
  }
}
