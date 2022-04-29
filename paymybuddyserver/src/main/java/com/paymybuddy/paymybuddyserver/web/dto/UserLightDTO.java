package com.paymybuddy.paymybuddyserver.web.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLightDTO {
    /** User id.*/
    private long id;
    /** User lastname.*/
    private String lastName;
    /** User firstname.*/
    private String firstName;
    /** User mail. */
    private String email;
}
