package com.paymybuddy.paymybuddyserver.data.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "transaction_contact")
public class TransactionContactEntity {
    /** Transaction id. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /** Date transaction. */
    private Date date;
    /** Transaction sender. */
    @ManyToOne
    @JoinColumn(name = "sender")
    private UserEntity sender;
    /** Transaction recipient. */
    @ManyToOne
    @JoinColumn(name = "contact")
    private UserEntity contact;
    /** Transaction amount.*/
    private double amountTransaction;
    /** transaction amount. */
    private double amountCommission;
    /** Transaction description. */
    private String description;
}
