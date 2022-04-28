package com.paymybuddy.paymybuddyserver.data.repository;

import com.paymybuddy.paymybuddyserver.data.entity.CommissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommissionRepository extends
        JpaRepository<CommissionEntity, Long> {
}
