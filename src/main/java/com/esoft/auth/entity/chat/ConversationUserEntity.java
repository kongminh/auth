package com.esoft.auth.entity.chat;

import com.esoft.auth.entity.user.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "conversation_user")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConversationUserEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    @ManyToOne
    @JoinColumn(name = "conversation_id")
    private ConversationEntity conversationEntity;

}
