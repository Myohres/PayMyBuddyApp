package com.paymybuddy.paymybuddyserver.data.repository;

import com.paymybuddy.paymybuddyserver.data.entity.BankEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BankRepository extends
        JpaRepository<BankEntity, Long> {
    /**
     * Find bankEntity by accountNumber.
     * @param accountNumber bank number
     * @return BankEntity
     */
    Optional<BankEntity> findByAccountNumber(String accountNumber);
}
