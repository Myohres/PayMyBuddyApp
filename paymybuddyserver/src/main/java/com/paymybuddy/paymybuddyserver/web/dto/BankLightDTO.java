package com.paymybuddy.paymybuddyserver.web.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BankLightDTO {

    /** Account number bank. */
    private String accountNumber;
    /** Bank name. */
    private String name;

}
