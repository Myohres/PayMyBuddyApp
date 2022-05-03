import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {TransactionUser} from "../model/transactionUser";


@Injectable({
  providedIn:"root"
})
export class TransactionUserService {


  private rootURL: string = "http://localhost:8080/transaction_user";
  private rootURLPayment: string = "http://localhost:8080/payment_user";

  _transaction: TransactionUser = {
    id:0,
    date : new Date(),
    bank: {
      id:0,
      accountNumber:"",
      name: "",
      amount:0,
    },
    user:{
      id:0,
      email:"",
      password:"",
      lastName:"",
      firstName:"",
      amount:0,
      contactList:[],
      bankList:[]
    },
    amountTransaction: 0.00,
    amountCommission: 0.00,
    description:"",
  }

  constructor(private http: HttpClient) {
  }

  public getTransactionsContactByUserId(id: number): Observable<TransactionUser[]> {
    return this.http.get<TransactionUser[]>(`${this.rootURL}/user/id/${id}`)
  }

  public paymentUserToBank(transaction: TransactionUser) {
    return this.http.post(`${this.rootURLPayment}`, transaction)
  }

  getTransaction(): TransactionUser {
    return this._transaction;
  }

  setTransaction(value: TransactionUser) {
    this._transaction = value;
  }

  setSenderIdToTransaction(value: number){
    this._transaction.bank.id = value;
  }

  setRecipientIdToTransaction(value: number) {
    this._transaction.user.id = value;
  }

  setAmountToTransaction(value: number) {
    this._transaction.amountTransaction = value;
  }

  setDescriptionToTransaction(value: string) {
    this._transaction.description = value;
  }
}
