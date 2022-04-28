package com.paymybuddy.paymybuddyserver.service;

import com.paymybuddy.paymybuddyserver.data.entity.TransactionContactEntity;
import com.paymybuddy.paymybuddyserver.data.entity.UserEntity;
import com.paymybuddy.paymybuddyserver.data.repository.TransactionContactRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransactionContactServiceTest {

    List<TransactionContactEntity> tranContactSenderEntityList;
    List<TransactionContactEntity> tranContactRecipientEntityList;

    @Mock
    private TransactionContactRepository transactionContactRepository;

    @InjectMocks
    private TransactionContactService transactionContactService;

    @BeforeEach
    public void setUp() {
        Date date1 = new Date();
        Date date2 = new Date();
        UserEntity user1 = new UserEntity();
        user1.setId(1L);
        user1.setLastName("Bougard");
        user1.setFirstName("Simon");
        UserEntity user2 = new UserEntity();
        user2.setId(2L);
        user2.setLastName("Beaumont");
        user2.setFirstName("Etienne");

        tranContactSenderEntityList = new ArrayList<>();
        TransactionContactEntity tranContactSenderEntity = new TransactionContactEntity();
        tranContactSenderEntity.setDate(date1);
        tranContactSenderEntity.setSender(user1);
        tranContactSenderEntity.setContact(user2);
        tranContactSenderEntityList.add(tranContactSenderEntity);

        tranContactRecipientEntityList = new ArrayList<>();
        TransactionContactEntity tranContactRecipientEntity = new TransactionContactEntity();
        tranContactRecipientEntity.setDate(date2);
        tranContactRecipientEntity.setSender(user2);
        tranContactRecipientEntity.setContact(user1);
        tranContactRecipientEntityList.add(tranContactRecipientEntity);

    }

    @Test
    void getTransactionByUserId() {
        when(transactionContactRepository.findBySenderId(anyLong())).thenReturn(Optional.of(tranContactSenderEntityList));
        transactionContactService.getTransactionByUserId(1L);
        verify(transactionContactRepository,times(1)).findBySenderId(1L);
    }

    @Test
    void getTransactionByUserIdNotFound() {
        when(transactionContactRepository.findBySenderId(1L)).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class,
                () -> transactionContactService.getTransactionByUserId(1L));
    }

    @Test
    void getTransactionBySenderId() {
        when(transactionContactRepository.findByContactId(1L)).thenReturn(Optional.of(tranContactSenderEntityList));
        transactionContactService.getTransactionByRecipientId(1L);
        verify(transactionContactRepository,times(1)).findByContactId(1L);
    }

    @Test
    void getTransactionBySenderIdNotFound() {
        when(transactionContactRepository.findByContactId(1L)).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class,
                () -> transactionContactService.getTransactionByRecipientId(1L));
    }

    @Test
    void addTransaction() {
        transactionContactService.addTransaction(any());
        verify(transactionContactRepository,times(1)).save(any());
    }

    @Test
    void getContactTransaction() {
        when(transactionContactRepository.findBySenderId(anyLong())).thenReturn(Optional.of(tranContactSenderEntityList));
        when(transactionContactRepository.findByContactId(1L)).thenReturn(Optional.of(tranContactSenderEntityList));
        List<TransactionContactEntity> transactionContactEntityList =
                transactionContactService.getContactTransaction(1L);
        assertEquals(2, transactionContactEntityList.size());
    }
}