package com.paymybuddy.paymybuddyserver.service;

import com.paymybuddy.paymybuddyserver.data.entity.BankEntity;
import com.paymybuddy.paymybuddyserver.data.entity.UserEntity;
import com.paymybuddy.paymybuddyserver.data.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @Mock
    BankService bankService;

    @InjectMocks
    UserService userService;

    @Test
    void getUserByEmail() {
        when(userRepository.findUserEntityByEmail(any())).thenReturn(Optional.of(new UserEntity()));
        userService.getUserByEmail("");
        verify(userRepository,times(1)).findUserEntityByEmail(any());
    }

    @Test
    void getUserByEmailNotFound() {
        when(userRepository.findUserEntityByEmail(any())).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> userService.getUserByEmail(any()));
    }

    @Test
    void getUserById() {
        when(userRepository.findById(any())).thenReturn(Optional.of(new UserEntity()));
        userService.getUserById(1L);
        verify(userRepository,times(1)).findById(any());
    }

    @Test
    void getUserByIdNotFound() {
        when(userRepository.findById(any())).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> userService.getUserById(1L));
    }

    @Test
    void getUserByEmailAndPassword() {
        UserEntity user = new UserEntity();
        user.setEmail("emailTest");
        when((userRepository.findByEmailAndPassword(any(),any()))).thenReturn(Optional.of(user));
        UserEntity userTest = userService.getUserByEmailAndPassword(any(),anyString());
        assertEquals("emailTest",userTest.getEmail());
    }

    @Test
    void getUserByEmailAndPasswordNotFound() {
        when(userRepository.findByEmailAndPassword(any(),any())).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> userService.getUserByEmailAndPassword(any(),any()));
    }

    @Test
    void addUser() {
        when(userRepository.save(any())).thenReturn(new UserEntity());
        userService.addUser(new UserEntity());
        verify(userRepository,times(1)).save(any());
    }

    @Test
    void addContact() {
        UserEntity user = new UserEntity();
        List<UserEntity> contactList = new ArrayList<>();
        user.setContactList(contactList);
        UserEntity contact = new UserEntity();
        contact.setFirstName("Myohres");
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);
        userService.addContact(1L,contact);
        assertEquals("Myohres",user.getContactList().get(0).getFirstName());
        verify(userRepository,times(1)).save(any());
    }

    @Test
    void addBank() {
        UserEntity user = new UserEntity();
        List<BankEntity> bankList = new ArrayList<>();
        user.setBankList(bankList);
        BankEntity bank = new BankEntity();
        bank.setName("Myohres");
        bank.setAccountNumber("55");
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(bankService.addBank(bank)).thenReturn(bank);
        when(bankService.getBankByAccountNumber("55")).thenReturn(bank);
        when(userRepository.save(user)).thenReturn(user);
        userService.addBank(1L,bank);
        assertEquals("Myohres",user.getBankList().get(0).getName());
        verify(userRepository,times(1)).save(any());
        assertEquals(1,user.getBankList().size());
    }

    @Test
    void sendMoney() {
        UserEntity user = new UserEntity();
        user.setAmount(100);
        when(userRepository.save(user)).thenReturn(user);
        userService.sendMoney(user,10,0.5);
        assertEquals(85,user.getAmount());
        verify(userRepository,times(1)).save(any());
    }

    @Test
    void receivedMoney() {
        UserEntity user = new UserEntity();
        user.setAmount(100);
        when(userRepository.save(user)).thenReturn(user);
        userService.receivedMoney(user,10);
        assertEquals(110,user.getAmount());
        verify(userRepository,times(1)).save(any());
    }

    @Test
    void isEmailTakenTrue() {
        when(userRepository.existsByEmail(any())).thenReturn(true);
        assertTrue(userService.isEmailTaken(""));
    }

    @Test
    void isEmailTakenFalse() {
        when(userRepository.existsByEmail(any())).thenReturn(false);
        assertFalse(userService.isEmailTaken(""));
    }

    @Test
    void deleteUser() {
        UserEntity user = new UserEntity();
        user.setId(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        userService.deleteUser(1L);
        verify(userRepository, times(1)).delete(user);
    }

    @Test
    void deleteContact() {
        UserEntity user = new UserEntity();
        List<UserEntity> contactList = new ArrayList<>();
        UserEntity contact = new UserEntity();
        contactList.add(contact);
        user.setContactList(contactList);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.findUserEntityByEmail("mail")).thenReturn(Optional.of(contact));
        userService.deleteContact(1L, "mail");
        verify(userRepository,times(1)).save(user);
        assertEquals(0,user.getContactList().size());
    }

    @Test
    void deleteBank() {
        UserEntity user = new UserEntity();
        List<BankEntity> bankList = new ArrayList<>();
        BankEntity bank = new BankEntity();
        bank.setAccountNumber("55");
        bankList.add(bank);
        user.setBankList(bankList);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(bankService.getBankByAccountNumber("55")).thenReturn(bank);
        userService.deleteBank(1L,"55");
        verify(userRepository,times(1)).save(user);
        assertEquals(0,user.getBankList().size());
    }

}
