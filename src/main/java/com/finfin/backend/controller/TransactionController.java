package com.finfin.backend.controller;

import com.finfin.backend.dto.app.transaction.TransactionDTORequest;
import com.finfin.backend.dto.app.transaction.TransactionDTOResponse;
import com.finfin.backend.entity.Transaction;
import com.finfin.backend.service.TransactionService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/wallets/{walletId}/transactions")
public class TransactionController {


    @Autowired
    TransactionService service;
    @Autowired
    ModelMapper mapper;


    @PostMapping
    public ResponseEntity<TransactionDTOResponse> create(
            @PathVariable UUID walletId, @Valid @RequestBody TransactionDTORequest request) {
        Transaction saved = service.insert(walletId, request.getCategoryId(),
                mapper.map(request, Transaction.class));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(mapper.map(saved, TransactionDTOResponse.class));
    }


    @GetMapping
    public ResponseEntity<List<TransactionDTOResponse>> list(@PathVariable UUID walletId) {
        return ResponseEntity.ok(service.list(walletId).stream()
                .map(t -> mapper.map(t, TransactionDTOResponse.class)).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionDTOResponse> get(@PathVariable UUID walletId, @PathVariable Long id) {
        return ResponseEntity.ok(mapper.map(service.get(walletId, id), TransactionDTOResponse.class));
    }


    @PutMapping("/{id}")
    public ResponseEntity<TransactionDTOResponse> update(
            @PathVariable UUID walletId, @PathVariable Long id,
            @Valid @RequestBody TransactionDTORequest request) {
        Transaction updated = service.update(walletId, id, request.getCategoryId(),
                mapper.map(request, Transaction.class));
        return ResponseEntity.ok(mapper.map(updated, TransactionDTOResponse.class));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID walletId, @PathVariable Long id) {
        service.delete(walletId, id);
        return ResponseEntity.noContent().build();
    }
}
