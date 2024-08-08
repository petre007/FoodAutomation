import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map, Observable } from 'rxjs';
import { environment } from 'src/environments/environment.development';
import { Robot } from '../common/robot';

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

  public getOutputData(id: number): Observable<OutputData> {
    return this.http.post<OutputData>(environment.apiUrl+environment.getOutputData, JSON.stringify(id), this.httpOptions)
  }

  public getRobots(): Observable<Robot[]> {
    return this.http.get<Robot[]>(environment.apiUrl+environment.getRobots, this.httpOptions);
  }

  public updateRobot(robot: Robot): void {
    this.http.put(environment.apiUrl+environment.updateRobot, JSON.stringify(robot), this.httpOptions).subscribe(response=>{

    })
    alert("Update Robot")
    location.reload()
  }
}

interface OutputData {
  [key: string]: number[];
}