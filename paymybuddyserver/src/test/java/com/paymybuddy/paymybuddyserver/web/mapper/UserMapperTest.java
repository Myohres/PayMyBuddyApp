package com.paymybuddy.paymybuddyserver.web.mapper;

import com.paymybuddy.paymybuddyserver.data.entity.BankEntity;
import com.paymybuddy.paymybuddyserver.data.entity.UserEntity;
import com.paymybuddy.paymybuddyserver.web.dto.UserDTO;
import com.paymybuddy.paymybuddyserver.web.dto.UserLightDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class UserMapperTest {

    @InjectMocks
    private UserMapper userMapper;

    @Test
    void convertEntityToDTO() {
        List<UserEntity> contactList = new ArrayList<>();
        UserEntity contact = new UserEntity();
        contact.setId(1L);
        contact.setLastName("lastNameC");
        contact.setFirstName("firstNameC");
        contact.setEmail("contactMail");
        contactList.add(contact);
        List<BankEntity> bankList = new ArrayList<>();
        BankEntity bank = new BankEntity();
        bank.setId(1L);
        bankList.add(bank);
        UserEntity user = new UserEntity();
        user.setId(1L);
        user.setFirstName("firstName");
        user.setLastName("lastName");
        user.setEmail("email");
        user.setPassword("password");
        user.setAmount(15);
        user.setContactList(contactList);
        user.setBankList(bankList);
        UserDTO userDTO = userMapper.convertEntityToDTO(user);
        assertEquals(1L, userDTO.getId());
        assertEquals("lastName", userDTO.getLastName());
        assertEquals("firstName", userDTO.getFirstName());
        assertEquals("email", userDTO.getEmail());
        assertEquals("password", userDTO.getPassword());
        assertEquals(15, userDTO.getAmount());
        assertEquals(1, userDTO.getContactList().get(0).getId());
        assertEquals("lastNameC", userDTO.getContactList().get(0).getLastName());
        assertEquals("firstNameC", userDTO.getContactList().get(0).getFirstName());
        assertEquals("contactMail", userDTO.getContactList().get(0).getEmail());
    }

    @Test
    void convertEntityToLightDTO() {
        UserEntity user = new UserEntity();
        user.setId(1L);
        user.setLastName("lastName");
        user.setFirstName("firstName");
        user.setEmail("mail");
        UserLightDTO userLightDTO = userMapper.convertEntityToLightDTO(user);
        assertEquals("firstName", userLightDTO.getFirstName());
        assertEquals("lastName", userLightDTO.getLastName());
        assertEquals("mail", userLightDTO.getEmail());
    }
}
