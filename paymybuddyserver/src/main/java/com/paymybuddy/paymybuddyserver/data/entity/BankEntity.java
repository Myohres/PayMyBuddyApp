package com.paymybuddy.paymybuddyserver.data.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Entity
@Getter
@Setter
@Table(name = "bank")
public class BankEntity {

    /** Id bank. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /** Account number bank. */
    private String accountNumber;
    /** Name bank. */
    private String name;
    /** Amount bank. */
    private double amount;

}
