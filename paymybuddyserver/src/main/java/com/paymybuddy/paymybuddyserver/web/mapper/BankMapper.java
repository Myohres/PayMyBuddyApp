package com.paymybuddy.paymybuddyserver.web.mapper;

import com.paymybuddy.paymybuddyserver.data.entity.BankEntity;
import com.paymybuddy.paymybuddyserver.web.dto.BankLightDTO;

import java.util.List;
import java.util.stream.Collectors;

public class BankMapper {

    /**
     * Convert BankEntity to BankLightDTO.
     * @param bankEntity bank
     * @return bankLightDTO
     */
    public BankLightDTO convertEntityToDTO(final BankEntity bankEntity) {
        BankLightDTO bankLightDTO = new BankLightDTO();
        bankLightDTO.setName(bankEntity.getName());
        bankLightDTO.setAccountNumber(bankEntity.getAccountNumber());
        return bankLightDTO;
    }

    /**
     * Convert BankEntity List to bankLightDTO List.
     * @param bankList bankEntityList
     * @return bankLightDTO List
     */
    public List<BankLightDTO> convertListEntityToListDTO(
            final List<BankEntity> bankList) {
        return bankList.stream()
                .map(this::convertEntityToDTO).collect(Collectors.toList());
    }
}

