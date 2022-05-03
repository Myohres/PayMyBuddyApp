package com.paymybuddy.paymybuddyserver.web.controller;

import com.paymybuddy.paymybuddyserver.service.PaymentBankService;
import com.paymybuddy.paymybuddyserver.web.dto.TransactionBankDTO;
import com.paymybuddy.paymybuddyserver.web.mapper.TransactionMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.NoSuchElementException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class PaymentBankControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PaymentBankService paymentBankService;

    @MockBean
    private TransactionMapper transactionMapper;

    @Test
    void sendMoneyToBank() throws Exception {
        when(transactionMapper.convertTranBankToTranBankDTO(any())).thenReturn(new TransactionBankDTO());
        mockMvc.perform(post("/payment_bank")
                .contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andExpect(status().isOk());
    }

    @Test
    void sendMoneyToBank_ShouldReturnNotFound() throws Exception {
        when(paymentBankService.sendMoneyToBank(any())).thenThrow(new NoSuchElementException());
        mockMvc.perform(post("/payment_bank")
                .contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andExpect(status().isNotFound());
    }

    @Test
    void sendMoneyToBank_ShouldReturnNotEnoughMoney() throws Exception {
        when(paymentBankService.sendMoneyToBank(any())).thenThrow(new IllegalStateException());
        mockMvc.perform(post("/payment_bank")
                .contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andExpect(status().isUnprocessableEntity());
    }

}
