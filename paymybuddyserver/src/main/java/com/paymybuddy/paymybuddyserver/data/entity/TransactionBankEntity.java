package com.paymybuddy.paymybuddyserver.data.entity;

import lombok.Getter;
import lombok.Setter;


import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "transaction_bank")
public class TransactionBankEntity {

    /** transaction id. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /** Date transactions. */
    private Date date;
    /** transaction sender. */
    @ManyToOne
    @JoinColumn(name = "user")
    private UserEntity user;
    /** transaction recipient. */
    @ManyToOne
    @JoinColumn(name = "bank")
    private BankEntity bank;
    /** transaction amount. */
    private double amountTransaction;
    /** transaction amount. */
    private double amountCommission;
    /** transaction description. */
    private String description;
}
