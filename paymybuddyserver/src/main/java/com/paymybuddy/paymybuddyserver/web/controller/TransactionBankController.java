package com.paymybuddy.paymybuddyserver.web.controller;

import com.paymybuddy.paymybuddyserver.data.entity.TransactionBankEntity;
import com.paymybuddy.paymybuddyserver.service.TransactionBankService;
import com.paymybuddy.paymybuddyserver.web.dto.TransactionBankDTO;
import com.paymybuddy.paymybuddyserver.web.mapper.TransactionMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/transaction_bank")
@CrossOrigin("http://localhost:4200")
public class TransactionBankController {

    /** Transaction service layer.*/
    @Autowired
    private TransactionBankService transactionBanKService;
    /** transactionMapper layer. */
    @Autowired
    private TransactionMapper transactionMapper;


    /**
     * Get transactions by id.
     * @param id long
     * @return List transactions
     */
    @GetMapping("/user/id/{id}")
    public ResponseEntity<List<TransactionBankDTO>> getTransactionBySenderId(
            @PathVariable("id") final Long id) {
        log.info("GET/transaction_bank/id/" + id);
        try {
            List<TransactionBankEntity> transactionBankEntityList =
                    transactionBanKService.getTransactionByUserId(id);
            List<TransactionBankDTO> transactionBankDTOList =
                    transactionBankEntityList.stream()
                            .map(data -> transactionMapper
                                    .convertTranBankToTranBankDTO(
                                            data)
                            ).collect(Collectors.toList());
            return ResponseEntity.ok(transactionBankDTOList);
        } catch (NoSuchElementException e) {
            log.error("Get transactions by user id error : " + e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
}
