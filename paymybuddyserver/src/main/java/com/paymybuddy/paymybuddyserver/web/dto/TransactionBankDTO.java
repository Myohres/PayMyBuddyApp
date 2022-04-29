package com.paymybuddy.paymybuddyserver.web.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class TransactionBankDTO {
    /** Transaction id. */
    private long id;
    /** Transaction date. */
    private Date date;
    /** Transaction sender. */
    private UserLightDTO user;
    /** Transaction recipient. */
    private BankLightDTO bank;
    /** Transaction amount. */
    private double amountTransaction;
    /** Commission amount. */
    private double amountCommission;
    /** Transaction description. */
    private String description;
}
