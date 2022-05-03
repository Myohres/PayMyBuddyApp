import {User} from "./user";

export interface TransactionContact {
  id: number;
  date: Date;
  sender: User;
  contact: User;
  amountTransaction : number;
  amountCommission: number;
  description : string
}
