package com.paymybuddy.paymybuddyserver.web.controller;

import com.paymybuddy.paymybuddyserver.data.entity.TransactionContactEntity;
import com.paymybuddy.paymybuddyserver.service.TransactionContactService;
import com.paymybuddy.paymybuddyserver.web.dto.TransactionContactDTO;
import com.paymybuddy.paymybuddyserver.web.mapper.TransactionMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/transaction_contact")
@CrossOrigin("http://localhost:4200")
public class TransactionContactController {
    /** IntTransaction service layer.*/
    @Autowired
    private TransactionContactService transactionContactService;

    /** TransactionContactMapper layer. */
    @Autowired
    private TransactionMapper transactionMapper;


    /**
     * Get transactions by id.
     * @param id long
     * @return List transactions
     */
    @GetMapping("/id/{id}")
    public ResponseEntity<List<TransactionContactDTO>> getTransactionByUserId(
            @PathVariable("id") final Long id) {
        log.info("GET/transaction_contact/id/" + id);
        try {
            List<TransactionContactEntity> transUserAndContact =
                    transactionContactService.getContactTransaction(id);
            List<TransactionContactDTO> transactionContactDTOList =
                    transUserAndContact.stream()
                            .map(transactionEntity -> transactionMapper
                                    .convertTranContactToTranContactDTO(
                                            transactionEntity)
                            ).collect(Collectors.toList());
            return ResponseEntity.ok(transactionContactDTOList);
        } catch (NoSuchElementException e) {
            log.error("getTransactionByUserId : " + e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

}
