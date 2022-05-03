package com.paymybuddy.paymybuddyserver.web.controller;

import com.paymybuddy.paymybuddyserver.data.entity.TransactionBankEntity;
import com.paymybuddy.paymybuddyserver.data.entity.TransactionContactEntity;
import com.paymybuddy.paymybuddyserver.data.entity.TransactionUserEntity;
import com.paymybuddy.paymybuddyserver.service.TransactionBankService;
import com.paymybuddy.paymybuddyserver.service.TransactionContactService;
import com.paymybuddy.paymybuddyserver.service.TransactionUserService;
import com.paymybuddy.paymybuddyserver.web.dto.TransactionALLDTO;
import com.paymybuddy.paymybuddyserver.web.mapper.TransactionMapper;
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
class TransactionControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    TransactionContactService transactionContactService;

    @MockBean
    TransactionBankService transactionBankService;

    @MockBean
    TransactionUserService transactionUserService;

    @MockBean
    TransactionMapper transactionMapper;

    @Test
    void getTransactionsByUserId() throws Exception {
        List<TransactionBankEntity> tranBankList = new ArrayList<>();
        List<TransactionContactEntity> tranContactSenderList = new ArrayList<>();
        List<TransactionContactEntity> tranContactRecipientList = new ArrayList<>();
        List<TransactionUserEntity> tranUserList = new ArrayList<>();

        TransactionBankEntity tranBankEntity = new TransactionBankEntity();
        tranBankEntity.setId(1L);
        TransactionContactEntity tranContactEntity = new TransactionContactEntity();
        tranContactEntity.setId(2L);
        TransactionUserEntity tranUserEntity = new TransactionUserEntity();
        tranUserEntity.setId(3L);
        tranBankList.add(tranBankEntity);
        tranContactSenderList.add(tranContactEntity);
        tranUserList.add(tranUserEntity);

        List<TransactionALLDTO> transactionALLDTOList = new ArrayList<>();

        when(transactionBankService.getTransactionByUserId(any())).thenReturn(tranBankList);
        when(transactionContactService.getTransactionByUserId(any())).thenReturn(tranContactSenderList);
        when(transactionContactService.getTransactionByRecipientId(any())).thenReturn(tranContactRecipientList);
        when(transactionUserService.getTransactionByUserId(any())).thenReturn(tranUserList);

        when(transactionMapper.convertListTranToListAllDTO(any(),any(),any(),any())).thenReturn(transactionALLDTOList);
        mockMvc.perform(get("/transaction/user/id/1")).andExpect(status().isOk());

    }

    @Test
    void getTransactionsByUserIdNotFound() throws Exception {
        when(transactionBankService.getTransactionByUserId(any())).thenThrow(new NoSuchElementException());
        mockMvc.perform(get("/transaction/user/id/1")).andExpect(status().isNotFound());
    }
}
