package com.paymybuddy.paymybuddyserver.service;

import com.paymybuddy.paymybuddyserver.data.entity.BankEntity;
import com.paymybuddy.paymybuddyserver.data.entity.CommissionEntity;
import com.paymybuddy.paymybuddyserver.data.entity.TransactionUserEntity;
import com.paymybuddy.paymybuddyserver.data.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class PaymentUserServiceTest {

    @Mock
    private UserService userService;

    @Mock
    private BankService bankService;

    @Mock
    private TransactionUserService transactionUserService;

    @Mock
    private CommissionService commissionService;

    @InjectMocks
    private PaymentUserService paymentUserService;

    @Test
    void sendMoneyToUser() {
        TransactionUserEntity tranUser = new TransactionUserEntity();
        tranUser.setId(1L);
        tranUser.setDate(new Date());
        UserEntity user = new UserEntity();
        user.setId(1L);
        tranUser.setUser(user);
        BankEntity bank = new BankEntity();
        bank.setId(1L);
        bank.setAmount(1000);
        tranUser.setBank(bank);
        tranUser.setAmountTransaction(55);
        tranUser.setAmountCommission(0.5);
        tranUser.setDescription("test");
        CommissionEntity commission = new CommissionEntity();
        commission.setId(1L);
        commission.setAmount(100);
        commission.setRate(0.5);
        when(userService.getUserById(1L)).thenReturn(user);
        when(commissionService.getCommission()).thenReturn(commission);
        when(bankService.getBankById(1L)).thenReturn(bank);
        paymentUserService.sendMoneyToUser(tranUser);
        verify(transactionUserService,times(1)).addTransaction(tranUser);
    }

    @Test
    void isSuperiorToZero() {
        double amount = 0;
        assertThrows(IllegalStateException.class,()
                -> paymentUserService.isSuperiorToZero( amount));
    }

    @Test
    void isAmountSenderProvided() {
        assertThrows(IllegalStateException.class,()
                -> paymentUserService.isAmountSenderProvided(
                1,1,0.5));
    }
}