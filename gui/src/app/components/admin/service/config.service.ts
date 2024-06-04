import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment.development';
import { Config } from '../common/config';

@Injectable({
  providedIn: 'root'
})
export class ConfigService {

  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type':'application/json',
      'token': localStorage.getItem("token") || ''
    })
  }

  constructor(private http: HttpClient) { }

  public getConfigParams(): Observable<Config[]>{
    return this.http.get<Config[]>(environment.apiUrl+environment.getParams, this.httpOptions)
  }

  public updateParamValue(config: Config): void {
    this.http.put<any>(environment.apiUrl+environment.updateParam, JSON.stringify(config), this.httpOptions).subscribe(response => {
      console.log("Value updated")
    })
}
}
