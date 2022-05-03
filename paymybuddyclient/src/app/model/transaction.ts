export interface Transaction {
  date: Date;
  senderName1: string;
  senderName2: string;
  recipientName1: string;
  recipientName2: string;
  amountTransaction: number;
  amountCommission: number;
  description: string;
}
