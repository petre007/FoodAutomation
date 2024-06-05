import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment.development';

@Injectable({
  providedIn: 'root'
})
export class RobotService {

  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type':'application/json',
      'token': localStorage.getItem("token") || ''
    })
  }

  constructor(private http: HttpClient) { }

  public getUltrasonicData(id: number): Observable<number[]>{
    const reqBody = {
      id: id
    }
    return this.http.post<number[]>(environment.apiUrl+environment.getUltrasonicDataFromRobot, JSON.stringify(reqBody), this.httpOptions)
  }
}
