import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Transaction} from "../model/transaction";


@Injectable({
  providedIn:"root"
})
export class TransactionService {

  private rootURL: string = "http://localhost:8080/transaction";

  constructor(private http: HttpClient) {
  }

  public getTransactionsByUserId(userId: number): Observable<Transaction[]> {
    return this.http.get<Transaction[]>(`${this.rootURL}/user/id/${userId}`);
  }
}
