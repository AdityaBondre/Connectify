package com.whoknows.Connectify.controller;

import com.whoknows.Connectify.entity.ChatMessage;
import com.whoknows.Connectify.entity.User;
import com.whoknows.Connectify.repository.ChatMessageRepository;
import com.whoknows.Connectify.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;

@RestController
public class ChatController {

    @Autowired
    private ChatMessageRepository chatMessageRepository;
    @Autowired
    private UserService userService;

    @MessageMapping("/sendMessage")
    @SendTo("/topic/messages")
    public ChatMessage sendMessage(ChatMessage message) {
        message.setTimestamp(LocalDateTime.now());
        chatMessageRepository.save(message);  // Save message to MongoDB
        return message;
    }

    @MessageMapping("/sendFriendMessage")
    @SendTo("/topic/friend-messages")
    public ChatMessage sendFriendMessage(ChatMessage message) {
        message.setTimestamp(LocalDateTime.now());
        chatMessageRepository.save(message);  // Save message to MongoDB
        return message;
    }

    @GetMapping("/talk")
    public ModelAndView getChat(@AuthenticationPrincipal UserDetails currentUser) {
        ModelAndView mv = new ModelAndView("chat");
        List<User> users = userService.getAll(); // Fetch all users
        // Remove the current user from the list
        users.removeIf(user -> user.getUsername().equals(currentUser.getUsername()));
        mv.addObject("users", users);
        mv.addObject("username", currentUser.getUsername());  // Add the username to the model
        return mv;
    }

    @GetMapping("/friend-chat/{username}")
    public ModelAndView getFriendChat(@AuthenticationPrincipal UserDetails currentUser, @PathVariable String username) {
        ModelAndView mv = new ModelAndView("friend-chat");
        mv.addObject("currentUser", currentUser.getUsername());
        mv.addObject("friend", username);
        List<User> users = userService.getAll(); // Fetch all users
        // Remove the current user from the list
        users.removeIf(user -> user.getUsername().equals(currentUser.getUsername()));


        // Fetch chat history between the two users
        List<ChatMessage> chatHistory = chatMessageRepository.findChatHistory(currentUser.getUsername(), username);

        // Decode the content of each message
        for (ChatMessage message : chatHistory) {
            String decodedContent = new String(Base64.getDecoder().decode(message.getContent()));
            message.setContent(decodedContent);  // Update message content with decoded value
        }
        mv.addObject("users", users);
        mv.addObject("chatHistory", chatHistory);
        return mv;
    }

}