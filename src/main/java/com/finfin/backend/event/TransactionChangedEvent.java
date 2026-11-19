package com.finfin.backend.event;

import com.finfin.backend.service.TransactionEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.UUID;


public record TransactionChangedEvent(UUID walletId, String action) {
}