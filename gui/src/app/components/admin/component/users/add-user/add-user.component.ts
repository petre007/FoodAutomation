import { Component } from '@angular/core';
import { UsersService } from '../../../service/users.service';

@Component({
  selector: 'app-add-user',
  templateUrl: './add-user.component.html',
  styleUrls: ['./add-user.component.css']
})
export class AddUserComponent {

  constructor(private userService: UsersService) {}

  addUser(username: string, password: string, role: string): void {
    this.userService.addUser(username, password, role);
  }
}
