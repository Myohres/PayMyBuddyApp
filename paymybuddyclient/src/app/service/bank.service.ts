import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Router} from "@angular/router";
import {Bank} from "../model/bank";


@Injectable({
  providedIn:"root"
})
export class BankService {


  private rootURL: string = "http://localhost:8080/bank/";

  private _bank: Bank = {
    id: 0,
    accountNumber: "0",
    name: "",
    amount: 0,
  }

  constructor( private http: HttpClient, private router: Router){
  }

  public getBankById(id: number): Observable<Bank> {
    return this.http.get<Bank>(this.rootURL+"id/"+id);
  }

  public geBankByAccount(account: string): Observable<any> {
    return this.http.get<Bank>(`${this.rootURL}account/${account}`)
  }

  public addBank(bank: Bank): Observable<Bank> {
    return  this.http.post<Bank>(this.rootURL, bank)
  }

  public deleteBank(bank: Bank){
    return this.http.delete(this.rootURL)
  }

  get bank(): Bank {
    return this._bank;
  }

  set bank(value: Bank) {
    this._bank = value;
  }

  getBankAccountNumber(): string {
    return this._bank.accountNumber;
  }

  setBankAccountNumber(value: string) {
    this._bank.accountNumber = value;
  }

  getBankName(): string {
    return this._bank.name;
  }

  setBankName(value: string) {
    this._bank.name = value;
  }

  getBankAmount(): number {
    return this._bank.amount;
  }

  setBankAmount(value: number) {
    this._bank.amount = value;
  }
}
