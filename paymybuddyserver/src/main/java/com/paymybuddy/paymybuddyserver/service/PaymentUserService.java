package com.paymybuddy.paymybuddyserver.service;

import com.paymybuddy.paymybuddyserver.data.entity.BankEntity;
import com.paymybuddy.paymybuddyserver.data.entity.CommissionEntity;
import com.paymybuddy.paymybuddyserver.data.entity.TransactionUserEntity;
import com.paymybuddy.paymybuddyserver.data.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class PaymentUserService {

    /** User service layer. */
    @Autowired
    private UserService userService;

    /** Bank service layer. */
    @Autowired
    private BankService bankService;

    /** Extern Transaction service layer. */
    @Autowired
    private TransactionUserService transactionUserService;

    /** Commission service layer. */
    @Autowired
    private CommissionService commissionService;

    /**
     * Decrease bank amount to user amount.
     * @param transaction extern transaction
     * @return extern transaction
     */
    public TransactionUserEntity sendMoneyToUser(
            final TransactionUserEntity transaction) {
        CommissionEntity commission = commissionService.getCommission();
        isSuperiorToZero(transaction.getAmountTransaction());
        BankEntity bank = bankService.getBankById(
                transaction.getBank().getId());
        isAmountSenderProvided(transaction.getAmountTransaction(),
                bank.getAmount(), commission.getRate());
        bankService.sendMoney(bank,
                transaction.getAmountTransaction(), commission.getRate());
        UserEntity user = userService.getUserById(
                transaction.getUser().getId());
        userService.receivedMoney(user, transaction.getAmountTransaction());
        transaction.setDate(new Date());
        double commissionAmount = transaction.getAmountTransaction()
                * commission.getRate();
        transaction.setAmountCommission(commissionAmount);
        return transactionUserService.addTransaction(transaction);
    }


    /**
     * Verify if amount is supérior to zero.
     * @param amount sender amount
     */
    public void isSuperiorToZero(final double amount) {
        if (amount <= 0) {
            throw new IllegalStateException(
                    "Cant do transfer with amount not superior to 0 , "
                            + "actual amount : " + amount);
        }
    }

    /**
     * Verify amount transaction is not superior to sender amount.
     * @param transferAmount amount transfer
     * @param senderAmount amount user sender
     * @param commissionRate commission rate
     */
    public void isAmountSenderProvided(final double transferAmount,
                                       final double senderAmount,
                                       final double commissionRate) {
        if (transferAmount + (transferAmount * commissionRate) > senderAmount) {
            throw new IllegalStateException("No enough money in account. "
                    + "Amount transfer : " + transferAmount
                    + " Amount sender account : " + senderAmount);
        }
    }
}
