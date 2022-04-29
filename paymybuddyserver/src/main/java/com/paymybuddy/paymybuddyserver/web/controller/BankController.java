package com.paymybuddy.paymybuddyserver.web.controller;

import com.paymybuddy.paymybuddyserver.data.entity.BankEntity;
import com.paymybuddy.paymybuddyserver.service.BankService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@Slf4j
@RestController
@RequestMapping("/bank")
@CrossOrigin("http://localhost:4200")
public class BankController {

    /** Bank service layer. */
    @Autowired
    private BankService bankService;


    /**
     * Get bank by id.
     * @param bankId id bank
     * @return BankEntity
     */
    @GetMapping("/id/{bankId}")
    public ResponseEntity<BankEntity> getBankById(
            @PathVariable("bankId") final Long bankId) {
        log.info("GET/bank/id/" + bankId);
        try {
            return ResponseEntity.ok(bankService.getBankById(bankId));
        } catch (NoSuchElementException e) {
            log.error("getBankById error " + e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Get bank Entity by account number.
     * @param account bank number
     * @return BankEntity
     */
    @GetMapping("/account/{account}")
    public ResponseEntity<BankEntity> getBankByAccountNumber(
            @PathVariable("account") final String account) {
        log.info("GET/bank/account/" + account);
        try {
            return ResponseEntity.ok(bankService.
                    getBankByAccountNumber(account));
        } catch (NoSuchElementException e) {
            log.error("getBankByAccountNumber error " + e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Add a bank.
     * @param bank bankEntity
     * @return bank in DB
     */
    @PostMapping("")
    public ResponseEntity<BankEntity> addBank(
            @RequestBody final BankEntity bank) {
        log.info("POST/bank");
        try {
            return ResponseEntity.ok(bankService.addBank(bank));
        } catch (NoSuchElementException e) {
            log.error("AddBank error : " + e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Delete bank.
     * @param bank bankEntity
     * @return bank deleted
     */
    @DeleteMapping("")
    public ResponseEntity<?> deleteBank(
            @RequestBody final BankEntity bank) {
        log.info("DEL/bank");
        try {
            bankService.deleteBank(bank);
            return ResponseEntity.ok().build();
        } catch (NoSuchElementException e) {
            log.error("deleteBank " + e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
}
