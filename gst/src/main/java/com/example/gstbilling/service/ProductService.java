package com.example.gstbilling.service;

import com.example.gstbilling.model.Category;
import com.example.gstbilling.model.Product;
import com.example.gstbilling.repository.CategoryRepository;
import com.example.gstbilling.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public Product createProduct(String name, Double price, String categoryName) {
        Category category = categoryRepository.findByName(categoryName);
        if (category == null) {
            throw new RuntimeException("Category not found");
        }
        Product product = new Product();
        product.setName(name);
        product.setPrice(price);
        product.setCategory(category);
        return productRepository.save(product);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
