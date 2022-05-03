import {User} from "./user";
import {Bank} from "./bank";

export interface TransactionBank {
  id: number;
  date: Date;
  user: User;
  bank: Bank;
  amountTransaction: number;
  amountCommission: number;
  description : string
}
