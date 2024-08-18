package com.example.gstbilling.controller;

import com.example.gstbilling.model.Category;
import com.example.gstbilling.service.ApiKeyAuthService;
import com.example.gstbilling.service.CategoryService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ApiKeyAuthService apiKeyAuthService;

    private static final String ADMIN_ROLE = "ADMIN";

    @PostMapping
    public ResponseEntity<String> createCategory(
            @RequestHeader(value = "Authorization", required = false) String authorizationHeader,
            @RequestParam String name,
            @RequestParam Double gstRate) {

        if (authorizationHeader == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authorization header missing");
        }

        ResponseEntity<String> authResponse = apiKeyAuthService.validateApiKey(authorizationHeader);
        if (!authResponse.getStatusCode().is2xxSuccessful() || !ADMIN_ROLE.equals(authResponse.getBody())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Admin Access Only");
        }

        Category category = categoryService.createCategory(name, gstRate);
        return ResponseEntity.ok("Category created successfully");
    }

    @GetMapping("/{name}")
    public ResponseEntity<Category> getCategory(
            @RequestHeader(value = "Authorization", required = false) String authorizationHeader,
            @PathVariable String name) {

        if (authorizationHeader == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        ResponseEntity<String> authResponse = apiKeyAuthService.validateApiKey(authorizationHeader);
        if (!authResponse.getStatusCode().is2xxSuccessful()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }

        Category category = categoryService.getCategoryByName(name);
        return ResponseEntity.ok(category);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(
            @RequestHeader(value = "Authorization", required = false) String authorizationHeader,
            @PathVariable Long id) {

        if (authorizationHeader == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authorization header missing");
        }

        ResponseEntity<String> authResponse = apiKeyAuthService.validateApiKey(authorizationHeader);
        if (!authResponse.getStatusCode().is2xxSuccessful() || !ADMIN_ROLE.equals(authResponse.getBody())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Admin Access Only");
        }

        categoryService.deleteCategory(id);
        return ResponseEntity.ok("Category deleted successfully");
    }
    
 // New Method to Get All Categories
    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories(
            @RequestHeader(value = "Authorization", required = false) String authorizationHeader) {

        if (authorizationHeader == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        ResponseEntity<String> authResponse = apiKeyAuthService.validateApiKey(authorizationHeader);
        if (!authResponse.getStatusCode().is2xxSuccessful()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }

        List<Category> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }
}




//package com.example.gstbilling.controller;
//
//import com.example.gstbilling.model.Category;
//import com.example.gstbilling.service.CategoryService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/categories")
//public class CategoryController {
//
//    @Autowired
//    private CategoryService categoryService;
//
//    @PostMapping
//    public Category createCategory(@RequestParam String name, @RequestParam Double gstRate) {
//        return categoryService.createCategory(name, gstRate);
//    }
//
//    @GetMapping("/{name}")
//    public Category getCategory(@PathVariable String name) {
//        return categoryService.getCategoryByName(name);
//    }
//
//    @DeleteMapping("/{id}")
//    public void deleteCategory(@PathVariable Long id) {
//        categoryService.deleteCategory(id);
//    }
//}
