import { Component, OnInit } from '@angular/core';
import { Foods } from '../../common/foods';
import { ClientService } from '../../service/client.service';

@Component({
  selector: 'app-client',
  templateUrl: './client.component.html',
  styleUrls: ['./client.component.css']
})
export class ClientComponent implements OnInit{

  foods: Foods[] = []
  checked: boolean[] = []

  constructor(private clientService: ClientService) {}

  ngOnInit(): void {
    this.clientService.getAllFoods().subscribe(data=>{
      this.foods = data;
    })
  }

}
