import { Component, OnInit } from '@angular/core';
import {UserService} from "../service/user.service";
import {Router} from "@angular/router";
import {FormControl} from "@angular/forms";
import {User} from "../model/user";

@Component({
  selector: 'app-authentication',
  templateUrl: './authentication.component.html',
  styleUrls: ['./authentication.component.css']
})
export class AuthenticationComponent implements OnInit {

  constructor(private userService: UserService) {
  }

  link: string = '';
  login: FormControl = new FormControl();
  password: FormControl = new FormControl();
  message: string = '';

  ngOnInit(): void {
  }

  authentication() {
    if (this.loginNotNull()){
      this.userService.loginTaken(this.login.value).subscribe({
        next: value => {
          console.log("login taken : " +value);
          if (value) {
            this.userService.login(this.login.value, this.password.value);
            this.login.reset();
            this.password.reset();
          }
          else {
            this.message = "login or password not good. Please try again"
            this.login.reset();
            this.password.reset();
          }
        },
        error: err => console.error("error" +err),
        complete:() => console.log("fin de l'authentication")
      })
    }

  }

  loginNotNull(): boolean {
    if (this.login.value != null && this.password.value != null) {
      return true;
    } else {
      this.message = "email or password cant be null";
      return false;
    }
  }
}
