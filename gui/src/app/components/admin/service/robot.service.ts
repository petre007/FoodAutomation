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
    return this.http.post<number[]>(environment.apiUrl+environment.getUltrasonicDataFromRobot, JSON.stringify(id), this.httpOptions)
  }

  public startRlModelTraining(): void {
    this.http.get(environment.apiUrl+environment.startRlModelTraining, this.httpOptions).subscribe(response=>{

    })
    alert("Begin training model initialized")
  }
}
