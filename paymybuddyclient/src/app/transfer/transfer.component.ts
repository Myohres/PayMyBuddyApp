import { Component, OnInit } from '@angular/core';
import {Transaction} from "../model/transaction";
import {TransactionBank} from "../model/transactionBank";
import {TransactionContact} from "../model/transactionContact";
import {TransactionUser} from "../model/transactionUser";
import {Contact} from "../model/contact";
import {Bank} from "../model/bank";
import {FormControl} from "@angular/forms";
import {UserService} from "../service/user.service";
import {BankService} from "../service/bank.service";
import {TransactionService} from "../service/transaction.service";
import {TransactionBankService} from "../service/transactionBank.service";
import {TransactionContactService} from "../service/transactionContact.service";
import {TransactionUserService} from "../service/transactionUser.service";

@Component({
  selector: 'app-transfer',
  templateUrl: './transfer.component.html',
  styleUrls: ['./transfer.component.css']
})
export class TransferComponent implements OnInit {

  public transactionData: Transaction[] = [];
  public transactionBankData: TransactionBank[] = [];
  public transactionContactData: TransactionContact[] = [];
  public transactionUserData: TransactionUser[] = [];
  public contactData: Contact[] = [];
  public bankData: Bank[] = [];

  public contactSelected: number = 0;
  public userLogin: number = this.userService.getUserId();
  public userAmount: number = 0;

  public ContactSelectedValue: string = "";
  public toBankSelectedValue: string = "";
  public fromBankSelectedValue: string = "";

  public amountContact  = new FormControl();
  public descriptionContact  = new FormControl();
  public amountBank = new FormControl();
  public descriptionBank = new FormControl();
  public descriptionUser = new FormControl();
  public amountUser = new FormControl();

  constructor(private userService: UserService,
              private bankService: BankService,
              private transactionService: TransactionService,
              private transactionBankService: TransactionBankService,
              private transactionContactService: TransactionContactService,
              private transactionUserService: TransactionUserService) {}

  ngOnInit(): void {
    this.getTransactions()
    this.getTransactionsBank();
    this.getTransactionsContact();
    this.getTransactionUser()
    this.getAmount();
    this.getContacts();
  }

  getTransactions(){
    this.transactionService.getTransactionsByUserId(this.userLogin)
      .subscribe(response => {
        this.transactionData = response;
      })
  }

  getTransactionsBank() {
    this.transactionBankService.getTransactionsBankByUserId(this.userLogin)
      .subscribe(response => {
        this.transactionBankData = response;
      })
  }

  getTransactionsContact() {
    this.transactionContactService.getTransactionContactById(this.userLogin)
      .subscribe(response => {
        this.transactionContactData = response;
      });
  }

  getTransactionUser() {
    this.transactionUserService.getTransactionsContactByUserId(this.userLogin)
      .subscribe( response => {
        this.transactionUserData = response;
      })
  }

  getAmount() {
    this.userService.getUserById(this.userLogin)
      .subscribe(response => {
        this.userAmount = response.amount;
      })
  }

  getContacts() {
    this.userService.getUserById(this.userLogin).subscribe(response => {
      this.contactData = response.contactList;
      this.bankData = response.bankList;
    })
  }

  resetAmountAndDescription() {
    this.amountBank.reset();
    this.descriptionBank.reset();
    this.amountContact.reset();
    this.descriptionContact.reset();
    this.amountUser.reset();
    this.descriptionUser.reset();
  }

  PaymentToBank() {
    this.transactionBankService.setSenderIdToTransaction(this.userLogin);
    this.transactionBankService.setRecipientIdToTransaction(Number(this.toBankSelectedValue));
    this.transactionBankService.setAmountToTransaction(this.amountBank.value);
    this.transactionBankService.setDescriptionToTransaction(this.descriptionBank.value);
    this.transactionBankService.paymentUserToBank(this.transactionBankService.getTransaction())
      .subscribe({
        next: value => {
          console.log("add ext_transaction")
          this.getTransactionsBank();
          this.getTransactions();
          this.getAmount();
        },
        error: err => console.log("add ext_transaction error : ", err),
        complete: () => console.log("ext_transaction done")
      })
    this.resetAmountAndDescription();
  }

  PaymentToContact() {
    this.transactionContactService.setSenderIdToTransaction(this.userLogin);
    this.transactionContactService.setAmountToTransaction(this.amountContact.value);
    this.transactionContactService.setDescriptionToTransaction(this.descriptionContact.value) ;
    this.transactionContactService.setRecipientIdToTransaction(Number(this.ContactSelectedValue));
    this.transactionContactService.paymentUserToContact(this.transactionContactService.getTransaction())
      .subscribe({
        next: value => {
          console.log("add transation")
          this.getTransactionsContact();
          this.getTransactions()
          this.getAmount();
        },
        error: err => console.log("add transaction error : ", err),
        complete: () => console.log("transaction done")
      })
    this.resetAmountAndDescription();
  }

  PaymentToUser() {
    this.transactionUserService.setSenderIdToTransaction(Number(this.fromBankSelectedValue))
    this.transactionUserService.setRecipientIdToTransaction(this.userLogin);
    this.transactionUserService.setAmountToTransaction(this.amountUser.value);
    this.transactionUserService.setDescriptionToTransaction(this.descriptionUser.value);
    this.transactionUserService.paymentUserToBank(this.transactionUserService.getTransaction())
      .subscribe({
        next: value => {
          console.log("add ext_transaction")
          this.getTransactionUser();
          this.getTransactions();
          this.getAmount();
        },
        error: err => console.log("add ext_transaction error : ", err),
        complete: () => console.log("ext_transaction done")
      })
    this.resetAmountAndDescription();
  }
}
