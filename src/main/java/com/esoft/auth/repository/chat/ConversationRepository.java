package com.esoft.auth.repository.chat;

import com.esoft.auth.entity.chat.ConversationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ConversationRepository extends JpaRepository<ConversationEntity, Integer>,
        PagingAndSortingRepository<ConversationEntity, Integer>,
        JpaSpecificationExecutor<ConversationEntity> {

}
