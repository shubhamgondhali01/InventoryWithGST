package com.example.gstbilling.controller;

import com.example.gstbilling.model.Sale;
import com.example.gstbilling.service.ApiKeyAuthService;
import com.example.gstbilling.service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/sales")
public class SaleController {

    @Autowired
    private SaleService saleService;

    @Autowired
    private ApiKeyAuthService apiKeyAuthService;

    @PostMapping
    public ResponseEntity<String> createSale(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestParam Long productId,
            @RequestParam int quantity,
            @RequestParam String saleDate) {

        // Validate the API key and check if the user has ADMIN role
        ResponseEntity<String> authResponse = apiKeyAuthService.validateApiKey(authorizationHeader);
        if (!authResponse.getStatusCode().is2xxSuccessful() || !authResponse.getBody().equals("ADMIN")) {
            return ResponseEntity.status(authResponse.getStatusCode()).body("Admin Access Only");
        }

        // Create a sale
        String result = saleService.createSale(productId, quantity, saleDate);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/byDate")
    public ResponseEntity<List<Sale>> getSalesByDate(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestParam String saleDate) {

        // Validate the API key and check if the user has ADMIN role
        ResponseEntity<String> authResponse = apiKeyAuthService.validateApiKey(authorizationHeader);
        if (!authResponse.getStatusCode().is2xxSuccessful() || !authResponse.getBody().equals("ADMIN")) {
            return ResponseEntity.status(authResponse.getStatusCode()).body(null);
        }

        // Retrieve sales by date
        List<Sale> sales = saleService.getSalesByDate(saleDate);
        return ResponseEntity.ok(sales);
    }

    @GetMapping("/totalRevenue")
    public ResponseEntity<Double> getTotalRevenue(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestParam String startDate,
            @RequestParam String endDate) {

        // Validate the API key and check if the user has ADMIN role
        ResponseEntity<String> authResponse = apiKeyAuthService.validateApiKey(authorizationHeader);
        if (!authResponse.getStatusCode().is2xxSuccessful() || !authResponse.getBody().equals("ADMIN")) {
            return ResponseEntity.status(authResponse.getStatusCode()).body(null);
        }

        // Calculate total revenue
        double totalRevenue = saleService.getTotalRevenue(startDate, endDate);
        return ResponseEntity.ok(totalRevenue);
    }

    @GetMapping("/calculateBill")
    public ResponseEntity<String> calculateBill(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestParam Map<String, String> params) {

//        // Validate the API key and check if the user has ADMIN role
//        ResponseEntity<String> authResponse = apiKeyAuthService.validateApiKey(authorizationHeader);
//        if (!authResponse.getStatusCode().is2xxSuccessful() || !authResponse.getBody().equals("ADMIN")) {
//            return ResponseEntity.status(authResponse.getStatusCode()).body("Admin Access Only");
//        }

        // Calculate the bill
        Map<Long, Integer> productQuantities = new HashMap<>();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            try {
                Long productId = Long.parseLong(entry.getKey());
                Integer quantity = Integer.parseInt(entry.getValue());
                productQuantities.put(productId, quantity);
            } catch (NumberFormatException e) {
                return ResponseEntity.badRequest().body("Invalid product ID or quantity format.");
            }
        }

        String result = saleService.calculateBill(productQuantities);
        return ResponseEntity.ok(result);
    }
}
