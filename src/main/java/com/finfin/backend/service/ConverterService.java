package com.finfin.backend.service;
import com.finfin.backend.entity.Transaction;
import com.finfin.backend.entity.User;
import org.modelmapper.Converter;
import com.finfin.backend.entity.Category;

import org.modelmapper.spi.MappingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConverterService {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserService userService;

    @Autowired
    private TransactionService transactionService;

    public Converter<Long, Category> idToCategoryConverter = new Converter<>() {
        @Override
        public Category convert(MappingContext<Long, Category> context) {
            if (context.getSource() == null) {
                return null;
            }

            return categoryService.findById(context.getSource());
        }
    };

    public Converter<Long, Transaction> idToTransactionConverter = new Converter<>() {
        @Override
        public Transaction convert(MappingContext<Long, Transaction> context) {
            if (context.getSource() == null) {
                return null;
            }

            return transactionService.findById(context.getSource());
        }
    };

    public Converter<Long, User> idToUserConverter = new Converter<Long, User>() {
        @Override
        public User convert(MappingContext<Long, User> context) {
            if(context.getSource() == null){
                return null;
            }

            return userService.findById(context.getSource());
        }
    };



}
