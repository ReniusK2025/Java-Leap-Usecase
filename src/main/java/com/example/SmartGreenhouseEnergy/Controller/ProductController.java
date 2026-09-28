package com.example.SmartGreenhouseEnergy.Controller;

import com.example.SmartGreenhouseEnergy.Model.Product;
import com.example.SmartGreenhouseEnergy.Service.ProductService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService service;
    public ProductController(ProductService service) { this.service=service; }
    @PostMapping public Product create(@RequestBody Product x) { return service.save(x); }
    @GetMapping public List<Product> all() { return service.findAll(); }
    @GetMapping("/{id}") public Product one(@PathVariable Long id) { return service.findById(id); }
}
