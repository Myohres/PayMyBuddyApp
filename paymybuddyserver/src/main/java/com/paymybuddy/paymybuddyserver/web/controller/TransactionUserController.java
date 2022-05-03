package com.paymybuddy.paymybuddyserver.web.controller;

import com.paymybuddy.paymybuddyserver.data.entity.TransactionUserEntity;
import com.paymybuddy.paymybuddyserver.service.TransactionUserService;
import com.paymybuddy.paymybuddyserver.web.dto.TransactionUserDTO;
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
@RequestMapping("/transaction_user")
@CrossOrigin("http://localhost:4200")
public class TransactionUserController {

    /** transaction service layer. */
    @Autowired
    private TransactionUserService transactionUserService;

    /** Transaction Mapper layer. */
    @Autowired
    private TransactionMapper transactionMapper;

    /**
     * Get extern transaction dto by user id.
     * @param id user id
     * @return extern transaction dto
     */
    @GetMapping("/user/id/{userId}")
    public ResponseEntity<List<TransactionUserDTO>> getTransactionByUserId(
            @PathVariable("userId") final Long id) {
        log.info("GET/transaction_user/user/" + id);
        try {
            List<TransactionUserEntity> transactionUserEntityList =
                    transactionUserService.getTransactionByUserId(id);
            List<TransactionUserDTO> dtoList =
                    transactionUserEntityList.stream()
                            .map(data -> transactionMapper
                                    .convertTranUserToTranUserDTO(data))
                            .collect(Collectors.toList());
            return ResponseEntity.ok(dtoList);
        } catch (NoSuchElementException e) {
            log.error("getTransactionByUserId error " + e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
}
