package com.paymybuddy.paymybuddyserver.web.controller;

import com.paymybuddy.paymybuddyserver.data.entity.BankEntity;
import com.paymybuddy.paymybuddyserver.data.entity.UserEntity;
import com.paymybuddy.paymybuddyserver.service.UserService;
import com.paymybuddy.paymybuddyserver.web.dto.LoginRequestDTO;
import com.paymybuddy.paymybuddyserver.web.dto.UserDTO;
import com.paymybuddy.paymybuddyserver.web.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@Slf4j
@RestController
@RequestMapping("/user")
@CrossOrigin("http://localhost:4200")
public class UserController {
    /** User service layer. */
    @Autowired
    private UserService userService;

    /** User mapper layer.*/
    @Autowired
    private UserMapper userMapper;

    /** Error code.*/
    private final int error  = 403;


    /**
     * Get an user with his email.
     * @param email string
     * @return user
     */
    @GetMapping("/email/{email}")
    public ResponseEntity<UserDTO> getUserByEmail(
            @PathVariable("email") final String email) {
        log.info("GET/user/email/" + email);
        try {
            UserEntity userEntity = userService.getUserByEmail(email);
            return ResponseEntity.ok(
                    userMapper.convertEntityToDTO(userEntity));
        } catch (NoSuchElementException e) {
            log.error("getUserByEmail error : " + e.getMessage());
            return ResponseEntity.status(error).build();
        }
    }

    /**
     * Get User by id.
     * @param userId id user
     * @return User
     */
    @GetMapping("/id/{id}")
    public ResponseEntity<UserDTO> getUserById(
            @PathVariable("id") final Long userId) {
        log.info("GET/user/id/" + userId);
        try {
            UserEntity user = userService.getUserById(userId);
            return ResponseEntity.ok(userMapper.convertEntityToDTO(user));
        } catch (NoSuchElementException e) {
            log.error("getUserById error : " + e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Ask DB available email.
     * @param email string
     * @return true email is not free, false email is free
     */
    @GetMapping("/taken/{email}")
    public ResponseEntity<Boolean> emailIsTaken(
            @PathVariable("email") final String email) {
        log.info("GET/user/taken/" + email);
        try {
            return ResponseEntity.ok(userService.isEmailTaken(email));
        } catch (NoSuchElementException e) {
            log.error("emailIsTaken error : " + e.getMessage());
            return ResponseEntity.status(error).build();
        }
    }

    /**
     * Ask DB available email.
     * @param loginRequestDTO loginRequest
     * @return true email is not free, false email is free
     */
    @PostMapping("/login")
    public ResponseEntity<UserDTO> login(
            @RequestBody final LoginRequestDTO loginRequestDTO) {
        log.info("POST/user/login");
        try {
            return ResponseEntity.ok(
                    userMapper.convertEntityToDTO(
                            userService.getUserByEmailAndPassword(
                                    loginRequestDTO.getLogin(),
                                    loginRequestDTO.getPassword())));
        } catch (NoSuchElementException e) {
            log.error("login error : " + e.getMessage());
            return ResponseEntity.status(error).build();
        }
    }

    /**
     * Add user in DB.
     * @param userEntity userEntity
     * @return user in DB
     */
    @PostMapping("")
    public ResponseEntity<UserDTO> addUser(
            @RequestBody final UserEntity userEntity) {
        log.info("POST/user" + userEntity);
        try {
            return ResponseEntity.ok(
                    userMapper.convertEntityToDTO(
                            userService.addUser(userEntity)));
        } catch (NoSuchElementException e) {
            log.error("addUser error : " + e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Add contact.
     * @param userId id user
     * @param contact User to add at contact list
     * @return User updated
     */
    @PutMapping("/id/{userId}/contact")
    public ResponseEntity<UserDTO> addContact(
            @PathVariable("userId") final Long userId,
            @RequestBody final UserEntity contact) {
        log.info("PUT/user/id/" + userId + "/contact");
        try {
            return ResponseEntity.ok(
                    userMapper.convertEntityToDTO(
                            userService.addContact(userId, contact)));
        } catch (NoSuchElementException e) {
            log.error("AddContact error : " + e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Add bank to user.
     * @param userId user id
     * @param bank bank to add
     * @return user updated
     */
    @PostMapping("/id/{userId}/bank")
    public ResponseEntity<UserDTO> addBank(
            @PathVariable("userId") final Long userId,
            @RequestBody final BankEntity bank) {
        log.info("PUT/user/id/" + userId + "/bank");
        try {
            UserEntity user = userService.addBank(userId, bank);
            return ResponseEntity.ok(userMapper.convertEntityToDTO(user));
        } catch (NoSuchElementException e) {
            log.error("AddBank error : " + e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Delete contact.
     * @param userId id user
     * @param userEmail id contact
     * @return User updated
     */
    @DeleteMapping("/id/{userId}/contact/{userEmail}")
    public ResponseEntity<UserDTO> deleteContact(
            @PathVariable("userId") final Long userId,
            @PathVariable("userEmail") final String userEmail) {
        log.info("DEL/user/id/" + userId + "/contact/" + userEmail);
        try {
            userService.deleteContact(userId, userEmail);
            return ResponseEntity.ok(
                    userMapper.convertEntityToDTO(
                            userService.getUserById(userId)));
        } catch (NoSuchElementException e) {
            log.error("delete contact " + e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Delete bank user.
     * @param userId user id
     * @param accountNumber account number bank
     * @return user updated
     */
    @DeleteMapping ("/id/{userId}/bank/{acNumber}")
    public ResponseEntity<UserDTO> deleteBank(
            @PathVariable("userId") final Long userId,
            @PathVariable("acNumber") final String accountNumber) {
        log.info("DEL/user/id/" + userId + "/bank");
        try {
            userService.deleteBank(userId, accountNumber);
            return ResponseEntity.ok(
                    userMapper.convertEntityToDTO(
                            userService.getUserById(userId))
            );
        } catch (NoSuchElementException e) {
            log.error("delete bank " + e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Delete User.
     * @param userId user id
     * @return user deleted
     */
    @DeleteMapping("/id/{userId}")
    public ResponseEntity<?> deleteUser(
            @PathVariable("userId") final Long userId) {
        log.info("DEL/user/id/" + userId);
        try {
            userService.deleteUser(userId);
            return ResponseEntity.ok().build();
        } catch (NoSuchElementException e) {
            log.error("DeleteUser error " + e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
}

