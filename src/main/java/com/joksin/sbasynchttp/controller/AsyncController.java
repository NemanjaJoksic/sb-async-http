package com.joksin.sbasynchttp.controller;

import com.joksin.sbasynchttp.model.Message;
import com.joksin.sbasynchttp.service.MessageService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.concurrent.*;

@Slf4j
@RestController
@AllArgsConstructor
public class AsyncController {

    private final MessageService messageService;

    @GetMapping("/api/messages/sync")
    public ResponseEntity<Message> getMessageSync() {
        log.info("AsyncController.getMessageSync() start");
        Message message = new Message("UUID: " + UUID.randomUUID());
        log.info("AsyncController.getMessageSync() end");
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @GetMapping("/api/messages/callable")
    public Callable<Message> getMessageCallable() {
        log.info("AsyncController.getMessageCallable() start");
        Callable<Message> callableWithMessage = messageService.getCallableWithMessageObject();
        log.info("AsyncController.getMessageCallable() end");
        return callableWithMessage;
    }

    @GetMapping("/api/messages/future")
    public CompletionStage<ResponseEntity<Message>> getMessageCompletionStage() {
        log.info("AsyncController.getMessageCompletionStage() start");
        CompletionStage<Message> completionStageWithMessage = messageService.getCompletionStageWithMessageObject();
        log.info("AsyncController.getMessageCompletionStage() end");
        return completionStageWithMessage.thenApply(message -> new ResponseEntity<>(message, HttpStatus.OK));
    }

}
