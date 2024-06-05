import { Component, OnInit } from '@angular/core';
import { Orders } from '../../common/orders';
import { EmployeeService } from '../../service/employee.service';
import { DeliveryService } from 'src/app/service/delivery.service';

@Component({
  selector: 'app-employee',
  templateUrl: './employee.component.html',
  styleUrls: ['./employee.component.css']
})
export class EmployeeComponent implements OnInit{
  
    orders: Orders[] = [];

    constructor(private employeeService: EmployeeService, 
      private deliveryService: DeliveryService) {}

    ngOnInit(): void {
      this.employeeService.getAllOrders().subscribe(data=> {
        this.orders = data;
      });
    }

    onStateChange(order: Orders, newState: string) {
      this.deliveryService.updateDeliveryState(order, newState);
      alert("Delivery status updated")
      location.reload()
      // Add additional logic if needed
    }

}
