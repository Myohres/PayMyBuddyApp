package com.paymybuddy.paymybuddyserver.service;

import com.paymybuddy.paymybuddyserver.data.entity.TransactionUserEntity;
import com.paymybuddy.paymybuddyserver.data.repository.TransactionUserRepository;
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
class TransactionUserServiceTest {

    @Mock
    private TransactionUserRepository transactionUserRepository;

    @InjectMocks
    private TransactionUserService transactionUserService;

    @Test
    void getTransactionByUserId() {
        List<TransactionUserEntity> tranUserList = new ArrayList<>();
        TransactionUserEntity tranUser = new TransactionUserEntity();
        tranUserList.add(tranUser);
        when(transactionUserRepository.findByUserId(any())).thenReturn(Optional.of(tranUserList));
        transactionUserService.getTransactionByUserId(1L);
        verify(transactionUserRepository,times(1)).findByUserId(any());
    }

    @Test
    void getTransactionByUserIdNotFound() {
        when(transactionUserRepository.findByUserId(1L)).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class,
                () -> transactionUserService.getTransactionByUserId(1L));
    }

    @Test
    void addTransaction() {
        transactionUserService.addTransaction(any());
        verify(transactionUserRepository,times(1)).save(any());
    }

}
