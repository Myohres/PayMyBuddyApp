package com.paymybuddy.paymybuddyserver.web.controller;

import com.paymybuddy.paymybuddyserver.data.entity.*;
import com.paymybuddy.paymybuddyserver.service.TransactionUserService;
import com.paymybuddy.paymybuddyserver.web.dto.TransactionUserDTO;
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
class TransactionUserControllerTest {

    UserEntity user;
    BankEntity bank;
    List<TransactionUserEntity> tranUserList;
    TransactionUserEntity tranUser;
    TransactionUserDTO tranDTO;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionUserService transactionUserService;

    @MockBean
    private TransactionMapper transactionMapper;

    @BeforeEach
    private void setUpPerTest() {
        user = new UserEntity();
        bank = new BankEntity();
        tranUserList = new ArrayList<>();

        tranUser = new TransactionUserEntity();
        tranUser.setId(1L);
        tranUser.setDate(new Date());
        tranUser.setUser(user);
        tranUser.setBank(bank);
        tranUser.setAmountTransaction(10);
        tranUser.setAmountCommission(0.5);
        tranUser.setDescription("test");
        tranUserList.add(tranUser);

        tranDTO = new TransactionUserDTO();
    }

    @Test
    void getTransactionByUserId() throws Exception {
        when(transactionUserService.getTransactionByUserId(1L)).thenReturn(tranUserList);
        when(transactionMapper.convertTranUserToTranUserDTO(tranUser)).thenReturn(tranDTO);
        mockMvc.perform(get("/transaction_user/user/id/1")).andExpect(status().isOk());
    }

    @Test
    void getTransactionByUserId_ShouldReturnNotFound() throws Exception {
        when(transactionUserService.getTransactionByUserId(1L)).thenThrow(new NoSuchElementException());
        mockMvc.perform(get("/transaction_user/user/id/1")).andExpect(status().isNotFound());
    }
}
