import {User} from "./user";
import {Bank} from "./bank";

export interface TransactionUser {
  id: number;
  date: Date;
  bank: Bank;
  user: User;
  amountTransaction : number;
  amountCommission : number;
  description : string
}
