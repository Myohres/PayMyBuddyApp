import { Component, OnInit } from '@angular/core';
import {User} from "../model/user";
import {FormControl} from "@angular/forms";
import {UserService} from "../service/user.service";


@Component({
  selector: 'app-subscription',
  templateUrl: './subscription.component.html',
  styleUrls: ['./subscription.component.css']
})
export class SubscriptionComponent implements OnInit {

  constructor(private userService: UserService) { }

  firstname: FormControl = new FormControl();
  lastname: FormControl = new FormControl();
  email: FormControl = new FormControl();
  password: FormControl = new FormControl();
  message: string = '';

  userTmp: User = {
    id:0,
    email: "",
    password: "",
    lastName: "",
    firstName: "",
    amount: 0,
    contactList: [],
    bankList: [],
  };

  ngOnInit(): void {

  }

  public loginNotNull(): boolean{
    if (this.email.value != null && this.password.value != null) {
      return true;
    } else {
      this.message = "fields cant be null";
      return false;
    }
  }

  public subscription() {
    this.message = "";
    console.log("login not null : " +this.loginNotNull())
    if (this.loginNotNull()){
      this.userTmp.email = this.email.value;
      this.userTmp.password = this.password.value;
      this.userTmp.lastName = this.lastname.value;
      this.userTmp.firstName = this.firstname.value;
      console.log(this.userTmp.email);
      console.log(this.userTmp.password)
      this.register(this.email.value, this.userTmp);
      this.message = this.userService.getRegisterState();
    }
    this.email.reset();
    this.password.reset();
    this.lastname.reset();
    this.firstname.reset();
  }

  public register(email: string, user: User){
    this.message = "";
    this.userService.loginTaken(email).subscribe({
      next: value => {
        console.log("login taken : " +value);
        if (!value){
          console.log("adding user ");
          this.userService.addUser(user).subscribe({
            complete:() => {
              console.log("user added")
              this.message = "user added";
            }
          });
        } else {
          this.message= "email not free";
        }},
      error: err => console.error("error" +err),
      complete:() => console.log("fin de registration")
    });
  }
}
