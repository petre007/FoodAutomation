import { Component, OnInit } from '@angular/core';
import { UserInfo } from '../../../common/user-info';
import { UsersService } from '../../../service/users.service';

@Component({
  selector: 'app-user-form',
  templateUrl: './user-form.component.html',
  styleUrls: ['./user-form.component.css']
})
export class UserFormComponent implements OnInit {

  usersInfo: UserInfo[] = []

  constructor(private userService: UsersService,
  ) {}
  
  ngOnInit(): void {
    this.userService.getAll().subscribe(data=>{
      this.usersInfo = data
      console.log(data)
    })  
  }

  deleteUser(id: number | undefined) {
    console.log(id)
    this.userService.deleteUser(id);
  }



}
