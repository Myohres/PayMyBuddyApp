package com.paymybuddy.paymybuddyserver.web.controller;

import com.paymybuddy.paymybuddyserver.data.entity.TransactionUserEntity;
import com.paymybuddy.paymybuddyserver.service.PaymentUserService;
import com.paymybuddy.paymybuddyserver.web.dto.TransactionUserDTO;
import com.paymybuddy.paymybuddyserver.web.mapper.TransactionMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@Slf4j
@RestController
@RequestMapping("/payment_user")
@CrossOrigin("http://localhost:4200")
public class PaymentUserController {

    /** Extern Payment service layer. */
    @Autowired
    private PaymentUserService paymentUserService;

    /** Transaction Mapper layer. */
    @Autowired
    private TransactionMapper transactionMapper;
    /**
     * Get money from bank amount to user amount.
     * @param transaction Extern Transaction
     * @return Extern transaction
     */
    @PostMapping("")
    public ResponseEntity<TransactionUserDTO> sendMoneyToUser(
            @RequestBody final TransactionUserEntity transaction) {
        log.info("POST/payment_user");
        try {
            return ResponseEntity.ok(
                    transactionMapper.convertTranUserToTranUserDTO(
                            paymentUserService.sendMoneyToUser(transaction)));
        } catch (NoSuchElementException e) {
            log.info("sendMoneyToUser error : " + e.getMessage());
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.info("sendMoneyToUser error : " + e.getMessage());
            return ResponseEntity.unprocessableEntity().build();
        }
    }
}
