package com.finfin.backend.service;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

@Service
public class GeminiService {

    @Autowired
    private ChatModel chatModel;
    String testPrompt = "Tell me about green";

    @Async
    public CompletableFuture<String> makeNewTestPrompt() {
        ChatResponse response = chatModel.call(
                new Prompt(testPrompt)
        );
        Generation result = response.getResult();
        if (result == null) {
            throw new RuntimeException("");
        }
        return CompletableFuture.completedFuture(result.getOutput().getText());
    }
}