package com.paymybuddy.paymybuddyserver.service;

import com.paymybuddy.paymybuddyserver.data.entity.CommissionEntity;
import com.paymybuddy.paymybuddyserver.data.entity.TransactionContactEntity;
import com.paymybuddy.paymybuddyserver.data.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class PaymentContactService {

    /** User service Layer. */
    @Autowired
    private UserService userService;

    /** Transaction service layer. */
    @Autowired
    private TransactionContactService transactionContactService;

    /** Commission service layer. */
    @Autowired
    private CommissionService commissionService;

    /**
     * Payment between an user and his contact.
     * @param tranContactEntity transaction
     * @return transaction
     */
    public TransactionContactEntity sendMoneyToContact(
            final TransactionContactEntity tranContactEntity) {
        CommissionEntity commission = commissionService.getCommission();
        isSuperiorToZero(tranContactEntity.getAmountTransaction());
        UserEntity sender = userService.getUserById(
                tranContactEntity.getSender().getId());
        isAmountSenderProvided(tranContactEntity.getAmountTransaction(),
                sender.getAmount(), commission.getRate());
        userService.sendMoney(sender,
                tranContactEntity.getAmountTransaction(), commission.getRate());
        commissionService.sendCommissionTransaction(
                commission, tranContactEntity.getAmountTransaction());
        UserEntity recipient = userService.getUserById(
                tranContactEntity.getContact().getId());
        userService.receivedMoney(recipient,
                tranContactEntity.getAmountTransaction());
        tranContactEntity.setSender(sender);
        tranContactEntity.setContact(recipient);
        tranContactEntity.setDate(new Date());
        double commissionAmount = tranContactEntity.getAmountTransaction()
                * commission.getRate();
        tranContactEntity.setAmountCommission(commissionAmount);
        return transactionContactService.addTransaction(tranContactEntity);
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
    public void isAmountSenderProvided(
            final double transferAmount,
            final double senderAmount,
            final double commissionRate) {
        if (transferAmount + (transferAmount * commissionRate) > senderAmount) {
            throw new IllegalStateException("No enough money in account. "
                    + "Amount transfer : " + transferAmount
                    + " Amount account : " + senderAmount);
        }
    }
}

