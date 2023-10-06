package com.esoft.auth.entity.user;

import com.esoft.auth.entity.chat.ConversationEntity;
import com.esoft.auth.entity.chat.MessageEntity;
import com.esoft.auth.entity.report.ReportEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users")
@Data
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    private String role;

    private String permissions;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private Date createdAt;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    private Date updatedAt;

    @OneToMany(mappedBy = "userEntity",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<ReportEntity> reports;

    @OneToMany(mappedBy = "userEntity")
    private List<MessageEntity> messages;

    @ManyToMany(mappedBy = "userEntity")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<ConversationEntity> conversationEntities;
}
