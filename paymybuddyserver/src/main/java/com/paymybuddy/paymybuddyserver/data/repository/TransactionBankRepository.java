package com.paymybuddy.paymybuddyserver.data.repository;

import com.paymybuddy.paymybuddyserver.data.entity.TransactionBankEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionBankRepository extends
        JpaRepository<TransactionBankEntity, Long> {

    /**
     * Find transaction by user id.
     * @param userId user id
     * @return transactionBankEntity List
     */
    Optional<List<TransactionBankEntity>> findByUserId(long userId);
}
