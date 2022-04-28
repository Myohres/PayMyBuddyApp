package com.paymybuddy.paymybuddyserver.service;

import com.paymybuddy.paymybuddyserver.data.entity.TransactionUserEntity;
import com.paymybuddy.paymybuddyserver.data.repository.TransactionUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class TransactionUserService {
    /** Extern transaction repository layer. */
    @Autowired
    private TransactionUserRepository transactionUserRepository;


    /**
     * Get transaction by user id.
     * @param userId user id
     * @return transaction
     */
    public List<TransactionUserEntity> getTransactionByUserId(
            final Long userId) {
        Optional<List<TransactionUserEntity>> optionalList =
                transactionUserRepository.findByUserId(userId);
        return optionalList.orElseThrow(()
                -> new NoSuchElementException(
                "No transaction with user " + userId));
    }

    /**
     * Add transaction in DB.
     * @param transactionUserEntity transaction
     * @return new transaction in DB
     */
    public TransactionUserEntity addTransaction(
            final TransactionUserEntity transactionUserEntity) {
        return transactionUserRepository.save(transactionUserEntity);
    }

}
