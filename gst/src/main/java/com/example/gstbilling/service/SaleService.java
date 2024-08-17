//package com.example.gstbilling.service;
//
//import com.example.gstbilling.model.Product;
//import com.example.gstbilling.model.Sale;
//import com.example.gstbilling.repository.ProductRepository;
//import com.example.gstbilling.repository.SaleRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDate;
//import java.time.format.DateTimeFormatter;
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class SaleService {
//
//    @Autowired
//    private SaleRepository saleRepository;
//
//    @Autowired
//    private ProductRepository productRepository;
//
//    public String createSale(Long productId, int quantity, String saleDate) {
//        Optional<Product> productOpt = productRepository.findById(productId);
//        if (productOpt.isEmpty()) {
//            return "Product not found";
//        }
//
//        Product product = productOpt.get();
//        double totalPrice = product.getPrice() * quantity;
//
//        Sale sale = new Sale();
//        sale.setProduct(product);
//        sale.setQuantity(quantity);
//        sale.setTotalPrice(totalPrice);
//        sale.setSaleDate(LocalDate.parse(saleDate, DateTimeFormatter.ISO_LOCAL_DATE));
//
//        saleRepository.save(sale);
//        return "Sale created successfully";
//    }
//
//    public List<Sale> getSalesByDate(String saleDate) {
//        LocalDate date = LocalDate.parse(saleDate, DateTimeFormatter.ISO_LOCAL_DATE);
//        List<Sale> sales = saleRepository.findBySaleDate(date);
//        
//        // Calculate total price including GST for each sale
//        for (Sale sale : sales) {
//            double gstRate = productRepository.findById(sale.getProduct().getId())
//                                              .map(product -> product.getCategory().getGstRate())
//                                              .orElse(0.0);
//            double taxAmount = (sale.getTotalPrice() * gstRate) / 100;
//            sale.setTotalPrice(sale.getTotalPrice() + taxAmount);
//        }
//
//        return sales;
//    }
//
//    public double getTotalRevenue(String startDate, String endDate) {
//        LocalDate start = LocalDate.parse(startDate, DateTimeFormatter.ISO_LOCAL_DATE);
//        LocalDate end = LocalDate.parse(endDate, DateTimeFormatter.ISO_LOCAL_DATE);
//
//        // Fetch sales within the date range
//        List<Sale> sales = saleRepository.findBySaleDateBetween(start, end);
//
//        // Calculate total revenue including tax
//        return sales.stream()
//                    .mapToDouble(sale -> {
//                        double price = sale.getTotalPrice();
//                        double gstRate = productRepository.findById(sale.getProduct().getId())
//                                                          .map(product -> product.getCategory().getGstRate())
//                                                          .orElse(0.0);
//                        double taxAmount = (price * gstRate) / 100;
//                        return price + taxAmount;
//                    })
//                    .sum();
//    }
//    
//
//}

package com.example.gstbilling.service;

import com.example.gstbilling.model.Product;
import com.example.gstbilling.model.Sale;
import com.example.gstbilling.repository.ProductRepository;
import com.example.gstbilling.repository.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class SaleService {

    @Autowired
    private SaleRepository saleRepository;

    @Autowired
    private ProductRepository productRepository;

    // Existing methods

    public String createSale(Long productId, int quantity, String saleDate) {
        Optional<Product> productOpt = productRepository.findById(productId);
        if (productOpt.isEmpty()) {
            return "Product not found";
        }

        Product product = productOpt.get();
        double totalPrice = product.getPrice() * quantity;

        Sale sale = new Sale();
        sale.setProduct(product);
        sale.setQuantity(quantity);
        sale.setTotalPrice(totalPrice);
        sale.setSaleDate(LocalDate.parse(saleDate, DateTimeFormatter.ISO_LOCAL_DATE));

        saleRepository.save(sale);
        return "Sale created successfully";
    }

    public List<Sale> getSalesByDate(String saleDate) {
        LocalDate date = LocalDate.parse(saleDate, DateTimeFormatter.ISO_LOCAL_DATE);
        List<Sale> sales = saleRepository.findBySaleDate(date);
        
        // Calculate total price including GST for each sale
        for (Sale sale : sales) {
            double gstRate = productRepository.findById(sale.getProduct().getId())
                                              .map(product -> product.getCategory().getGstRate())
                                              .orElse(0.0);
            double taxAmount = (sale.getTotalPrice() * gstRate) / 100;
            sale.setTotalPrice(sale.getTotalPrice() + taxAmount);
        }

        return sales;
    }

    public double getTotalRevenue(String startDate, String endDate) {
        LocalDate start = LocalDate.parse(startDate, DateTimeFormatter.ISO_LOCAL_DATE);
        LocalDate end = LocalDate.parse(endDate, DateTimeFormatter.ISO_LOCAL_DATE);

        // Fetch sales within the date range
        List<Sale> sales = saleRepository.findBySaleDateBetween(start, end);

        // Calculate total revenue including tax
        return sales.stream()
                    .mapToDouble(sale -> {
                        double price = sale.getTotalPrice();
                        double gstRate = productRepository.findById(sale.getProduct().getId())
                                                          .map(product -> product.getCategory().getGstRate())
                                                          .orElse(0.0);
                        double taxAmount = (price * gstRate) / 100;
                        return price + taxAmount;
                    })
                    .sum();
    }

    public String calculateBill(Map<Long, Integer> productQuantities) {
        double totalBill = 0.0;

        LocalDate today = LocalDate.now(); // Assuming sales are for today

        for (Map.Entry<Long, Integer> entry : productQuantities.entrySet()) {
            Long productId = entry.getKey();
            Integer quantity = entry.getValue();

            Optional<Product> productOpt = productRepository.findById(productId);
            if (productOpt.isEmpty()) {
                return "Product not found for ID: " + productId;
            }

            Product product = productOpt.get();
            double gstRate = product.getCategory().getGstRate();
            double price = product.getPrice();
            double totalPrice = price * quantity;
            double taxAmount = (totalPrice * gstRate) / 100;
            double finalPrice = totalPrice + taxAmount;

            // Create sale record
            Sale sale = new Sale();
            sale.setProduct(product);
            sale.setQuantity(quantity);
            sale.setTotalPrice(finalPrice);
            sale.setSaleDate(today);

            saleRepository.save(sale);

            totalBill += finalPrice;
        }

        return "Total Bill: " + totalBill;
    }
}
