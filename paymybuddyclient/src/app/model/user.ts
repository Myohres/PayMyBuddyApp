import {Contact} from "./contact";
import {Bank} from "./bank";

export interface User {
  id: number;
  email: string;
  password: string;
  lastName: string;
  firstName: string;
  amount: number;
  contactList: Contact[];
  bankList: Bank[];
}
