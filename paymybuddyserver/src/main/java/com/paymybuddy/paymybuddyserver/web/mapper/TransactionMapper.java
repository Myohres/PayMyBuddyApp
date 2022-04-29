package com.paymybuddy.paymybuddyserver.web.mapper;

import com.paymybuddy.paymybuddyserver.data.entity.TransactionBankEntity;
import com.paymybuddy.paymybuddyserver.data.entity.TransactionContactEntity;
import com.paymybuddy.paymybuddyserver.data.entity.TransactionUserEntity;
import com.paymybuddy.paymybuddyserver.web.dto.TransactionALLDTO;
import com.paymybuddy.paymybuddyserver.web.dto.TransactionBankDTO;
import com.paymybuddy.paymybuddyserver.web.dto.TransactionContactDTO;
import com.paymybuddy.paymybuddyserver.web.dto.TransactionUserDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TransactionMapper {

    /** User Mapper layer. */
    private final UserMapper userMapper = new UserMapper();
    /** Bank Mapper layer. */
    private final BankMapper bankMapper = new BankMapper();

    /**
     * Convert extern transaction entity to DTO.
     * @param tranBankEntity extern transaction
     * @return extern transaction dto
     */
    public TransactionBankDTO convertTranBankToTranBankDTO(
            final TransactionBankEntity tranBankEntity) {
        TransactionBankDTO tranBankDTO = new TransactionBankDTO();
        tranBankDTO.setId(tranBankEntity.getId());
        tranBankDTO.setDate(tranBankEntity.getDate());
        tranBankDTO.setUser(userMapper.convertEntityToLightDTO(
                tranBankEntity.getUser()));
        tranBankDTO.setBank(bankMapper.convertEntityToDTO(
                tranBankEntity.getBank()));
        tranBankDTO.setAmountTransaction(tranBankEntity.getAmountTransaction());
        tranBankDTO.setAmountCommission(tranBankEntity.getAmountCommission());
        tranBankDTO.setDescription(tranBankEntity.getDescription());
        return tranBankDTO;
    }

    /**
     * Convert transaction Entity to DTO.
     * @param tranContactEntity transaction
     * @return transaction DTO
     */
    public TransactionContactDTO convertTranContactToTranContactDTO(
            final TransactionContactEntity tranContactEntity) {
        TransactionContactDTO tranContactDTO = new TransactionContactDTO();
        tranContactDTO.setId(
                tranContactEntity.getId());
        tranContactDTO.setDate(
                tranContactEntity.getDate());
        tranContactDTO.setSender(userMapper.convertEntityToLightDTO(
                tranContactEntity.getSender()));
        tranContactDTO.setRecipient(userMapper.convertEntityToLightDTO(
                tranContactEntity.getContact()));
        tranContactDTO.setAmount(
                tranContactEntity.getAmountTransaction());
        tranContactDTO.setAmountCommission(
                tranContactEntity.getAmountCommission());
        tranContactDTO.setDescription(tranContactEntity.getDescription());
        return tranContactDTO;
    }

    /**
     * Convert extern transaction entity to DTO.
     * @param tranUserEntity extern transaction
     * @return extern transaction dto
     */
    public TransactionUserDTO convertTranUserToTranUserDTO(
            final TransactionUserEntity tranUserEntity) {
        TransactionUserDTO tranUserDTO = new TransactionUserDTO();
        tranUserDTO.setId(tranUserEntity.getId());
        tranUserDTO.setDate(tranUserEntity.getDate());
        tranUserDTO.setUser(userMapper.convertEntityToLightDTO(
                tranUserEntity.getUser()));
        tranUserDTO.setBank(bankMapper.convertEntityToDTO(
                tranUserEntity.getBank()));
        tranUserDTO.setAmountTransaction(tranUserEntity.getAmountTransaction());
        tranUserDTO.setAmountCommission(tranUserEntity.getAmountCommission());
        tranUserDTO.setDescription(tranUserEntity.getDescription());
        return tranUserDTO;
    }

    /**
     * Mapper TransactionUserEntity to DTO.
     * @param tranUserEntity transactionUserEntity
     * @return transactionDTO
     */
    public TransactionALLDTO convertTranUserToALLDTO(
            final TransactionUserEntity tranUserEntity) {
        TransactionALLDTO tranDTO = new TransactionALLDTO();
        tranDTO.setDate(tranUserEntity.getDate());
        tranDTO.setSenderName1(tranUserEntity.getBank().getName());
        tranDTO.setSenderName2(tranUserEntity.getBank().getAccountNumber());
        tranDTO.setRecipientName1(tranUserEntity.getUser().getLastName());
        tranDTO.setRecipientName2(tranUserEntity.getUser().getFirstName());
        tranDTO.setAmountTransaction(tranUserEntity.getAmountTransaction());
        tranDTO.setAmountCommission(tranUserEntity.getAmountCommission());
        tranDTO.setDescription(tranUserEntity.getDescription());
        return tranDTO;
    }

    /**
     * Mapper TransactionBankEntity to DTO.
     * @param tranBankEntity transactionBankEntity
     * @return transactionDTO
     */
    public TransactionALLDTO convertTranBankToALLDTO(
            final TransactionBankEntity tranBankEntity) {
        TransactionALLDTO tranDTO = new TransactionALLDTO();
        tranDTO.setDate(tranBankEntity.getDate());
        tranDTO.setSenderName1(tranBankEntity.getUser().getLastName());
        tranDTO.setSenderName2(tranBankEntity.getUser().getFirstName());
        tranDTO.setRecipientName1(tranBankEntity.getBank().getName());
        tranDTO.setRecipientName2(tranBankEntity.getBank().getAccountNumber());
        tranDTO.setAmountTransaction(tranBankEntity.getAmountTransaction());
        tranDTO.setDescription(tranBankEntity.getDescription());
        tranDTO.setAmountCommission(tranBankEntity.getAmountCommission());
        return tranDTO;
    }

    /**
     * Mapper TransactionContactEntity to DTO.
     * @param tranContactEntity transactionContactEntity
     * @return transactionDTO
     */
    public TransactionALLDTO convertTranContactToALLDTO(
            final TransactionContactEntity tranContactEntity) {
        TransactionALLDTO tranDTO = new TransactionALLDTO();
        tranDTO.setDate(tranContactEntity.getDate());
        tranDTO.setSenderName1(
                tranContactEntity.getSender().getLastName());
        tranDTO.setSenderName2(
                tranContactEntity.getSender().getFirstName());
        tranDTO.setRecipientName1(
                tranContactEntity.getContact().getLastName());
        tranDTO.setRecipientName2(
                tranContactEntity.getContact().getFirstName());
        tranDTO.setAmountTransaction(tranContactEntity.getAmountTransaction());
        tranDTO.setAmountCommission(tranContactEntity.getAmountCommission());
        tranDTO.setDescription(tranContactEntity.getDescription());
        return tranDTO;
    }

    /**
     * Convert all List type transaction to one List allDTO transaction.
     * @param tranBankEntityList transaction Bank
     * @param tranContactRecipientEntityList transaction Contact recipient
     * @param tranContactSenderEntityList transaction contact sender
     * @param tranUserEntityList transaction user
     * @return Transaction all DTO list
     */
    public List<TransactionALLDTO> convertListTranToListAllDTO(
            final List<TransactionBankEntity> tranBankEntityList,
            final List<TransactionContactEntity> tranContactRecipientEntityList,
            final List<TransactionContactEntity> tranContactSenderEntityList,
            final List<TransactionUserEntity> tranUserEntityList) {
        List<TransactionALLDTO> transactionALLDTOList = new ArrayList<>();
        tranBankEntityList
                .forEach(data -> transactionALLDTOList.add(
                        convertTranBankToALLDTO(data)));
        tranContactRecipientEntityList
                .forEach(data -> transactionALLDTOList.add(
                        convertTranContactToALLDTO(data)));
        tranContactSenderEntityList
                .forEach(data -> transactionALLDTOList.add(
                        convertTranContactToALLDTO(data)));
        tranUserEntityList
                .forEach(data -> transactionALLDTOList.add(
                        convertTranUserToALLDTO(data)));

        transactionALLDTOList.stream().sorted((o1, o2) -> {
            if (o1.getDate().before(o2.getDate())) {
                return 1;
            } else if (o1.getDate().after(o2.getDate())) {
                return -1;
            }
            return 0;
        }).close();
        return transactionALLDTOList;
    }

}
