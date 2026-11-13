package com.finfin.backend.config;

import com.finfin.backend.dto.app.category.CategoryDTOResponse;
import com.finfin.backend.dto.app.category.CategoryDTORequest;
import com.finfin.backend.dto.app.transaction.TransactionDTORequest;
import com.finfin.backend.dto.app.transaction.TransactionDTOResponse;
import com.finfin.backend.dto.app.user.UserDTOResponse;
import com.finfin.backend.dto.app.wallet.WalletDTORequest;
import com.finfin.backend.dto.app.wallet.WalletDTOResponse;
import com.finfin.backend.dto.auth.register.RegisterDTORequest;
import com.finfin.backend.entity.Category;
import com.finfin.backend.entity.Transaction;
import com.finfin.backend.entity.User;
import com.finfin.backend.entity.Wallet;
import com.finfin.backend.service.ConverterService;
import org.modelmapper.ModelMapper;

import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Autowired
    ConverterService converter;

    @Bean
    public ModelMapper createModelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        mapCategoryDTO(modelMapper);
        mapRegisterDTO(modelMapper);
        mapUserDTO(modelMapper);
        mapWalletDto(modelMapper);
        mapTransactionDTO(modelMapper);
        modelMapper.validate();
        return modelMapper;
    }

    private void mapUserDTO(ModelMapper modelMapper) {
        modelMapper.createTypeMap(User.class, UserDTOResponse.class)
                .addMappings(m ->
                        m.using(converter.authoritiesToStrings).map(User::getAuthorities, UserDTOResponse::setAuthorities));

        //No need for the other way around, email should be in JWT and the user can be found through that
    }

    private void mapWalletDto(ModelMapper modelMapper) {
        modelMapper.createTypeMap(WalletDTORequest.class, Wallet.class)
                .addMappings(m -> {
                    m.skip(Wallet::setId);
                    m.skip(Wallet::setOwner);
                    m.skip(Wallet::setMembers);
                    m.skip(Wallet::setTransactions);
                    m.skip(Wallet::setCreatedIn); // remove this line if the entity has no such setter
                });

// entity -> response
        modelMapper.createTypeMap(Wallet.class, WalletDTOResponse.class)
                .addMappings(m -> {
                    m.using(converter.userToNameConverter)
                            .map(Wallet::getOwner, WalletDTOResponse::setOwnerEmail);
                    m.using(converter.membersToEmailsConverter)
                            .map(Wallet::getMembers, WalletDTOResponse::setMemberEmails);
                });
    }

    private void mapCategoryDTO(ModelMapper modelMapper) {
        //addMapping recebe uma fonte e um destino, portanto o primeiro argumento é um getter e o segundo um setter
        //DTO de criar categoria com Categoria
        modelMapper.createTypeMap(CategoryDTORequest.class, Category.class)
                .addMappings(m -> {
                    m.skip(Category::setId);
                    m.skip(Category::setOwner);
                    m.skip(Category::setActive);
                });

        //Categoria com DTO de resposta
        modelMapper.createTypeMap(Category.class, CategoryDTOResponse.class);
    }

    private void mapTransactionDTO(ModelMapper modelMapper) {
        modelMapper.createTypeMap(TransactionDTORequest.class, Transaction.class)
                .addMappings(m -> {
                    m.skip(Transaction::setId);
                    m.skip(Transaction::setWallet);     // comes from the URL
                    m.skip(Transaction::setCategory);   // resolved and checked in the service
                    m.skip(Transaction::setCreatedBy);  // comes from the logged-in user
                    m.skip(Transaction::setCreatedIn);
                });

        modelMapper.createTypeMap(Transaction.class, TransactionDTOResponse.class)
                .addMappings(m -> {
                    m.map(t -> t.getWallet().getId(), TransactionDTOResponse::setWalletId);
                    m.map(t -> t.getCategory().getId(), TransactionDTOResponse::setCategoryId);
                    m.using(converter.userToNameConverter)
                            .map(Transaction::getCreatedBy, TransactionDTOResponse::setCreatedBy);
                });
    }


    private void mapRegisterDTO(ModelMapper modelMapper) {
        modelMapper.createTypeMap(RegisterDTORequest.class, User.class)
                .addMapping(RegisterDTORequest::getPasswd, User::setHashedPassword)
                .addMappings(m -> {
                    m.skip(User::setId);
                    m.skip(User::setAddress);
                    m.skip(User::setUserProfiles);
                    m.skip(User::setCreatedIn);
                    m.skip(User::setUpdatedIn);
                });
    }
}
