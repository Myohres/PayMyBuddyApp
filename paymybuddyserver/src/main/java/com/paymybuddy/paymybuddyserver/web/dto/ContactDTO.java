package com.paymybuddy.paymybuddyserver.web.dto;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ContactDTO {
    /** Contact id. */
    private Long id;
    /** Contact lastname. */
    private String lastName;
    /** Contact firstname. */
    private String firstName;
    /** Contact email. */
    private String email;

}