package com.finfin.backend.service;

import com.finfin.backend.entity.Category;
import com.finfin.backend.entity.Transaction;
import com.finfin.backend.entity.User;
import com.finfin.backend.entity.Wallet;
import com.finfin.backend.entity.enums.TransactionType;
import com.finfin.backend.exception.ResourceNotFoundException;
import com.finfin.backend.repository.CategoryRepository;
import com.finfin.backend.repository.TransactionRepository;
import com.finfin.backend.security.UserAuthProvider;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TransactionService {

    @Autowired
    TransactionRepository repository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    WalletService walletService;
    @Autowired
    UserAuthProvider authProvider;

    public List<Transaction> list(UUID walletId) {
        walletService.findAccessible(walletId);
        return repository.findByWalletIdOrderByDateTransactionDescIdDesc(walletId);
    }

    private Transaction findInWallet(UUID walletId, Long id) {
        return repository.findByIdAndWalletId(id, walletId)
                .orElseThrow(() -> new ResourceNotFoundException("Transação não encontrada"));
    }

    private Category resolveCategory(Long categoryId, TransactionType type, User user) {
        Category category = categoryRepository.findByIdAndOwnerId(categoryId, user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada"));
        if (category.getType() != type) {
            throw new IllegalArgumentException("O tipo da transação deve ser igual ao tipo da categoria");
        }
        return category;
    }

    public Transaction get(UUID walletId, Long id) {
        walletService.findAccessible(walletId);
        return findInWallet(walletId, id);
    }

    public Transaction insert(UUID walletId, Long categoryId, @NonNull Transaction t) {
        Wallet wallet = walletService.findWritable(walletId);
        User current = authProvider.getAuthenticatedUser();

        t.setWallet(wallet);
        t.setCreatedBy(current);
        t.setCategory(resolveCategory(categoryId, t.getType(), current));
        return repository.save(t);
    }

    public Transaction update(UUID walletId, Long id, Long categoryId, @NonNull Transaction changes) {
        walletService.findWritable(walletId);
        Transaction tdb = findInWallet(walletId, id);

        tdb.setType(changes.getType());
        tdb.setValue(changes.getValue());
        tdb.setDescription(changes.getDescription());
        tdb.setDateTransaction(changes.getDateTransaction());
        tdb.setCategory(resolveCategory(categoryId, changes.getType(),
                authProvider.getAuthenticatedUser()));
        return repository.save(tdb);
    }

    public void delete(UUID walletId, Long id) {
        walletService.findWritable(walletId);
        repository.delete(findInWallet(walletId, id));
    }
}
