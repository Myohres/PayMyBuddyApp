package com.paymybuddy.paymybuddyserver.data.repository;

import com.paymybuddy.paymybuddyserver.data.entity.TransactionContactEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionContactRepository extends
        JpaRepository<TransactionContactEntity, Long> {
    /**
     * Get Transaction by sender id.
     * @param id sender id
     * @return transaction list
     */
    Optional<List<TransactionContactEntity>> findBySenderId(long id);

    /**
     * Get Transaction by recipient id.
     * @param id recipient id
     * @return transaction list
     */
    Optional<List<TransactionContactEntity>> findByContactId(long id);

}
