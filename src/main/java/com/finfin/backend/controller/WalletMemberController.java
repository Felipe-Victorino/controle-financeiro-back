package com.finfin.backend.controller;

import com.finfin.backend.dto.app.walletmember.WalletMemberDTOResponse;

import com.finfin.backend.security.UserAuthProvider;
import com.finfin.backend.service.WalletMemberService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/wallets/{walletId}/members")
public class WalletMemberController {

    @Autowired
    WalletMemberService service;

    @Autowired
    UserAuthProvider authProvider;

    @Autowired
    ModelMapper mapper;

    @GetMapping
    public ResponseEntity<List<WalletMemberDTOResponse>> list(@PathVariable UUID walletId) {
        List<WalletMemberDTOResponse> body = service.listMembers(walletId).stream()
                .map(m -> mapper.map(m, WalletMemberDTOResponse.class))
                .toList();
        return ResponseEntity.ok(body);
    }

}
