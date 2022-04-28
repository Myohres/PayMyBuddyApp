package com.paymybuddy.paymybuddyserver.service;

import com.paymybuddy.paymybuddyserver.data.entity.BankEntity;
import com.paymybuddy.paymybuddyserver.data.repository.BankRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class BankServiceTest {

    BankEntity bank;

    @Mock
    private BankRepository bankRepository;

    @InjectMocks
    private BankService bankService;

    @BeforeEach
    public void setUp() {
        bank = new BankEntity();
        bank.setId(1L);
        bank.setName("bankName");
        bank.setAccountNumber("5555");
        bank.setAmount(5000);
    }

    @Test
    void getBankById() {
        when(bankRepository.findById(any())).thenReturn(Optional.of(bank));
        BankEntity bankResult = bankService.getBankById(1L);
        assertEquals(1L, bankResult.getId());
        assertEquals("bankName", bankResult.getName());
        assertEquals("5555", bankResult.getAccountNumber());
        assertEquals(5000, bankResult.getAmount());
    }

    @Test
    void getBankByIdNoBankFound() {
        when(bankRepository.findById(any())).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class,
                () -> bankService.getBankById(1L));
    }

    @Test
    void getBankByAccountNumber() {
        when(bankRepository.findByAccountNumber(any())).thenReturn(Optional.of(bank));
        BankEntity bankResult = bankService.getBankByAccountNumber("5555");
        assertEquals(1L, bankResult.getId());
        assertEquals("bankName", bankResult.getName());
        assertEquals("5555", bankResult.getAccountNumber());
        assertEquals(5000, bankResult.getAmount());
    }

    @Test
    void getBankByAccountNumberNotFound() {
        when(bankRepository.findByAccountNumber(any())).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class,
                () -> bankService.getBankByAccountNumber("5555"));
    }

    @Test
    void sendMoney() {
        bankService.sendMoney(bank, 5,0.5);
        assertEquals(4992.5,bank.getAmount());
        verify(bankRepository,times(1)).save(bank);
    }

    @Test
    void receivedMoney() {
        bankService.receivedMoney(bank,5);
        assertEquals(5005, bank.getAmount());
        verify(bankRepository,times(1)).save(bank);
    }

    @Test
    void addBank() {
        when(bankRepository.save(any())).thenReturn(bank);
        bankService.addBank(bank);
        verify(bankRepository,times(1)).save(bank);
    }

    @Test
    void deleteBank() {
        bankService.deleteBank(bank);
        verify(bankRepository, times(1)).delete(bank);
    }
}
