package com.finfin.backend.service;

import com.finfin.backend.entity.Transaction;
import com.finfin.backend.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService{

    @Autowired
    TransactionRepository repository;

    public Transaction findById(Long id) {
        return repository.findById(id).orElseThrow(()->new RuntimeException("{transaction.notfound}"));
    }


    public Transaction insert(Transaction t) {
        return repository.save(t);
    }


    public void delete(Long id) {
        repository.delete(findById(id));
    }

    public Transaction update(Transaction t) {
        Transaction tdb = findById(t.getId());

        tdb.setWallet(t.getWallet());
        tdb.setType(t.getType());
        tdb.setCategory(t.getCategory());
        tdb.setDateTransaction(t.getDateTransaction());
        tdb.setValue(t.getValue());
        tdb.setDescription(tdb.getDescription());
        tdb.setCreatedBy(tdb.getCreatedBy());

        return repository.save(tdb);
    }

    public Transaction updateWallet(Transaction t){
        Transaction tdb = findById(t.getId());
        tdb.setWallet(t.getWallet());
        return repository.save(tdb);
    }

    public Transaction updateType(Transaction t){
        Transaction tdb = findById(t.getId());
        tdb.setType(t.getType());
        return repository.save(tdb);
    }

    public Transaction updateCategory(Transaction t){
        Transaction tdb = findById(t.getId());
        tdb.setCategory(t.getCategory());
        return repository.save(tdb);
    }

    public Transaction updateDateTransaction(Transaction t){
        Transaction tdb = findById(t.getId());
        tdb.setDateTransaction(t.getDateTransaction());
        return repository.save(tdb);
    }

    public Transaction updateValue(Transaction t){
        Transaction tdb = findById(t.getId());
        tdb.setValue(t.getValue());
        return repository.save(tdb);
    }

    public Transaction updateDescription(Transaction t){
        Transaction tdb = findById(t.getId());
        tdb.setDescription(tdb.getDescription());
        return repository.save(tdb);
    }

    public Transaction updateCreatedBy(Transaction t){
        Transaction tdb = findById(t.getId());
        tdb.setCreatedBy(tdb.getCreatedBy());
        return repository.save(tdb);
    }


    public List<Transaction> listAll() {
        return repository.findAll();
    }
}
