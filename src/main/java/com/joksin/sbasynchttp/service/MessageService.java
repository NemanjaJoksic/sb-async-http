package com.joksin.sbasynchttp.service;

import com.joksin.sbasynchttp.model.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.*;

@Slf4j
@Service
public class MessageService {

    private static ExecutorService ec = Executors.newSingleThreadExecutor();

    public Callable<Message> getCallableWithMessageObject() {
        return () -> {
            log.info("getCallableWithMessageObject() start");
            Thread.sleep(3000); // wait 3s
            Message message = new Message("UUID: " + UUID.randomUUID());
            log.info("getCallableWithMessageObject() end");
            return message;
        };
    }

    public CompletionStage<Message> getCompletionStageWithMessageObject() {
        return CompletableFuture.supplyAsync(() -> {
            log.info("getCompletionStageWithMessageObject() start");
            try {
                Thread.sleep(3000); // wait 3s
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            Message message = new Message("UUID: " + UUID.randomUUID());
            log.info("getCompletionStageWithMessageObject() end");
            return message;
        }, ec);
    }

}
