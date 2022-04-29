package com.paymybuddy.paymybuddyserver.web.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class TransactionContactDTO {
    /** Transaction id. */
    private long id;
    /** Date transaction. */
    private Date date;
    /** Transaction sender. */
    private UserLightDTO sender;
    /** Transaction recipient. */
    private UserLightDTO recipient;
    /** Transaction amount. */
    private double amount;
    /** Commission amount. */
    private double amountCommission;
    /** Transaction description. */
    private String description;
}
