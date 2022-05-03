import { Component, OnInit } from '@angular/core';
import {Contact} from "../model/contact";
import {Bank} from "../model/bank";
import {FormControl} from "@angular/forms";
import {UserService} from "../service/user.service";
import {BankService} from "../service/bank.service";

@Component({
  selector: 'app-contact',
  templateUrl: './contact.component.html',
  styleUrls: ['./contact.component.css']
})
export class ContactComponent implements OnInit {

  public contactData: Contact[] = [];
  public bankData: Bank[] = [];

  public userLogin: number = 0;
  public contactEmail = new FormControl();
  public bankName = new FormControl();
  public bankAccountNumber = new FormControl();

  public isError: boolean = false;

  constructor(private userService: UserService,
              private bankService: BankService) {
  }

  ngOnInit(): void {
    this.userLogin = this.userService.getUserId();
    this.getContactsAndBanks();
  }

  addContact() {
    this.userService.getUserByEmail(this.contactEmail.value)
      .subscribe({
        next: data => this.userService.addContact(this.userLogin,data)
          .subscribe({
            next: data => {
              this.getContactsAndBanks()
              this.resetFormControl()
            },
            error: err => {console.error(+err)},
            complete: () => console.log("complete")
          }),
        error: err => {
          console.error(+err)
          this.isError = true;
          this.resetFormControl()
        },
        complete:() => {
          console.log("complete")
        },
      })
  }

  deleteContact() {
    this.userService.deleteContact(this.userLogin, this.contactEmail.value)
      .subscribe({
        next: data => {
          this.getContactsAndBanks()
          this.resetFormControl()
        },
        error: err => {
          console.error(+err)
          this.isError = true;
          this.resetFormControl()
        },
        complete: () => console.log("complete")
      })
  }

  resetFormControl() {
    this.bankAccountNumber.reset();
    this.bankName.reset();
    this.contactEmail.reset();
  }

  getContactsAndBanks() {
    this.userService.getUserById(this.userLogin)
      .subscribe({
        next: data => {
          this.contactData = data.contactList
          this.bankData = data.bankList
        },
        error: err => console.error(+err),
        complete: () => console.log("get connections done")
      })
  }

  addBank() {
    this.bankService.setBankName(this.bankName.value);
    this.bankService.setBankAccountNumber(this.bankAccountNumber.value);
    this.bankService.setBankAmount(5000);
    this.userService.addBank(this.userLogin,this.bankService.bank)
      .subscribe({
        next: data => {
          this.bankData = data.bankList
          this.getContactsAndBanks()
          this.resetFormControl()
        },
        error: err => {
          console.error(+err)
          this.isError = true;
          this.resetFormControl()
        },
        complete: () => console.log("get connections done")
      })
  }

  deleteBank() {
    this.userService.deleteBank(this.userLogin,this.bankAccountNumber.value)
      .subscribe({
        next: data => {
          this.getContactsAndBanks();
          this.resetFormControl()
        },
        error: err => {
          console.error(+err)
          this.isError = true;
          this.resetFormControl()
        },
        complete: () => console.log("complete"),
      })
  }

}
