package com.finfin.backend.service;

import com.finfin.backend.entity.Transaction;
import com.finfin.backend.entity.User;
import com.finfin.backend.entity.Wallet;
import com.finfin.backend.entity.WalletMember;
import org.modelmapper.Converter;
import com.finfin.backend.entity.Category;

import org.modelmapper.spi.MappingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Service
public class ConverterService {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserService userService;

    @Autowired
    private WalletService walletService;

    @Autowired
    private TransactionService transactionService;


    public Converter<Collection<? extends GrantedAuthority>, List<String>> authoritiesToStrings = new Converter<>() {
        @Override
        public List<String> convert(MappingContext<Collection<? extends GrantedAuthority>, List<String>> context) {
            if (context.getSource() == null) {
                return List.of();
            }

            return context.getSource().stream()
                    .map(GrantedAuthority::getAuthority)
                    .toList();
        }
    };

    public Converter<Long, Category> idToCategoryConverter = new Converter<>() {
        @Override
        public Category convert(MappingContext<Long, Category> context) {
            if (context.getSource() == null) {
                return null;
            }

            return categoryService.findById(context.getSource());
        }
    };

    public Converter<UUID, Wallet> idToWalletConverter = new Converter<>() {
        @Override
        public Wallet convert(MappingContext<UUID, Wallet> context) {
            if (context.getSource() == null) {
                return null;
            }

            return walletService.findById(context.getSource());
        }
    };

    public final Converter<Collection<WalletMember>, List<String>> membersToEmailsConverter =
            ctx -> ctx.getSource() == null
                    ? List.of()
                    : ctx.getSource().stream()
                    .map(wm -> wm.getUser().getEmail())
                    .toList();

    public Converter<User, String> userToNameConverter = new Converter<>() {

        @Override
        public String convert(MappingContext<User, String> context) {
            if (context.getSource() == null) {
                return "";
            }

            return context.getSource().getName();
        }
    };
    

}
