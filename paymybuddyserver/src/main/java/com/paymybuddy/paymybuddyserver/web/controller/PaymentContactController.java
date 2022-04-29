package com.paymybuddy.paymybuddyserver.web.controller;

import com.paymybuddy.paymybuddyserver.data.entity.TransactionContactEntity;
import com.paymybuddy.paymybuddyserver.service.PaymentContactService;
import com.paymybuddy.paymybuddyserver.web.dto.TransactionContactDTO;
import com.paymybuddy.paymybuddyserver.web.mapper.TransactionMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@Slf4j
@RestController
@RequestMapping("/payment_contact")
@CrossOrigin("http://localhost:4200")
public class PaymentContactController {

    /** IntPayment service layer. */
    @Autowired
    private PaymentContactService paymentContactService;

    /** Mapper layer. */
    @Autowired
    private TransactionMapper transactionMapper;

    /**
     * Do payment between two user.
     * @param transaction transaction
     * @return transaction
     */
    @PostMapping("")
    public ResponseEntity<TransactionContactDTO> sendMoneyToContact(
            @RequestBody final TransactionContactEntity transaction) {
        log.info("POST/payment_contact");
        try {
            return ResponseEntity.ok(
                    transactionMapper.convertTranContactToTranContactDTO(
                            paymentContactService.sendMoneyToContact(
                                    transaction)));
        } catch (NoSuchElementException e) {
            log.info("sendMoneyToContact : " + e.getMessage());
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.info("sendMoneyToContact : " + e.getMessage());
            return ResponseEntity.unprocessableEntity().build();
        }
    }
}
