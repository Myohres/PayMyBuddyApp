import { Component, OnInit } from '@angular/core';
import {UserService} from "../service/user.service";

@Component({
  selector: 'app-navigation',
  templateUrl: './navigation.component.html',
  styleUrls: ['./navigation.component.css']
})
export class NavigationComponent implements OnInit {

  public userId: number = 0;
  public userLastName: string = "";
  public userFirstName: string = "";

  constructor(private userService: UserService) {
  }

  ngOnInit() {
    this.userId = this.userService.getUserId();
    this.userLastName = this.userService.getUserLastName();
    this.userFirstName = this.userService.getUserFirstName();
  }
}
