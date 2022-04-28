package com.paymybuddy.paymybuddyserver.service;

import com.paymybuddy.paymybuddyserver.data.entity.TransactionBankEntity;
import com.paymybuddy.paymybuddyserver.data.repository.TransactionBankRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class TransactionBankServiceTest {

    List<TransactionBankEntity> tranBankList;
    TransactionBankEntity tranBank;

    @Mock
    private TransactionBankRepository transactionBankRepository;

    @InjectMocks
    private TransactionBankService transactionBankService;

    @BeforeEach
    public void setUp() {
        tranBankList = new ArrayList<>();
        tranBank = new TransactionBankEntity();
        tranBankList.add(tranBank);
    }

    @Test
    void getTransactionByUserId() {
        when(transactionBankRepository.findByUserId(1L)).thenReturn(Optional.of(tranBankList));
        transactionBankService.getTransactionByUserId(1L);
        verify(transactionBankRepository,times(1)).findByUserId(1L);
    }

    @Test
    void getTransactionByUserIdNotFound() {
        when(transactionBankRepository.findByUserId(1L)).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class,
                () -> transactionBankService.getTransactionByUserId(1L));
    }

    @Test
    void addTransaction() {
        transactionBankService.addTransaction(tranBank);
        verify(transactionBankRepository,times(1)).save(any());
    }
}
