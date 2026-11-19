package com.finfin.backend.service;


import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class TransactionEventService {

    private static final long TIMEOUT_MS = 30 * 60 * 1000L;
    private final Map<UUID, List<SseEmitter>> emitters = new ConcurrentHashMap<>();

    public SseEmitter register(UUID walletId) {
        SseEmitter emitter = new SseEmitter(TIMEOUT_MS);
        emitters.compute(walletId, (k, list) -> {
            if (list == null) list = new CopyOnWriteArrayList<>();
            list.add(emitter);
            return list;
        });

        Runnable cleanup = () -> emitters.computeIfPresent(walletId, (k, list) -> {
            list.remove(emitter);
            return list.isEmpty() ? null : list;
        });
        emitter.onCompletion(cleanup);
        emitter.onTimeout(cleanup);
        emitter.onError(e -> cleanup.run());

        send(emitter, "connected", "ok");
        return emitter;


    }

    public void notifyWallet(UUID walletId, String action) {
        List<SseEmitter> list = emitters.get(walletId);
        if (list == null) return;
        list.removeIf(e -> !send(e, "transaction-changed", action));
    }

    @Scheduled(fixedRate = 25_000)
        // needs @EnableScheduling; keeps proxies from dropping idle streams
    void heartbeat() {
        emitters.values().forEach(list -> list.removeIf(e -> !sendComment(e)));
    }

    private boolean send(SseEmitter emitter, String name, String data) {
        try {
            emitter.send(SseEmitter.event().name(name).data(data));
            return true;
        } catch (IOException | IllegalStateException ex) {
            return false;
        }
    }

    private boolean sendComment(SseEmitter emitter) {
        try {
            emitter.send(SseEmitter.event().comment("ping"));
            return true;
        } catch (IOException | IllegalStateException ex) {
            return false;
        }
    }
}
