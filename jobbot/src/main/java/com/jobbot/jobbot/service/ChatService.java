package com.jobbot.jobbot.service;

import com.jobbot.jobbot.model.Message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatService {
     @Autowired
private JobSearchService jobSearchService;


public Message getBotReply(Message userMessage) {
    String query = userMessage.getText().toLowerCase();
    String reply = jobSearchService.searchJobs(query);
    return new Message("bot", reply);
}

 

}
