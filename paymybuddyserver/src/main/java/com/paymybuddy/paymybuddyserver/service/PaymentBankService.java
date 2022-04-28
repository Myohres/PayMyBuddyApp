package com.paymybuddy.paymybuddyserver.service;

import com.paymybuddy.paymybuddyserver.data.entity.BankEntity;
import com.paymybuddy.paymybuddyserver.data.entity.CommissionEntity;
import com.paymybuddy.paymybuddyserver.data.entity.TransactionBankEntity;
import com.paymybuddy.paymybuddyserver.data.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class PaymentBankService {

    /** User service layer. */
    @Autowired
    private UserService userService;

    /** Bank service layer. */
    @Autowired
    private BankService bankService;

    /** Extern Transaction service layer. */
    @Autowired
    private TransactionBankService transactionBankService;

    /** Commission service layer. */
    @Autowired
    private CommissionService commissionService;

    /**
     * Decrease user amount to bank amount.
     * @param transaction extern transaction
     * @return extern transaction
     */
    public TransactionBankEntity sendMoneyToBank(
            final TransactionBankEntity transaction) {
        CommissionEntity commission = commissionService.getCommission();
        isSuperiorToZero(transaction.getAmountTransaction());
        UserEntity user = userService.getUserById(
                transaction.getUser().getId());
        isAmountSenderProvided(transaction.getAmountTransaction(),
                user.getAmount(), commission.getRate());
        userService.sendMoney(user,
                transaction.getAmountTransaction(), commission.getRate());
        BankEntity bank = bankService.getBankById(
                transaction.getBank().getId());
        bankService.receivedMoney(bank, transaction.getAmountTransaction());
        transaction.setDate(new Date());
        double commissionAmount = transaction.getAmountTransaction()
                * commission.getRate();
        transaction.setAmountCommission(commissionAmount);
        return transactionBankService.addTransaction(transaction);
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
                    + " Amount account : " + senderAmount);
        }
    }
}

