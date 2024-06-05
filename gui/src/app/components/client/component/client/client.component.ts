import { Component, OnInit } from '@angular/core';
import { Foods } from '../../common/foods';
import { ClientService } from '../../service/client.service';
import { DeliveryService } from 'src/app/service/delivery.service';
import { Orders } from 'src/app/components/employee/common/orders';

@Component({
  selector: 'app-client',
  templateUrl: './client.component.html',
  styleUrls: ['./client.component.css']
})
export class ClientComponent implements OnInit{

  foods: Foods[] = []
  selectedFoods: Foods[] = []

  constructor(private clientService: ClientService,
              private deliveryService: DeliveryService) {}

  ngOnInit(): void {
    this.clientService.getAllFoods().subscribe(data=>{
      this.foods = data;
    })
  }

  onCheckboxChange(food: Foods, isChecked: boolean) {
    if (isChecked) {
      this.selectedFoods.push(food);
    } else {
      const index = this.selectedFoods.findIndex(item => item.name === food.name);
      if (index > -1) {
        this.selectedFoods.splice(index, 1);
      }
    }
  }

  createOrder() {
    if (this.selectedFoods.length == 0) {
      alert("No food added to the order");
    } else {
      let order: Orders = {
        id: null,
        roomEntity: null,
        foodModelSet: this.selectedFoods,
        states: null
      }
      this.deliveryService.updateDeliveryState(order, "PENDING")
      alert("Order created")
    }
  }

}
