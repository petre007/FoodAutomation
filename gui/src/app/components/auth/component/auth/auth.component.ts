import { Component } from '@angular/core';
import { AuthService } from '../../service/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-auth',
  templateUrl: './auth.component.html',
  styleUrls: ['./auth.component.css']
})
export class AuthComponent {

  constructor(private authService: AuthService,
    private router: Router
) {}

getLogin(username: string, password: string): void {
  try{
    this.authService.login(username, password).subscribe((authResponse)=> {
      localStorage.setItem("token", authResponse.token)
      localStorage.setItem("roles", authResponse.roles)
      this.router.navigate(["/homePage"])
    })
  } catch (catchError) {
    alert("Username or password incorect")
  }
}

}
