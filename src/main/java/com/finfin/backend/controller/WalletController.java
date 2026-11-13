package com.finfin.backend.controller;

import com.finfin.backend.dto.app.wallet.WalletDTORequest;
import com.finfin.backend.dto.app.wallet.WalletDTOResponse;

import com.finfin.backend.entity.Wallet;
import com.finfin.backend.service.WalletService;

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
@RequestMapping("/wallets")
public class WalletController implements CrudController<WalletDTORequest, UUID, WalletDTOResponse> {

    @Autowired
    WalletService service;

    @Autowired
    ModelMapper mapper;

    @Override
    @PostMapping
    public ResponseEntity<WalletDTOResponse> create(@RequestBody WalletDTORequest request) {

        Wallet wallet = service.insert(mapper.map(request, Wallet.class));
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.map(wallet, WalletDTOResponse.class));
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<WalletDTOResponse> get(@PathVariable UUID id) {
        Wallet wallet = service.findAccessible(id);
        WalletDTOResponse response = mapper.map(wallet, WalletDTOResponse.class);
        return ResponseEntity.ok(response);
    }


    @GetMapping
    public ResponseEntity<List<WalletDTOResponse>> getAll() {
        List<Wallet> wallets = service.listAll();

        List<WalletDTOResponse> responseList = wallets.stream()
                .map(wallet -> mapper.map(wallet, WalletDTOResponse.class))
                .toList();

        if (responseList.isEmpty()) {
            return ResponseEntity.ok(List.of());
        }

        return ResponseEntity.ok(responseList);
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<WalletDTOResponse> update(@PathVariable UUID id, @RequestBody WalletDTORequest request) {
        Wallet wallet = mapper.map(request, Wallet.class);
        wallet.setId(id); // the id comes from the URL, never from the body
        Wallet updated = service.update(wallet);
        return ResponseEntity.ok(mapper.map(updated, WalletDTOResponse.class));
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
