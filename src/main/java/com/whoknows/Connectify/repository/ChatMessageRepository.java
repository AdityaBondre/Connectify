package com.whoknows.Connectify.repository;

import com.whoknows.Connectify.entity.ChatMessage;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface ChatMessageRepository extends MongoRepository<ChatMessage, ObjectId> {

    // Custom query to find chat history between two users
    @Query("{$or: [{'sender': ?0, 'recipient': ?1}, {'sender': ?1, 'recipient': ?0}]}")
    List<ChatMessage> findChatHistory(String user1, String user2);
}

