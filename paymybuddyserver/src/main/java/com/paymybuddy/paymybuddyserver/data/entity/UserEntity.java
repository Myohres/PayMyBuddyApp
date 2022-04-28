package com.paymybuddy.paymybuddyserver.data.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "user")
public class UserEntity {
    /** User id.*/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /** User email.*/
    private String email;
    /** User password.*/
    private String password;
    /** User lastname.*/
    private String lastName;
    /** User firstname.*/
    private String firstName;
    /** User amount.*/
    private double amount;
    /** User contact list.*/
    @ManyToMany
    @JoinTable(name = "user_contact",
            joinColumns = @JoinColumn(
                    name = "user",
                    referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "contact",
                    referencedColumnName = "id")
    )
    private List<UserEntity> contactList;
    /** User bank list.*/
    @OneToMany
    @JoinTable(name = "user_bank",
            joinColumns = @JoinColumn(
                    name = "user",
                    referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "bank",
                    referencedColumnName = "id")
    )
    private List<BankEntity> bankList;
}
