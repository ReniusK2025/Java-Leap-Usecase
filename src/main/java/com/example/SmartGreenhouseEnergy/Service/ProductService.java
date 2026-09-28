package com.example.SmartGreenhouseEnergy.Service;

import com.example.SmartGreenhouseEnergy.Model.Product;
import com.example.SmartGreenhouseEnergy.Repository.ProductRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductService {
    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public Product save(Product value) {
        return repository.save(value);
    }

    public List<Product> findAll() {
        return repository.findAll();
    }

    public Product findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found: " + id));
    }

    public Product update(Long id, Product value) {
        Product product = findById(id);
        product.setName(value.getName());
        product.setType(value.getType());
        product.setUnit(value.getUnit());
        product.setUnitPrice(value.getUnitPrice());
        return repository.save(product);
    }

    public void delete(Long id) {
        repository.delete(findById(id));
    }
}
