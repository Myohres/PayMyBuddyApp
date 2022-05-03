package com.paymybuddy.paymybuddyserver.web.controller;

import com.paymybuddy.paymybuddyserver.service.PaymentContactService;
import com.paymybuddy.paymybuddyserver.web.dto.TransactionContactDTO;
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
class PaymentContactControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PaymentContactService paymentContactService;

    @MockBean
    private TransactionMapper transactionMapper;

    @Test
    void sendMoneyToContact() throws Exception {
        when(transactionMapper.convertTranContactToTranContactDTO(any())).thenReturn(new TransactionContactDTO());
        mockMvc.perform(post("/payment_contact")
                .contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andExpect(status().isOk());
    }

    @Test
    void sendMoneyToContact_ShouldReturnNoFound() throws Exception {
        when(paymentContactService.sendMoneyToContact(any())).thenThrow(new NoSuchElementException());
        mockMvc.perform(post("/payment_contact")
                .contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andExpect(status().isNotFound());
    }

    @Test
    void sendMoneyToContact_ShouldReturnIllegalState() throws Exception {
        when(paymentContactService.sendMoneyToContact(any())).thenThrow(new IllegalStateException());
        mockMvc.perform(post("/payment_contact")
                .contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andExpect(status().isUnprocessableEntity());
    }
}
