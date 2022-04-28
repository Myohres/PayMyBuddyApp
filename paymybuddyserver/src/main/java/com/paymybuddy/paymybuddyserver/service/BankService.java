package com.paymybuddy.paymybuddyserver.service;

import com.paymybuddy.paymybuddyserver.data.entity.BankEntity;
import com.paymybuddy.paymybuddyserver.data.repository.BankRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class BankService {

    /** Bank repository layer. */
    @Autowired
    private BankRepository bankRepository;

    /**
     * Get a bank by id.
     * @param id id bank
     * @return BankEntity
     */
    public BankEntity getBankById(final Long id) {
        Optional<BankEntity> optionalBankEntity =
                bankRepository.findById(id);
        return optionalBankEntity.orElseThrow(()
                -> new NoSuchElementException("No bank " + id));
    }

    /**
     * Get bank by account number.
     * @param accountNumber account number
     * @return BankEntity
     */
    public BankEntity getBankByAccountNumber(final String accountNumber) {
        Optional<BankEntity> optionalBankEntity =
                bankRepository.findByAccountNumber(accountNumber);
        return optionalBankEntity.orElseThrow(()
                -> new NoSuchElementException("No bank " + accountNumber));
    }

    /**
     * Decrease bank amount.
     * @param bank bank
     * @param transferAmount amount to decrease
     * @param commissionRate commission rate
     */
    public void sendMoney(final BankEntity bank,
                          final double transferAmount,
                          final double commissionRate) {
        double newAmount = bank.getAmount()
                - (transferAmount + (transferAmount * commissionRate));
        bank.setAmount(newAmount);
        bankRepository.save(bank);
    }

    /**
     * Increase bank amount.
     * @param bank bank
     * @param transferAmount amount to increase
     */
    public void receivedMoney(final BankEntity bank,
                              final double transferAmount) {
        bank.setAmount(bank.getAmount() + transferAmount);
        bankRepository.save(bank);
    }

    /**
     * Add a bank in DB.
     * @param bank Bank
     * @return bank in db
     */
    public BankEntity addBank(final BankEntity bank) {
        return bankRepository.save(bank);
    }

    /**
     * Delete bank in DB.
     * @param bank Bank
     */
    public void deleteBank(final BankEntity bank) {
        bankRepository.delete(bank);
    }
}

