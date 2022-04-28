package com.paymybuddy.paymybuddyserver.service;

import com.paymybuddy.paymybuddyserver.data.entity.CommissionEntity;
import com.paymybuddy.paymybuddyserver.data.repository.CommissionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommissionServiceTest {

    @Mock
    private CommissionRepository commissionRepository;

    @InjectMocks
    private CommissionService commissionService;

    @Test
    void getCommission() {
        when(commissionRepository.getById(any())).thenReturn(new CommissionEntity());
        commissionService.getCommission();
        verify(commissionRepository,times(1)).getById(any());
    }

    @Test
    void sendCommissionTransaction() {
        CommissionEntity com = new CommissionEntity();
        com.setAmount(0);
        com.setRate(0.5);
        commissionService.sendCommissionTransaction(com,50);
        assertEquals(25, com.getAmount());
        verify(commissionRepository,times(1)).save(com);
    }
}
