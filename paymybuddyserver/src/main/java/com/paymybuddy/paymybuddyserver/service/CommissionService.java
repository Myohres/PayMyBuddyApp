package com.paymybuddy.paymybuddyserver.service;

import com.paymybuddy.paymybuddyserver.data.entity.CommissionEntity;
import com.paymybuddy.paymybuddyserver.data.repository.CommissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommissionService {

    /** Commission repositry layer. */
    @Autowired
    private CommissionRepository commissionRepository;

    /**
     * Get commission.
     * @return commission
     */
    public CommissionEntity getCommission() {
        return commissionRepository.getById(1L);
    }

    /**
     * Increase commission amount.
     * @param commission Commission
     * @param transactionAmount amount to increase
     */
    public void sendCommissionTransaction(
            final CommissionEntity commission,
            final double transactionAmount) {
        commission.setAmount(
                commission.getAmount()
                        + transactionAmount * commission.getRate());
        commissionRepository.save(commission);
    }
}
