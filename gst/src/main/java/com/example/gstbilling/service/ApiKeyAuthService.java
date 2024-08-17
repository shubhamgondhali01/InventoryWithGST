package com.example.gstbilling.service;

import com.example.gstbilling.model.User;
import com.example.gstbilling.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ApiKeyAuthService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Validates the API key and returns the role or an unauthorized message.
     * @param apiKey the API key to validate
     * @return ResponseEntity containing the user's role or an unauthorized message
     */
    public ResponseEntity<String> validateApiKey(String apiKey) {
        if (apiKey == null || apiKey.isEmpty()) {
            return new ResponseEntity<>("No API key found", HttpStatus.UNAUTHORIZED);
        }

        User user = userRepository.findByApiKey(apiKey);

        if (user == null) {
            return new ResponseEntity<>("Invalid API key", HttpStatus.UNAUTHORIZED);
        }

        String role = user.getRole();
        return new ResponseEntity<>(role, HttpStatus.OK);
    }
}
