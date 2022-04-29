package com.paymybuddy.paymybuddyserver.web.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class TransactionUserDTO {
    /** Transaction id. */
    private long id;
    /** transaction date. */
    private Date date;
    /** Transaction bank. */
    private BankLightDTO bank;
    /** Transaction user. */
    private UserLightDTO user;
    /** Transaction amount. */
    private double amountTransaction;
    /** Commission amount. */
    private double amountCommission;
    /** Transaction description. */
    private String description;
}

