package com.paymybuddy.paymybuddyserver.service;

import com.paymybuddy.paymybuddyserver.data.entity.CommissionEntity;
import com.paymybuddy.paymybuddyserver.data.entity.TransactionContactEntity;
import com.paymybuddy.paymybuddyserver.data.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentContactServiceTest {

    @Mock
    UserService userService;

    @Mock
    TransactionContactService transactionContactService;

    @Mock
    CommissionService commissionService;

    @InjectMocks
    PaymentContactService paymentContactService;

    @Test
    void intPayment() {
        CommissionEntity commission = new CommissionEntity();
        commission.setId(1L);
        commission.setRate(0.5);
        commission.setAmount(50);
        UserEntity sender = new UserEntity();
        sender.setId(1L);
        sender.setAmount(500);
        UserEntity recipient = new UserEntity();
        recipient.setId(2L);
        TransactionContactEntity intTran = new TransactionContactEntity();
        intTran.setId(1L);
        intTran.setSender(sender);
        intTran.setContact(recipient);
        intTran.setDescription("Test");
        intTran.setAmountTransaction(10);
        when(commissionService.getCommission()).thenReturn(commission);
        when(userService.getUserById(1L)).thenReturn(sender);
        when(userService.getUserById(2L)).thenReturn(recipient);
        paymentContactService.sendMoneyToContact(intTran);
        verify(transactionContactService,times(1)).addTransaction(intTran);
        verify(userService,times(1)).sendMoney(sender,intTran.getAmountTransaction(),commission.getRate());
        verify(commissionService,times(1)).sendCommissionTransaction(commission,intTran.getAmountTransaction());
        verify(userService,times(1)).receivedMoney(recipient,intTran.getAmountTransaction());
    }

    @Test
    void isSuperiorToZero() {
        assertThrows(IllegalStateException.class,()
                -> paymentContactService.isSuperiorToZero(0));
    }

    @Test
    void isAmountSenderProvided() {
        assertThrows(IllegalStateException.class,()
                -> paymentContactService.isAmountSenderProvided(10,0,0.5));
    }
}
