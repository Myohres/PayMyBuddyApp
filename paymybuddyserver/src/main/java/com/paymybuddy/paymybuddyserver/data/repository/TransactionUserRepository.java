package com.paymybuddy.paymybuddyserver.data.repository;

import com.paymybuddy.paymybuddyserver.data.entity.TransactionUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionUserRepository
        extends JpaRepository<TransactionUserEntity, Long> {
    /**
     * Find transaction by user id.
     * @param id user id
     * @return transaction
     */
    Optional<List<TransactionUserEntity>> findByUserId(Long id);
}
