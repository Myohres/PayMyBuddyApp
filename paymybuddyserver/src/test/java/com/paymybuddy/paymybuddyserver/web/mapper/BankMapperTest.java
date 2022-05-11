package com.paymybuddy.paymybuddyserver.web.mapper;

import com.paymybuddy.paymybuddyserver.data.entity.BankEntity;
import com.paymybuddy.paymybuddyserver.web.dto.BankLightDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class BankMapperTest {

    BankEntity bank;

    @InjectMocks
    private BankMapper bankMapper;

    @BeforeEach
    public void setUp(){
        bank = new BankEntity();
        bank.setId(1L);
        bank.setName("bankName");
        bank.setAccountNumber("55");
    }

    @Test
    void convertBankDTO() {
        BankLightDTO bankLightDTO = bankMapper.convertEntityToDTO(bank);
        assertEquals(1L,bankLightDTO.getId());
        assertEquals("55", bankLightDTO.getAccountNumber());
        assertEquals("bankName",bankLightDTO.getName());
    }

    @Test
    void convertListEntityToListDTO() {
        List<BankEntity> bankEntityList = new ArrayList<>();
        bankEntityList.add(bank);
        List<BankLightDTO> bankLightDTOList =
                bankMapper.convertListEntityToListDTO(bankEntityList);
        assertEquals(1,bankLightDTOList.size());
    }
}
