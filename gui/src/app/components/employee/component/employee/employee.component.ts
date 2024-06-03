import { Component, OnInit } from '@angular/core';
import { Orders } from '../../common/orders';
import { EmployeeService } from '../../service/employee.service';

@Component({
  selector: 'app-employee',
  templateUrl: './employee.component.html',
  styleUrls: ['./employee.component.css']
})
export class EmployeeComponent implements OnInit{
  
    orders: Orders[] = [];

    constructor(private employeeService: EmployeeService) {}

    ngOnInit(): void {
      this.employeeService.getAllOrders().subscribe(data=> {
        this.orders = data;
      });
    }

}
