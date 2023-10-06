package com.esoft.auth.model.chat;

import com.esoft.auth.entity.chat.MessageEntity;
import lombok.Data;

@Data
public class MessageDTO {

    private int id;

    private String message;

    private int type;

    public MessageDTO(MessageEntity messageEntity) {
        this.id = messageEntity.getId();
        this.message = messageEntity.getMessage();
        this.type = messageEntity.getType();
    }
}
