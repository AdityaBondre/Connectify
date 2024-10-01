package com.whoknows.Connectify.entity;


import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "Users")
public class User{

    @Id
    private ObjectId id;
    private String name;
    @Indexed(unique = true)
    private String username;
    private String password;
    private String email;
}
