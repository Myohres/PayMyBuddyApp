import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {TransactionContact} from "../model/transactionContact";
import {User} from "../model/user";


@Injectable({
  providedIn:"root"
})
export class TransactionContactService {

  private rootURL: string = "http://localhost:8080/transaction_contact";
  private rootURLPayment: string = "http://localhost:8080/payment_contact";

  private _transaction: TransactionContact = {
    id:0,
    date: new Date(),
    sender:{
      id:0,
      email:"",
      password:"",
      lastName:"",
      firstName:"",
      amount:0,
      contactList:[],
      bankList:[]
    },
    contact: {
      id:0,
      email:"",
      password:"",
      lastName:"",
      firstName:"",
      amount:0,
      contactList:[],
      bankList: []
    },
    amountTransaction: 0.00,
    amountCommission : 0.00,
    description:"",
  }


  constructor(private http: HttpClient) {
  }

  public getTransactionContactById(id: number): Observable<TransactionContact[]> {
    return this.http.get<TransactionContact[]>(`${this.rootURL}/id/${id}`)
  }

  public paymentUserToContact(Transaction: TransactionContact) {
    return this.http.post(this.rootURLPayment, Transaction)
  }

  getTransaction(): TransactionContact {
    return this._transaction;
  }

  setTransaction(value: TransactionContact) {
    this._transaction = value;
  }

  setSenderIdToTransaction(value: number){
    this._transaction.sender.id = value;
  }

  setRecipientIdToTransaction(value: number) {
    this._transaction.contact.id = value;
  }

  setAmountToTransaction(value: number) {
    this._transaction.amountTransaction = value;
  }

  setDescriptionToTransaction(value: string) {
    this._transaction.description = value;
  }
}
