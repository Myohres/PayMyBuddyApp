package com.paymybuddy.paymybuddyserver.web.controller;

import com.paymybuddy.paymybuddyserver.data.entity.TransactionContactEntity;
import com.paymybuddy.paymybuddyserver.data.entity.UserEntity;
import com.paymybuddy.paymybuddyserver.service.TransactionContactService;
import com.paymybuddy.paymybuddyserver.web.dto.TransactionContactDTO;
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
import java.util.List;
import java.util.NoSuchElementException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class TransactionContactControllerTest {

    UserEntity sender;
    UserEntity recipient;
    List<TransactionContactEntity> intTranList;
    TransactionContactEntity intTran;
    TransactionContactDTO tranDTO;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionContactService transactionContactService;

    @MockBean
    private TransactionMapper transactionMapper;

    @BeforeEach
    private void setUpPerTest() {
        sender = new UserEntity();
        recipient = new UserEntity();
        intTranList = new ArrayList<>();
        intTran = new TransactionContactEntity();
        intTran.setId(1L);
        intTran.setSender(sender);
        intTran.setContact(recipient);
        intTran.setAmountTransaction(10);
        intTran.setAmountCommission(0.5);
        intTran.setDescription("test");
        intTranList.add(intTran);

        tranDTO = new TransactionContactDTO();
    }

    @Test
    void getTransactionById() throws Exception {
        when(transactionContactService.getContactTransaction(1L)).thenReturn(intTranList);
        when(transactionMapper.convertTranContactToTranContactDTO(any())).thenReturn(tranDTO);
        mockMvc.perform(get("/transaction_contact/id/1")).andExpect(status().isOk());
    }

    @Test
    void getTransactionById_ShouldReturnNotFound() throws Exception {
        when(transactionContactService.getContactTransaction(1L)).thenThrow(new NoSuchElementException());
        mockMvc.perform(get("/transaction_contact/id/1")).andExpect(status().isNotFound());
    }
}
