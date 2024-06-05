import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Rooms } from '../components/admin/common/rooms';
import { Foods } from '../components/client/common/foods';
import { environment } from 'src/environments/environment.development';
import { Orders } from '../components/employee/common/orders';

@Injectable({
  providedIn: 'root'
})
export class DeliveryService {

  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type':'application/json',
      'token': localStorage.getItem("token") || ''
    })
  }

  constructor(private http: HttpClient) { }

  public updateDeliveryState(order: Orders, states: string): void {
    const reqBody = {
      orderEntity: {
        id: order.id,
        roomEntity: order.roomEntity,
        foodModelSet: order.foodModelSet,
        states: order.states
      },
      states: states
    }
    this.http
    .post(environment.apiUrl+environment.updateDeliveryState, JSON.stringify(reqBody), this.httpOptions)
    .subscribe(response=> {
        
    });
  }

}