package com.whoknows.Connectify.controller;

import com.whoknows.Connectify.entity.User;
import com.whoknows.Connectify.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Signup form page
    @GetMapping("/signup")
    public ModelAndView showCreateForm() {
        User user = new User();
        ModelAndView mv = new ModelAndView("signup");
        mv.addObject("userDetails", user);
        return mv;
    }

    // Handle user creation and password encoding
    @PostMapping("/create")
    public ModelAndView createUser(@ModelAttribute("userDetails") User user) {
        User newuser = new User();
        newuser.setUsername(user.getUsername());
        newuser.setPassword(passwordEncoder.encode(user.getPassword()));  // Encode the password
        newuser.setEmail(user.getEmail());
        newuser.setName(user.getName());

        // Save the correctly encoded user object
        userService.saveUser(newuser);

        return new ModelAndView("login");
    }

    // Login page
    @GetMapping("/login")
    public ModelAndView showLoginForm() {
        ModelAndView mv = new ModelAndView("login");
        return mv;
    }

    @PostMapping("/login")
    public ModelAndView loginUser(@ModelAttribute("userDetails") User user) {
        User dbUser = userService.findByUsername(user.getUsername());

        // Check if user exists and password matches
        if (dbUser != null && passwordEncoder.matches(user.getPassword(), dbUser.getPassword())) {
            return new ModelAndView("redirect:/index");  // Redirect to index page on successful login
        } else {
            ModelAndView mv = new ModelAndView("login");
            mv.addObject("error", "Invalid username or password");
            return mv;  // Return login page with error message
        }
    }

    // Show all users on the index page
    @GetMapping("/index")
    public ModelAndView viewIndex(@AuthenticationPrincipal UserDetails currentUser) {
        List<User> users = userService.getAll(); // Fetch all users
        // Remove the current user from the list
        users.removeIf(user -> user.getUsername().equals(currentUser.getUsername()));


        ModelAndView mv = new ModelAndView("index");
        mv.addObject("users", users);  // Pass users to the view
        return mv;
    }
    // Friend chat based on selected user
    @GetMapping("/friend-talk/{username}")
    public ModelAndView getFriendTalk(@AuthenticationPrincipal User currentUser, @PathVariable String username) {
        ModelAndView mv = new ModelAndView("friend-chat");
        List<User> users = userService.getAll(); // Fetch all users
        // Remove the current user from the list
        users.removeIf(user -> user.getUsername().equals(currentUser.getUsername()));
        mv.addObject("users", users);
        mv.addObject("currentUser", currentUser.getUsername());
        mv.addObject("friend", username);  // The selected friend for chat
        return mv;
    }


}