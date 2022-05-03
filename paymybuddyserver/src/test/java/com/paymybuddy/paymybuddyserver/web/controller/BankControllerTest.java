package com.paymybuddy.paymybuddyserver.web.controller;

import com.paymybuddy.paymybuddyserver.data.entity.BankEntity;
import com.paymybuddy.paymybuddyserver.service.BankService;
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
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class BankControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BankService bankService;

    @Test
    void getBankById() throws Exception {
        when(bankService.getBankById(any())).thenReturn(new BankEntity());
        mockMvc.perform(get("/bank/id/1")).andExpect(status().isOk());
    }

    @Test
    void getBankById_ShouldReturnNotFound() throws Exception {
        when(bankService.getBankById(any())).thenThrow(new NoSuchElementException());
        mockMvc.perform(get("/bank/id/1")).andExpect(status().isNotFound());
    }

    @Test
    void getBankByAccountNumber() throws Exception {
        when(bankService.getBankByAccountNumber("55")).thenReturn(new BankEntity());
        mockMvc.perform(get("/bank/account/55")).andExpect(status().isOk());
    }

    @Test
    void getBankByAccountNumberNotFound() throws Exception {
        when(bankService.getBankByAccountNumber("55")).thenThrow(new NoSuchElementException());
        mockMvc.perform(get("/bank/account/55")).andExpect(status().isNotFound());
    }

    @Test
    void addBank() throws Exception {
        mockMvc.perform(post("/bank")
                .contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andExpect(status().isOk());
    }

    @Test
    void addBank_ShouldReturnNotFound() throws Exception {
        when(bankService.addBank(any())).thenThrow(new NoSuchElementException());
        mockMvc.perform(post("/bank")
                .contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteBank() throws Exception {
        mockMvc.perform(delete("/bank")
                .contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andExpect(status().isOk());
    }

    @Test
    void deleteBank_ShouldReturnNotFound() throws Exception {
        doThrow(new NoSuchElementException()).when(bankService).deleteBank(any());
        mockMvc.perform(delete("/bank")
                .contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andExpect(status().isNotFound());
    }
}
