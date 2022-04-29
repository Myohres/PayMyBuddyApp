package com.paymybuddy.paymybuddyserver.web.controller;

import com.paymybuddy.paymybuddyserver.data.entity.TransactionBankEntity;
import com.paymybuddy.paymybuddyserver.service.PaymentBankService;
import com.paymybuddy.paymybuddyserver.web.dto.TransactionBankDTO;
import com.paymybuddy.paymybuddyserver.web.mapper.TransactionMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@Slf4j
@RestController
@RequestMapping("/payment_bank")
@CrossOrigin("http://localhost:4200")
public class PaymentBankController {

    /** Extern Payment service layer. */
    @Autowired
    private PaymentBankService paymentBankService;

    /** Transaction Mapper layer. */
    @Autowired
    private TransactionMapper transactionMapper;

    /**
     * Send money from user amount to bank amount.
     * @param transaction Extern Transaction
     * @return Extern transaction
     */
    @PostMapping("")
    public ResponseEntity<TransactionBankDTO> sendMoneyToBank(
            @RequestBody final TransactionBankEntity transaction) {
        log.info("POST/payment_bank");
        try {
            return ResponseEntity.ok(
                    transactionMapper.convertTranBankToTranBankDTO(
                            paymentBankService.sendMoneyToBank(transaction)));
        } catch (NoSuchElementException e) {
            log.info("sendMoneyToBank error : " + e.getMessage());
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.info("sendMoneyToBank error : " + e.getMessage());
            return ResponseEntity.unprocessableEntity().build();
        }
    }
}