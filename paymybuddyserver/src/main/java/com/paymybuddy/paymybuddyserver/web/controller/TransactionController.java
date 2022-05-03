package com.paymybuddy.paymybuddyserver.web.controller;

import com.paymybuddy.paymybuddyserver.data.entity.TransactionBankEntity;
import com.paymybuddy.paymybuddyserver.data.entity.TransactionContactEntity;
import com.paymybuddy.paymybuddyserver.data.entity.TransactionUserEntity;
import com.paymybuddy.paymybuddyserver.service.TransactionBankService;
import com.paymybuddy.paymybuddyserver.service.TransactionContactService;
import com.paymybuddy.paymybuddyserver.service.TransactionUserService;
import com.paymybuddy.paymybuddyserver.web.dto.TransactionALLDTO;
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

@Slf4j
@RestController
@RequestMapping("/transaction")
@CrossOrigin("http://localhost:4200")
public class TransactionController {

    /** Transaction User service layer.*/
    @Autowired
    private TransactionUserService transactionUserService;
    /** Transaction Bank service layer.*/
    @Autowired
    private TransactionBankService transactionBankService;
    /** Transaction Contact service layer.*/
    @Autowired
    private TransactionContactService transactionContactService;
    /** transactionMapper layer.*/
    @Autowired
    private TransactionMapper transactionMapper;

    /**
     * GetTransactionDTO list by user id.
     * @param userId user id
     * @return GetTransactionDTO list
     */
    @GetMapping("/user/id/{userId}")
    public ResponseEntity<List<TransactionALLDTO>> getTransactionsByUserId(
            @PathVariable("userId") final long userId) {
        log.info("GET/transaction/user/id/" + userId);
        try {
            List<TransactionBankEntity> tranBankList =
                    transactionBankService.getTransactionByUserId(
                            userId);
            List<TransactionContactEntity> tranContactRecipientList =
                    transactionContactService.getTransactionByRecipientId(
                            userId);
            List<TransactionContactEntity> tranContactSenderList =
                    transactionContactService.getTransactionByUserId(
                            userId);
            List<TransactionUserEntity> tranUserList =
                    transactionUserService.getTransactionByUserId(
                            userId);
            return ResponseEntity.ok(transactionMapper
                    .convertListTranToListAllDTO(
                            tranBankList, tranContactRecipientList,
                            tranContactSenderList, tranUserList));
        } catch (NoSuchElementException e) {
            log.error("GetTransactionsByUserid error " + e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
}
