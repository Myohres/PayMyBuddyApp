package com.paymybuddy.paymybuddyserver.web.mapper;

import com.paymybuddy.paymybuddyserver.data.entity.UserEntity;
import com.paymybuddy.paymybuddyserver.web.dto.ContactDTO;
import com.paymybuddy.paymybuddyserver.web.dto.UserDTO;
import com.paymybuddy.paymybuddyserver.web.dto.UserLightDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    /** Bank Mapper layer. */
    private final BankMapper bankMapper = new BankMapper();

    /**
     * Convert userEntity to DTO.
     * @param userEntity user
     * @return userDTO
     */
    public UserDTO convertEntityToDTO(final UserEntity userEntity) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(userEntity.getId());
        userDTO.setEmail(userEntity.getEmail());
        userDTO.setPassword(userEntity.getPassword());
        userDTO.setLastName(userEntity.getLastName());
        userDTO.setFirstName(userEntity.getFirstName());
        userDTO.setAmount(userEntity.getAmount());
        userDTO.setBankList(bankMapper
                .convertListEntityToListDTO(userEntity.getBankList()));
        List<UserEntity> userEntityList = userEntity.getContactList();
        List<ContactDTO> contactDTOList =
                userEntityList.stream()
                        .map(userEntity1 -> {
                            ContactDTO contactDTO = new ContactDTO();
                            contactDTO.setId(userEntity1.getId());
                            contactDTO.setEmail(userEntity1.getEmail());
                            contactDTO.setLastName(userEntity1.getLastName());
                            contactDTO.setFirstName(userEntity1.getFirstName());
                            return contactDTO;
                        }).collect(Collectors.toList());
        userDTO.setContactList(contactDTOList);
        return userDTO;
    }

    /**
     * Convert user entity to DTO.
     * @param userEntity user
     * @return userLightDTO
     */
    public UserLightDTO convertEntityToLightDTO(
            final UserEntity userEntity) {
        UserLightDTO userLightDTO = new UserLightDTO();
        userLightDTO.setId(userEntity.getId());
        userLightDTO.setLastName(userEntity.getLastName());
        userLightDTO.setFirstName(userEntity.getFirstName());
        userLightDTO.setEmail(userEntity.getEmail());
        return userLightDTO;
    }
}

