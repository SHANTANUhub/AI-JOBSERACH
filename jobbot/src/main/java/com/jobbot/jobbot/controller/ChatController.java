package com.jobbot.jobbot.controller;

import com.jobbot.jobbot.model.Message;
import com.jobbot.jobbot.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/chat")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @PostMapping
    public Message chat(@RequestBody Message userMessage) {
        return chatService.getBotReply(userMessage);
    }
}
