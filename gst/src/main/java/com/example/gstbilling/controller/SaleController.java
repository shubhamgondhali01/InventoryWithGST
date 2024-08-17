package com.example.gstbilling.controller;

import com.example.gstbilling.model.Sale;
import com.example.gstbilling.service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import java.util.Map;
import java.util.HashMap;


@RestController
@RequestMapping("/sales")
public class SaleController {

    @Autowired
    private SaleService saleService;

    @PostMapping
    public String createSale(@RequestParam Long productId,
                             @RequestParam int quantity,
                             @RequestParam String saleDate) {
        return saleService.createSale(productId, quantity, saleDate);
    }

    @GetMapping("/byDate")
    public List<Sale> getSalesByDate(@RequestParam String saleDate) {
        return saleService.getSalesByDate(saleDate);
    }

    @GetMapping("/totalRevenue")
    public double getTotalRevenue(@RequestParam String startDate,
                                  @RequestParam String endDate) {
        return saleService.getTotalRevenue(startDate, endDate);
    }
    
    
    @GetMapping("/calculateBill")
    public String calculateBill(@RequestParam Map<String, String> params) {
        Map<Long, Integer> productQuantities = new HashMap<>();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            try {
                Long productId = Long.parseLong(entry.getKey());
                Integer quantity = Integer.parseInt(entry.getValue());
                productQuantities.put(productId, quantity);
            } catch (NumberFormatException e) {
                return "Invalid product ID or quantity format.";
            }
        }
        return saleService.calculateBill(productQuantities);
    }
    
}
