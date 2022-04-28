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
@Table(name = "transaction_user")
public class TransactionUserEntity {

    /** Externe transaction id. */
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    /** Date transactions. */
    private Date date;
    /** Transaction recipient. */
    @ManyToOne
    @JoinColumn(name = "bank")
    private BankEntity bank;
    /** Transaction sender. */
    @ManyToOne
    @JoinColumn(name = "user")
    private UserEntity user;
    /** Transaction amount. */
    private double amountTransaction;
    /** Commission amount. */
    private double amountCommission;
    /** Transaction description. */
    private String description;

}
