package com.paymybuddy.paymybuddyserver.service;

import com.paymybuddy.paymybuddyserver.data.entity.TransactionBankEntity;
import com.paymybuddy.paymybuddyserver.data.repository.TransactionBankRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class TransactionBankService {
    /** Extern transaction repository layer. */
    @Autowired
    private TransactionBankRepository transactionBankRepository;


    /**
     * Get transaction by user id.
     * @param userId user id
     * @return transaction
     */
    public List<TransactionBankEntity> getTransactionByUserId(
            final Long userId) {
        Optional<List<TransactionBankEntity>> optionalList =
                transactionBankRepository.findByUserId(userId);
        return optionalList.orElseThrow(()
                -> new NoSuchElementException(
                        "No transaction with user " + userId));
    }

    /**
     * Add transaction in DB.
     * @param transactionBankEntity transaction
     * @return new transaction in DB
     */
    public TransactionBankEntity addTransaction(
            final TransactionBankEntity transactionBankEntity) {
        return transactionBankRepository.save(transactionBankEntity);
    }

}
