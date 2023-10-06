package com.esoft.auth.repository.chat;

import com.esoft.auth.entity.chat.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface MessageRepository extends JpaRepository<MessageEntity, Integer>,
        PagingAndSortingRepository<MessageEntity, Integer>,
        JpaSpecificationExecutor<MessageEntity> {


}
