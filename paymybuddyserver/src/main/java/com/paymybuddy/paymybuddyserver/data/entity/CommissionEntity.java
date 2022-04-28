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
@Table(name = "Commission")
public class CommissionEntity {

    /** Id commission. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /** Amount commission. */
    private double amount;
    /** Rate commission. */
    private double rate;
}
