package com.paymybuddy.paymybuddyserver.web.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class TransactionALLDTO {
    /** Transaction date.*/
    private Date date;
    /** Transaction sender name 1 .*/
    private String senderName1;
    /** Transaction sender name 2.*/
    private String senderName2;
    /** Transaction recipient name 1.*/
    private String recipientName1;
    /** Transaction recipient name 2.*/
    private String recipientName2;
    /** Transaction amount.*/
    private double amountTransaction;
    /** Amount commission .*/
    private double amountCommission;
    /** Transaction description.*/
    private String description;
}
