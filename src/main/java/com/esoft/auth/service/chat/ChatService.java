package com.esoft.auth.service.chat;

import com.esoft.auth.repository.chat.ConversationRepository;
import com.esoft.auth.repository.chat.MessageRepository;
import com.esoft.auth.service.PrimaryBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatService extends PrimaryBaseService {

    @Autowired
    ConversationRepository conversationRepository;

    @Autowired
    MessageRepository messageRepository;

    public boolean createMessageByUserName() {

        return true;
    }

}
