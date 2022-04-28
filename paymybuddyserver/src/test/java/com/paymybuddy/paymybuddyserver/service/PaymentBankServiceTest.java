package com.paymybuddy.paymybuddyserver.service;

import com.paymybuddy.paymybuddyserver.data.entity.BankEntity;
import com.paymybuddy.paymybuddyserver.data.entity.CommissionEntity;
import com.paymybuddy.paymybuddyserver.data.entity.TransactionBankEntity;
import com.paymybuddy.paymybuddyserver.data.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentBankServiceTest {

    TransactionBankEntity tranBank;
    UserEntity user;
    BankEntity bank;
    CommissionEntity commission;

    @Mock
    private UserService userService;

    @Mock
    private BankService bankService;

    @Mock
    private TransactionBankService transactionBankService;

    @Mock
    private CommissionService commissionService;

    @InjectMocks
    private PaymentBankService paymentBankService;

    @BeforeEach
    public void setUp() {
        tranBank = new TransactionBankEntity();
        tranBank.setAmountTransaction(10);
        user = new UserEntity();
        user.setId(1L);
        user.setAmount(100);
        tranBank.setUser(user);
        bank = new BankEntity();
        bank.setId(1L);
        tranBank.setBank(bank);
        commission = new CommissionEntity();
        commission.setId(1L);
        commission.setAmount(100);
        commission.setRate(0.5);
        tranBank.setAmountCommission(commission.getRate());
    }

    @Test
    void sendMoneyToBank() {
        when(userService.getUserById(1L)).thenReturn(user);
        when(commissionService.getCommission()).thenReturn(commission);
        when(bankService.getBankById(1L)).thenReturn(bank);
        paymentBankService.sendMoneyToBank(tranBank);
        verify(transactionBankService,times(1)).addTransaction(tranBank);
    }

    @Test
    void isSuperiorToZeroWithZero() {
        double amount = 0;
        assertThrows(IllegalStateException.class,()
                -> paymentBankService.isSuperiorToZero( amount));
    }

    @Test
    void isSuperiorToZeroWithZeroInferiorZero() {
        double amount = 0;
        assertThrows(IllegalStateException.class,()
                -> paymentBankService.isSuperiorToZero( amount));
    }

    @Test
    void isAmountSenderProvided() {
        assertThrows(IllegalStateException.class,()
                -> paymentBankService.isAmountSenderProvided(
                1,1,0.5));
    }
}
