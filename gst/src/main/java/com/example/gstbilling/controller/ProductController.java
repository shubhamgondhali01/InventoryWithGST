package com.example.gstbilling.controller;

import com.example.gstbilling.model.Product;
import com.example.gstbilling.service.ApiKeyAuthService;
import com.example.gstbilling.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ApiKeyAuthService apiKeyAuthService;

    private static final String ADMIN_ROLE = "ADMIN";

    @PostMapping
    public ResponseEntity<String> createProduct(
            @RequestHeader(value = "Authorization", required = false) String authorizationHeader,
            @RequestParam String name,
            @RequestParam Double price,
            @RequestParam String categoryName) {

        if (authorizationHeader == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authorization header missing");
        }

        ResponseEntity<String> authResponse = apiKeyAuthService.validateApiKey(authorizationHeader);
        if (!authResponse.getStatusCode().is2xxSuccessful() || !ADMIN_ROLE.equals(authResponse.getBody())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Admin Access Only");
        }

        Product product = productService.createProduct(name, price, categoryName);
        return ResponseEntity.ok("Product created successfully");
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts(
            @RequestHeader(value = "Authorization", required = false) String authorizationHeader) {

//        if (authorizationHeader == null) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
//        }
//
//        ResponseEntity<String> authResponse = apiKeyAuthService.validateApiKey(authorizationHeader);
//        if (!authResponse.getStatusCode().is2xxSuccessful()) {
//            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
//        }

        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(
            @RequestHeader(value = "Authorization", required = false) String authorizationHeader,
            @PathVariable Long id) {

        if (authorizationHeader == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authorization header missing");
        }

        ResponseEntity<String> authResponse = apiKeyAuthService.validateApiKey(authorizationHeader);
        if (!authResponse.getStatusCode().is2xxSuccessful() || !ADMIN_ROLE.equals(authResponse.getBody())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Admin Access Only");
        }

        productService.deleteProduct(id);
        return ResponseEntity.ok("Product deleted successfully");
    }
}
//package com.example.gstbilling.controller;


//
//import com.example.gstbilling.model.Product;
//import com.example.gstbilling.service.ProductService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/products")
//public class ProductController {
//
//    @Autowired
//    private ProductService productService;
//
//    @PostMapping
//    public Product createProduct(@RequestParam String name, @RequestParam Double price, @RequestParam String categoryName) {
//        return productService.createProduct(name, price, categoryName);
//    }
//
//    @GetMapping
//    public List<Product> getAllProducts() {
//        return productService.getAllProducts();
//    }
//
//    @DeleteMapping("/{id}")
//    public void deleteProduct(@PathVariable Long id) {
//        productService.deleteProduct(id);
//    }
//}
