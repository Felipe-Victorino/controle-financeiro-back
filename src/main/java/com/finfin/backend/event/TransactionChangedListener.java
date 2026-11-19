package com.finfin.backend.event;

import com.finfin.backend.service.TransactionEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
class TransactionChangedListener {
    @Autowired
    TransactionEventService events;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    void on(TransactionChangedEvent e) {
        events.notifyWallet(e.walletId(), e.action());
    }
}