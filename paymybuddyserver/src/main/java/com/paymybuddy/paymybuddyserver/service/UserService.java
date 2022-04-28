package com.paymybuddy.paymybuddyserver.service;

import com.paymybuddy.paymybuddyserver.data.entity.BankEntity;
import com.paymybuddy.paymybuddyserver.data.entity.UserEntity;
import com.paymybuddy.paymybuddyserver.data.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserService {

    /** User repository layer. */
    @Autowired
    private UserRepository userRepository;

    /** Bank Mapper layer. */
    @Autowired
    private BankService bankService;

    /**
     * Get user by email.
     * @param email string email
     * @return UserEntity
     * @throws NoSuchElementException no user found
     */
    public UserEntity getUserByEmail(final String email)
            throws NoSuchElementException {
        Optional<UserEntity> optionalUserEntity =
                userRepository.findUserEntityByEmail(email);
        return optionalUserEntity.orElseThrow(()
                -> new NoSuchElementException("No user with email " + email));
    }

    /**
     * Get user by Id.
     * @param id user id
     * @return userEntity
     */
    public UserEntity getUserById(final long id) {
        Optional<UserEntity> optionalUserEntity =
                userRepository.findById(id);
        return optionalUserEntity.orElseThrow(()
                -> new NoSuchElementException("No user " + id));
    }

    /**
     * Get user by email and password.
     * @param login email
     * @param password password
     * @return userEntity
     */
    public UserEntity getUserByEmailAndPassword(
            final String login,
            final String password) {
        return userRepository.findByEmailAndPassword(login, password)
                .orElseThrow(() -> new NoSuchElementException(
                        "Email or Password incorrect"));
    }

    /**
     * Add user in DB.
     * @param userEntity user
     * @return UserEntity added
     */
    public UserEntity addUser(final UserEntity userEntity) {
        return userRepository.save(userEntity);
    }

    /**
     * Add a contact to an user.
     * @param userId user id
     * @param contact contact to add
     * @return UserEntity updated with contact
     */
    public UserEntity addContact(final Long userId, final UserEntity contact) {
        UserEntity userEntity = getUserById(userId);
        userEntity.getContactList().add(contact);
        return userRepository.save(userEntity);
    }

    /**
     * add a bank to an user.
     * @param userId user id
     * @param bank bank to add
     * @return UserEntity updated with bank
     */
    public UserEntity addBank(final Long userId, final BankEntity bank) {
        UserEntity userEntity = getUserById(userId);
        bankService.addBank(bank);
        BankEntity bank2 = bankService.getBankByAccountNumber(
                bank.getAccountNumber());
        userEntity.getBankList().add(bank2);
        return userRepository.save(userEntity);
    }

    /**
     * Decrease user amount.
     * @param user user
     * @param transferAmount amount to decrease
     * @param commissionRate commission rate
     */
    public void sendMoney(final UserEntity user,
                          final double transferAmount,
                          final double commissionRate) {
        double newAmount = user.getAmount()
                - (transferAmount + (transferAmount * commissionRate));
        user.setAmount(newAmount);
        userRepository.save(user);
    }

    /**
     * Increase user amount.
     * @param user user
     * @param transferAmount amount to increase
     */
    public void receivedMoney(final UserEntity user,
                              final double transferAmount) {
        double newAmount = user.getAmount() + transferAmount;
        user.setAmount(newAmount);
        userRepository.save(user);
    }

    /**
     * Verify availability email.
     * @param email string email
     * @return true (email is free) false (email taken)
     */
    public boolean isEmailTaken(final String email) {
        return userRepository.existsByEmail(email);
    }

    /**
     * Delete user by id in DB.
     * @param userId user id.
     */
    public void deleteUser(final Long userId) {
        UserEntity user = getUserById(userId);
        userRepository.delete(user);
    }

    /**
     * Delete a contact to an user.
     * @param userId user id
     * @param email contact id
     */
    public void deleteContact(final Long userId,
                              final String email) {
        UserEntity user =  getUserById(userId);
        UserEntity contact = getUserByEmail(email);
        List<UserEntity> userEntityList = user.getContactList();
        userEntityList.remove(contact);
        user.setContactList(userEntityList);
        userRepository.save(user);
    }

    /**
     * Delete a bank to an user.
     * @param userId user id
     * @param accountNumber account number
     */
    public void deleteBank(final Long userId,
                           final String accountNumber) {
        UserEntity user = getUserById(userId);
        BankEntity bank = bankService.getBankByAccountNumber(accountNumber);
        List<BankEntity> bankList = user.getBankList();
        bankList.remove(bank);
        user.setBankList(bankList);
        bankService.deleteBank(bank);
        userRepository.save(user);
    }
}
