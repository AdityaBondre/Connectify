package com.whoknows.Connectify.entity;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Base64;

@Data
@Document(collection = "messages")
public class ChatMessage {

    @Id
    private ObjectId id;
    private String sender;
    private String recipient;
    private String content;
    private LocalDateTime timestamp;

    // Encode content
    public void setContent(String content) {
        this.content = Base64.getEncoder().encodeToString(content.getBytes(StandardCharsets.UTF_8));
    }

    // Decode content
    public String getContent() {
        return new String(Base64.getDecoder().decode(this.content), StandardCharsets.UTF_8);
    }
}
