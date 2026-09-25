package com.finfin.backend.config;

import com.finfin.backend.dto.app.category.CategoryDTOResponse;
import com.finfin.backend.dto.app.category.CategoryDTORequest;
import com.finfin.backend.dto.auth.register.RegisterDTORequest;
import com.finfin.backend.entity.Category;
import com.finfin.backend.entity.User;
import com.finfin.backend.service.ConverterService;
import org.modelmapper.ModelMapper;

import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    // Detalhado mais na classe correspondente, mas esse conversor é uma interface do modelmapper que
    // deixa vc detalhar oq fazer em algum caso de converter tipo x pra y
    // como se tivesse implementando um setter, mas automático
    @Autowired
    ConverterService converter;

    @Bean
    public ModelMapper createModelMapper(){
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);



        //addMapping recebe uma fonte e um destino, portanto o primeiro argumento é um getter e o segundo um setter
        //DTO de criar categoria com Categoria
        modelMapper.createTypeMap(CategoryDTORequest.class, Category.class)
                .addMappings(
                        mapper -> mapper
                                .using(converter.idToCategoryConverter)
                                .map(CategoryDTORequest::getOwnerId, Category::setOwner));


        //Categoria com DTO de resposta
        modelMapper.createTypeMap(Category.class, CategoryDTOResponse.class);

        modelMapper.createTypeMap(RegisterDTORequest.class, User.class)
                .addMapping(RegisterDTORequest::getPasswd, User::setHashedPassword);;

        return modelMapper;
    }
}
