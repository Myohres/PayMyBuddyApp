import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {TransactionBank} from "../model/transactionBank";


@Injectable({
  providedIn:"root"
})
export class TransactionBankService {

  private rootURL: string = "http://localhost:8080/transaction_bank";
  private rootURLPayment: string = "http://localhost:8080/payment_bank";

  private _transaction: TransactionBank = {
    id:0,
    date: new Date(),
    user: {
      id:0,
      email:"",
      password:"",
      lastName:"",
      firstName:"",
      amount:0,
      contactList:[],
      bankList:[]
    },
    bank: {
      id:0,
      accountNumber: "",
      name: "",
      amount:0,
    },
    amountTransaction: 0.00,
    amountCommission : 0.00,
    description:"",
  }

  constructor(private http: HttpClient) {
  }

  public getTransactionsBankByUserId(id: number): Observable<TransactionBank[]> {
    return this.http.get<TransactionBank[]>(`${this.rootURL}/user/id/${id}`)
  }

  public paymentUserToBank(transaction: TransactionBank) {
    return this.http.post(`${this.rootURLPayment}`, transaction)
  }

  getTransaction(): TransactionBank {
    return this._transaction;
  }

  setTransaction(value: TransactionBank) {
    this._transaction = value;
  }

  setSenderIdToTransaction(value: number){
    this._transaction.user.id = value;
  }

  setRecipientIdToTransaction(value: number) {
    this._transaction.bank.id = value;
  }

  setRecipientAccountNumberToTransaction(value: string) {
    this._transaction.bank.accountNumber = value;
  }

  setAmountToTransaction(value: number) {
    this._transaction.amountTransaction = value;
  }

  setDescriptionToTransaction(value: string) {
    this._transaction.description = value;
  }
}
