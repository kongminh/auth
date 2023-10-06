package com.esoft.auth.entity.chat;

import com.esoft.auth.entity.user.UserEntity;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "conversation")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConversationEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;

    private String description;

    @ManyToMany
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JoinTable(
            name = "conversation_user",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "conversation_id")
    )
    private List<UserEntity> userEntity;

    @OneToMany(
            mappedBy = "conversationEntity",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<MessageEntity> messageEntities;
}
