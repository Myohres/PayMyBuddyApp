package com.paymybuddy.paymybuddyserver.service;

import com.paymybuddy.paymybuddyserver.data.entity.TransactionContactEntity;
import com.paymybuddy.paymybuddyserver.data.repository.TransactionContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class TransactionContactService {

    /** Transaction repository layer.*/
    @Autowired
    private TransactionContactRepository transactionContactRepository;


    /**
     * Get all Transactions for an User.
     * @param userId user id
     * @return Transactions List
     */
    public List<TransactionContactEntity> getTransactionByUserId(
            final Long userId) {
        Optional<List<TransactionContactEntity>> optionalList =
                transactionContactRepository.findBySenderId(userId);
        return optionalList.orElseThrow(()
                -> new NoSuchElementException(
                "No transaction with user " + userId));
    }

    /**
     * Get all Transactions for an Contact.
     * @param userId contact id
     * @return Transactions List
     */
    public List<TransactionContactEntity> getTransactionByRecipientId(
            final Long userId) {
        Optional<List<TransactionContactEntity>> optionalList =
                transactionContactRepository.findByContactId(userId);
        return optionalList.orElseThrow(()
                -> new NoSuchElementException(
                "No transaction with contact " + userId));
    }

    /**
     * Add Transaction to DB.
     * @param transactionContactEntity transaction
     * @return Transaction added
     */
    public TransactionContactEntity addTransaction(
            final TransactionContactEntity transactionContactEntity) {
        return transactionContactRepository.save(transactionContactEntity);
    }

    /**
     * Get Transaction user sender and transaction user recipient.
     * @param userId user id
     * @return transactionContactEntity List
     */
    public List<TransactionContactEntity> getContactTransaction(
            final Long userId) {
        List<TransactionContactEntity> tranContactListByUser =
                getTransactionByUserId(userId);
        List<TransactionContactEntity> tranContactListByRecipient =
                getTransactionByRecipientId(userId);
        List<TransactionContactEntity> tranContactSenderAndRecipient =
                new ArrayList<>();
        tranContactSenderAndRecipient.addAll(tranContactListByUser);
        tranContactSenderAndRecipient.addAll(tranContactListByRecipient);
        tranContactSenderAndRecipient.stream().sorted((o1, o2) -> {
            if (o1.getDate().before(o2.getDate())) {
                return 1;
            } else if (o1.getDate().after(o2.getDate())) {
                return -1;
            }
            return 0;
        }).close();
        return tranContactSenderAndRecipient;
    }
}
