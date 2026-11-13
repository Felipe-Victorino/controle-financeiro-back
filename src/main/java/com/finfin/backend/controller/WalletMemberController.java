package com.finfin.backend.controller;

import com.finfin.backend.dto.app.walletmember.WalletMemberDTORequest;
import com.finfin.backend.dto.app.walletmember.WalletMemberDTOResponse;

import com.finfin.backend.entity.WalletMember;
import com.finfin.backend.service.WalletMemberService;
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
@RequestMapping("/wallets/{walletId}/members")
public class WalletMemberController {

    @Autowired
    WalletMemberService service;

    @Autowired
    ModelMapper mapper;

    @GetMapping
    public ResponseEntity<List<WalletMemberDTOResponse>> list(@PathVariable UUID walletId) {
        List<WalletMemberDTOResponse> body = service.listMembers(walletId).stream()
                .map(m -> mapper.map(m, WalletMemberDTOResponse.class))
                .toList();
        return ResponseEntity.ok(body);
    }

    @PostMapping
    public ResponseEntity<WalletMemberDTOResponse> add(@PathVariable UUID walletId, @Valid @RequestBody WalletMemberDTORequest request) {
        WalletMember member = service.addMember(walletId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.map(member, WalletMemberDTOResponse.class));
    }

    @PutMapping("/{memberId}")
    public ResponseEntity<WalletMemberDTOResponse> update(@PathVariable UUID walletId, @PathVariable Long memberId, @Valid @RequestBody WalletMemberDTORequest request) {
        WalletMember member = service.updateRole(walletId, memberId, request.getRole());
        return ResponseEntity.ok(mapper.map(member, WalletMemberDTOResponse.class));
    }

    @DeleteMapping("/{memberId}")
    public ResponseEntity<Void> remove(@PathVariable UUID walletId, @PathVariable Long memberId) {

        service.removeMember(walletId, memberId);
        return ResponseEntity.noContent().build();
    }

}
