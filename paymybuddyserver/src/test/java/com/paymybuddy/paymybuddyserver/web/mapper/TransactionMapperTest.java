package com.paymybuddy.paymybuddyserver.web.mapper;

import com.paymybuddy.paymybuddyserver.data.entity.*;
import com.paymybuddy.paymybuddyserver.web.dto.TransactionALLDTO;
import com.paymybuddy.paymybuddyserver.web.dto.TransactionBankDTO;
import com.paymybuddy.paymybuddyserver.web.dto.TransactionContactDTO;
import com.paymybuddy.paymybuddyserver.web.dto.TransactionUserDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class TransactionMapperTest {

    UserEntity user;
    UserEntity contact;
    BankEntity bank;
    Date date;
    TransactionBankEntity tranBankEntity;
    TransactionContactEntity tranContactEntity;
    TransactionContactEntity tranContactEntity2;
    TransactionUserEntity tranUserEntity;

    @InjectMocks
    TransactionMapper transactionMapper;

    @BeforeEach
    public void setUp() {
        user = new UserEntity();
        user.setId(1L);
        user.setLastName("userLastName");
        user.setFirstName("userFirstName");
        contact = new UserEntity();
        contact.setId(2L);
        contact.setLastName("contactLastName");
        contact.setFirstName("contactFirstName");
        bank = new BankEntity();
        bank.setId(1L);
        bank.setName("bankName");
        bank.setAccountNumber("5555");
        date = new Date();

        tranBankEntity = new TransactionBankEntity();
        tranBankEntity.setId(1L);
        tranBankEntity.setDate(date);
        tranBankEntity.setUser(user);
        tranBankEntity.setBank(bank);
        tranBankEntity.setAmountTransaction(10);
        tranBankEntity.setAmountCommission(0.5);
        tranBankEntity.setDescription("test");

        tranContactEntity = new TransactionContactEntity();
        tranContactEntity.setId(1L);
        tranContactEntity.setDate(date);
        tranContactEntity.setSender(user);
        tranContactEntity.setContact(contact);
        tranContactEntity.setAmountTransaction(10);
        tranContactEntity.setAmountCommission(0.5);
        tranContactEntity.setDescription("test");

        tranContactEntity2 = new TransactionContactEntity();
        tranContactEntity2.setId(1L);
        tranContactEntity2.setDate(date);
        tranContactEntity2.setSender(contact);
        tranContactEntity2.setContact(user);
        tranContactEntity2.setAmountTransaction(10);
        tranContactEntity2.setAmountCommission(0.5);
        tranContactEntity2.setDescription("test");

        tranUserEntity = new TransactionUserEntity();
        tranUserEntity.setId(1L);
        tranUserEntity.setDate(date);
        tranUserEntity.setUser(user);
        tranUserEntity.setBank(bank);
        tranUserEntity.setAmountTransaction(10);
        tranUserEntity.setAmountCommission(0.5);
        tranUserEntity.setDescription("test");
    }

    @Test
    void convertTranBankEntityToTranBankDTO() {
        TransactionBankDTO transactionBankDTO =
                transactionMapper.convertTranBankToTranBankDTO(tranBankEntity);
        assertEquals(1L,transactionBankDTO.getId());
        assertEquals(date, transactionBankDTO.getDate());
        assertEquals("userLastName", transactionBankDTO.getUser().getLastName());
        assertEquals("userFirstName", transactionBankDTO.getUser().getFirstName());
        assertEquals("5555",transactionBankDTO.getBank().getAccountNumber());
        assertEquals("bankName",transactionBankDTO.getBank().getName());
        assertEquals(10, transactionBankDTO.getAmountTransaction());
        assertEquals(0.5,transactionBankDTO.getAmountCommission());
        assertEquals("test", transactionBankDTO.getDescription());
    }
    @Test
    void convertTranContactEntityToTranContactDTO() {
        TransactionContactDTO tranContactDTO =
                transactionMapper.convertTranContactToTranContactDTO(tranContactEntity);
        assertEquals(1, tranContactDTO.getId());
        assertEquals(date, tranContactDTO.getDate());
        assertEquals("userLastName", tranContactDTO.getSender().getLastName());
        assertEquals("userFirstName", tranContactDTO.getSender().getFirstName());
        assertEquals("contactLastName", tranContactDTO.getRecipient().getLastName());
        assertEquals("contactFirstName", tranContactDTO.getRecipient().getFirstName());
        assertEquals(10, tranContactDTO.getAmount());
        assertEquals(0.5,tranContactDTO.getAmountCommission());
        assertEquals("test", tranContactDTO.getDescription());
    }
    @Test
    void convertTranUserEntityToTranUserDTO() {
        TransactionUserDTO tranDTO =
                transactionMapper.convertTranUserToTranUserDTO(tranUserEntity);
        assertEquals(1, tranDTO.getId());
        assertEquals(date,tranDTO.getDate());
        assertEquals("bankName", tranDTO.getBank().getName());
        assertEquals("5555", tranDTO.getBank().getAccountNumber());
        assertEquals(1, tranDTO.getUser().getId());
        assertEquals("userLastName",tranDTO.getUser().getLastName());
        assertEquals("userFirstName", tranDTO.getUser().getFirstName());
        assertEquals(10, tranDTO.getAmountTransaction());
        assertEquals(0.5,tranDTO.getAmountCommission());
        assertEquals("test", tranDTO.getDescription());
    }

    @Test
    void convertTranUserToALLDTO() {
        TransactionALLDTO tranDTO = transactionMapper.convertTranUserToALLDTO(tranUserEntity);
        assertEquals(date, tranDTO.getDate());
        assertEquals("bankName",tranDTO.getSenderName1());
        assertEquals("5555",tranDTO.getSenderName2());
        assertEquals("userLastName",tranDTO.getRecipientName1());
        assertEquals("userFirstName",tranDTO.getRecipientName2());
        assertEquals(10,tranDTO.getAmountTransaction());
        assertEquals(0.5, tranDTO.getAmountCommission());
        assertEquals("test",tranDTO.getDescription());
    }

    @Test
    void convertTranBankToALLDTO() {
        TransactionALLDTO tranDTO = transactionMapper.convertTranBankToALLDTO(tranBankEntity);
        assertEquals(date, tranDTO.getDate());
        assertEquals("userLastName",tranDTO.getSenderName1());
        assertEquals("userFirstName",tranDTO.getSenderName2());
        assertEquals("bankName",tranDTO.getRecipientName1());
        assertEquals("5555",tranDTO.getRecipientName2());
        assertEquals(10,tranDTO.getAmountTransaction());
        assertEquals(0.5, tranDTO.getAmountCommission());
        assertEquals("test",tranDTO.getDescription());
    }

    @Test
    void convertTranContactToALLDTO() {
        TransactionALLDTO tranDTO = transactionMapper.convertTranContactToALLDTO(tranContactEntity);
        assertEquals(date, tranDTO.getDate());
        assertEquals("userLastName",tranDTO.getSenderName1());
        assertEquals("userFirstName",tranDTO.getSenderName2());
        assertEquals("contactLastName",tranDTO.getRecipientName1());
        assertEquals("contactFirstName",tranDTO.getRecipientName2());
        assertEquals(10,tranDTO.getAmountTransaction());
        assertEquals(0.5, tranDTO.getAmountCommission());
        assertEquals("test",tranDTO.getDescription());
    }

    @Test
    void convertTranListToListAllDTO() {
        List<TransactionBankEntity> tranBankEntityList = new ArrayList<>();
        tranBankEntityList.add(tranBankEntity);
        List<TransactionContactEntity> tranContactRecipientEntityList = new ArrayList<>();
        tranContactRecipientEntityList.add(tranContactEntity2);
        List<TransactionContactEntity> tranContactSenderEntityList = new ArrayList<>();
        tranContactSenderEntityList.add(tranContactEntity);
        List <TransactionUserEntity> tranUserEntityList = new ArrayList<>();
        tranUserEntityList.add(tranUserEntity);
        List<TransactionALLDTO> tranAllDTOList = transactionMapper.convertListTranToListAllDTO(
                tranBankEntityList,tranContactRecipientEntityList,tranContactSenderEntityList,tranUserEntityList);
        assertEquals(4, tranAllDTOList.size());
    }
}
