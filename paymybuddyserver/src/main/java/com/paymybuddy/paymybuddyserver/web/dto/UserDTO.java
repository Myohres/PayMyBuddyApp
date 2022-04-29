package com.paymybuddy.paymybuddyserver.web.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserDTO {
    /** User id. */
    private Long id;
    /** User email. */
    private String email;
    /** User password. */
    private String password;
    /** User lastname. */
    private String lastName;
    /** User firstname. */
    private String firstName;
    /** User amount. */
    private double amount;
    /** User contact list dto. */
    private List<ContactDTO> contactList;
    /** User bank list. */
    private List<BankLightDTO> bankList;
}
