package com.paymybuddy.paymybuddyserver.web.controller;

import com.paymybuddy.paymybuddyserver.data.entity.BankEntity;
import com.paymybuddy.paymybuddyserver.data.entity.TransactionBankEntity;
import com.paymybuddy.paymybuddyserver.data.entity.UserEntity;
import com.paymybuddy.paymybuddyserver.service.TransactionBankService;
import com.paymybuddy.paymybuddyserver.web.dto.TransactionBankDTO;
import com.paymybuddy.paymybuddyserver.web.mapper.TransactionMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class TransactionBankControllerTest {

    List<TransactionBankEntity> tranList;
    TransactionBankEntity tranBankEntity;
    TransactionBankDTO tranBankDTO;
    UserEntity user;
    BankEntity bank;

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private TransactionBankService transactionBankService;

    @MockBean
    private TransactionMapper transactionMapper;

    @BeforeEach
    public void setup() {
        user = new UserEntity();
        bank = new BankEntity();
        tranList = new ArrayList<>();

        tranBankEntity = new TransactionBankEntity();
        tranBankEntity.setId(1L);
        tranBankEntity.setDate(new Date());
        tranBankEntity.setUser(user);
        tranBankEntity.setBank(bank);
        tranBankEntity.setAmountTransaction(10);
        tranBankEntity.setAmountCommission(0.5);
        tranBankEntity.setDescription("test");
        tranList.add(tranBankEntity);

        tranBankDTO = new TransactionBankDTO();
    }

    @Test
    void getTransactionBySenderId() throws Exception {
        when(transactionBankService.getTransactionByUserId(1L)).thenReturn(tranList);
        when(transactionMapper.convertTranBankToTranBankDTO(tranBankEntity)).thenReturn(tranBankDTO);
        mockMvc.perform(get("/transaction_bank/user/id/1")).andExpect(status().isOk());
    }

    @Test
    void getTransactionBySenderIdNotFound() throws Exception {
        when(transactionBankService.getTransactionByUserId(1L)).thenThrow(new NoSuchElementException());
        mockMvc.perform(get("/transaction_bank/user/id/1")).andExpect(status().isNotFound());
    }
}
