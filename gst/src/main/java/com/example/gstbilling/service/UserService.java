//package com.example.gstbilling.service;
//
//import com.example.gstbilling.model.User;
//import com.example.gstbilling.repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.security.SecureRandom;
//import java.util.Base64;
//
//@Service
//public class UserService {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    private static final SecureRandom random = new SecureRandom();
//
//    // Method to generate a random API key
//    private String generateApiKey() {
//        byte[] apiKeyBytes = new byte[24];
//        random.nextBytes(apiKeyBytes);
//        return Base64.getUrlEncoder().withoutPadding().encodeToString(apiKeyBytes);
//    }
//
//    public User signUp(String username, String password, String role) {
//        User user = new User();
//        user.setUsername(username);
//        user.setPassword(password); // You should hash this in a real application
//        user.setRole(role);
//        user.setApiKey(generateApiKey());  // Generate and set API key
//        return userRepository.save(user);
//    }
//
//    public User login(String username, String password) {
//        User user = userRepository.findByUsername(username);
//        if (user != null && user.getPassword().equals(password)) {  // Validate password (hash in real app)
//            return user;
//        }
//        return null;
//    }
//}
package com.example.gstbilling.service;

import com.example.gstbilling.model.User;
import com.example.gstbilling.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private static final SecureRandom random = new SecureRandom();

    // Method to generate a random API key
    private String generateApiKey() {
        byte[] apiKeyBytes = new byte[24];
        random.nextBytes(apiKeyBytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(apiKeyBytes);
    }

    public User signUp(String username, String password, String role) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password); // You should hash this in a real application
        user.setRole(role);
        user.setApiKey(generateApiKey());  // Generate and set API key
        return userRepository.save(user);
    }

    public String login(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {  // Validate password (hash in real app)
            return user.getApiKey();  // Return API key as token
        }
        return "No User found";  // Return a message if user not found
    }
}
