package com.paymybuddy.paymybuddyserver.web.controller;

import com.paymybuddy.paymybuddyserver.data.entity.BankEntity;
import com.paymybuddy.paymybuddyserver.data.entity.UserEntity;
import com.paymybuddy.paymybuddyserver.service.BankService;
import com.paymybuddy.paymybuddyserver.service.UserService;
import com.paymybuddy.paymybuddyserver.web.dto.BankLightDTO;
import com.paymybuddy.paymybuddyserver.web.dto.ContactDTO;
import com.paymybuddy.paymybuddyserver.web.dto.UserDTO;
import com.paymybuddy.paymybuddyserver.web.mapper.UserMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    UserEntity user;
    UserEntity contact;
    BankEntity bank;
    UserDTO userDTO;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private BankService bankService;

    @Mock
    private UserMapper userMapper;

    @BeforeEach
    private void setUpPerTest(){
        user = new UserEntity();
        user.setId(1L);
        user.setLastName("lastName");
        user.setFirstName("firstName");
        user.setEmail("mail");
        user.setPassword("password");
        user.setAmount(100);
        List<UserEntity> contactList = new ArrayList<>();
        user.setContactList(contactList);
        List<BankEntity> bankList = new ArrayList<>();
        user.setBankList(bankList);

        contact = new UserEntity();
        contact.setId(2L);
        contact.setLastName("lastName2");
        contact.setFirstName("firstName2");
        contact.setEmail("mail2");
        contact.setPassword("password2");
        List<UserEntity> contactList2 = new ArrayList<>();
        contact.setContactList(contactList2);
        List<BankEntity> bankList2 = new ArrayList<>();
        contact.setBankList(bankList2);

        bank = new BankEntity();
        bank.setId(1L);
        bank.setAmount(500);
        bank.setAccountNumber("5555");
        bank.setName("bankName");

        userDTO = new UserDTO();
        List<ContactDTO> contactDTOList = new ArrayList<>();
        userDTO.setContactList(contactDTOList);
        List<BankLightDTO> bankLightDTOList = new ArrayList<>();
        userDTO.setBankList(bankLightDTOList);

    }

    @Test
    void getUserWithEmail() throws Exception {
        when((userService.getUserByEmail("mail"))).thenReturn(user);
        mockMvc.perform(get("/user/email/mail")).andExpect(status().isOk());
    }

    @Test
    void getUserWithEmail_ShouldReturnNotFound() throws Exception {
        when((userService.getUserByEmail("mail"))).thenThrow(new NoSuchElementException());
        mockMvc.perform(get("/user/email/mail")).andExpect(status().isForbidden());
    }

    @Test
    void getUserById() throws Exception {
        when(userService.getUserById(1L)).thenReturn(user);
        mockMvc.perform(get("/user/id/1")).andExpect(status().isOk());
    }

    @Test
    void getUserById_ShouldReturnNotFound() throws Exception {
        when(userService.getUserById(1L)).thenThrow(new NoSuchElementException());
        mockMvc.perform(get("/user/id/1")).andExpect(status().isNotFound());
    }

    @Test
    void emailIsTaken() throws Exception {
        when(userService.isEmailTaken("mail")).thenReturn(true);
        mockMvc.perform(get("/user/taken/mail")).andExpect(status().isOk());
    }

    @Test
    void emailIsTaken_ShouldReturnNotFound() throws Exception {
        when(userService.isEmailTaken("mail")).thenThrow(new NoSuchElementException());
        mockMvc.perform(get("/user/taken/mail")).andExpect(status().isForbidden());
    }

    @Test
    void login() throws Exception {
        when(userService.getUserByEmailAndPassword("po45","410")).thenReturn(user);
        mockMvc.perform(post("/user/login")
                .contentType(MediaType.APPLICATION_JSON).content("{\n" +
                        "    \"login\" : \"po45\",\n" +
                        "    \"password\" : 410\n" +
                        "}"))
                .andExpect(status().isOk());
    }

    @Test
    void login_ShouldReturnNotFound() throws Exception {
        when(userService.getUserByEmailAndPassword("po45","410"))
                .thenThrow(new NoSuchElementException());
        mockMvc.perform(post("/user/login")
                .contentType(MediaType.APPLICATION_JSON).content("{\n" +
                        "    \"login\" : \"po45\",\n" +
                        "    \"password\" : 410\n" +
                        "}"))
                .andExpect(status().isForbidden());
    }

    @Test
    void addUser() throws Exception {
        when(userService.addUser(any())).thenReturn(user);
        mockMvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andExpect(status().isOk());
    }

    @Test
    void addUser_ShouldReturnNotFound() throws Exception {
        doThrow(new NoSuchElementException()).when(userService).addUser(any());
        mockMvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andExpect(status().isNotFound());
    }

    @Test
    void addContact() throws Exception {
        when(userService.addContact(any(),any())).thenReturn(user);
        mockMvc.perform(put("/user/id/1/contact")
                .contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andExpect(status().isOk());
    }

    @Test
    void addContact_ShouldReturnNotFound() throws Exception {
        when(userService.addContact(any(),any())).thenThrow(new NoSuchElementException());
        mockMvc.perform(put("/user/id/1/contact")
                .contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andExpect(status().isNotFound());
    }

    @Test
    void addBank() throws Exception {
        when(userService.addBank(any(),any())).thenReturn(user);
        mockMvc.perform(post("/user/id/1/bank")
                .contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andExpect(status().isOk());
    }


    @Test
    void addBank_ShouldReturnNotFound() throws Exception {
        when(userService.addBank(any(), any())).thenThrow(new NoSuchElementException());
        mockMvc.perform(post("/user/id/1/bank").contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteUser() throws Exception {
        when(userService.getUserById(1L)).thenReturn(user);
        mockMvc.perform(delete("/user/id/1"))
                .andExpect(status().isOk());
    }

    @Test
    void deleteUser_ShouldReturnNotFound() throws Exception {
        doThrow(new NoSuchElementException()).when(userService).deleteUser(1L);
        mockMvc.perform(delete("/user/id/1")).andExpect(status().isNotFound());
    }

    @Test
    void deleteContact() throws Exception {
        when(userService.getUserById(1L)).thenReturn(user);
        mockMvc.perform(delete("/user/id/1/contact/2"))
                .andExpect(status().isOk());
    }

    @Test
    void deleteContact_ShouldReturnNotFound() throws Exception {
        doThrow(new NoSuchElementException()).when(userService).deleteContact(1L,"mail");
        mockMvc.perform(delete("/user/id/1/contact/mail"))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteBank() throws Exception {
        when(userService.getUserById(1L)).thenReturn(user);
        mockMvc.perform(delete("/user/id/1/bank/5555")
                .contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andExpect(status().isOk());
    }

    @Test
    void deleteBank_ShouldReturnNotFound() throws Exception {
        doThrow(new NoSuchElementException()).when(userService).deleteBank(1L,"55555");
        mockMvc.perform(delete("/user/id/1/bank/55555")
                .contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andExpect(status().isNotFound());
    }
}
