package com.esoft.auth.entity.chat;

import com.esoft.auth.entity.user.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "message")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String message;

    private int type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conversion_id")
    private ConversationEntity conversationEntity;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

}
