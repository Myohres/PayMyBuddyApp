package com.paymybuddy.paymybuddyserver.web.controller;


import com.paymybuddy.paymybuddyserver.service.PaymentUserService;
import com.paymybuddy.paymybuddyserver.web.dto.TransactionUserDTO;
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
class PaymentUserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PaymentUserService paymentUserService;


    @MockBean
    private TransactionMapper transactionMapper;


    @Test
    void sendMoneyToUser() throws Exception {
        when(transactionMapper.convertTranUserToTranUserDTO(any())).thenReturn(new TransactionUserDTO());
        mockMvc.perform(post("/payment_user")
                .contentType(MediaType.APPLICATION_JSON).content(" {\n" +
                        "        \"id\": 15,\n" +
                        "        \"date\": \"2022-04-05T22:00:00.000+00:00\",\n" +
                        "        \"bank\": {\n" +
                        "            \"accountNumber\": \"5556885\",\n" +
                        "            \"name\": \"Paypal\"\n" +
                        "        },\n" +
                        "        \"user\": {\n" +
                        "            \"id\": 1,\n" +
                        "            \"lastName\": \"Bougard\",\n" +
                        "            \"firstName\": \"Simon\"\n" +
                        "        },\n" +
                        "        \"amountTransaction\": 100.0,\n" +
                        "        \"amountCommission\": 0.0,\n" +
                        "        \"description\": null\n" +
                        "    }")).andExpect(status().isOk());
    }

    @Test
    void sendMoneyToUserNotFound() throws Exception {
        when(paymentUserService.sendMoneyToUser(any())).thenThrow(new NoSuchElementException());
        mockMvc.perform(post("/payment_user")
                .contentType(MediaType.APPLICATION_JSON).content("{} "))
                .andExpect(status().isNotFound());
    }

    @Test
    void sendMoneyToUserIllegalState() throws Exception {
        when(paymentUserService.sendMoneyToUser(any())).thenThrow(new IllegalStateException());
        mockMvc.perform(post("/payment_user")
                .contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andExpect(status().isUnprocessableEntity());
    }
}
