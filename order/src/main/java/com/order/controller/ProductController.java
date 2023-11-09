package com.order.controller;

import com.order.model.Product;
import com.order.repository.ProductRepository;
import jdk.net.SocketFlow;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@RequestMapping("/product")
public class ProductController {
    private final ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    @GetMapping("view/all")
    public Iterable<Product> getAllProduct(){
        return productRepository.findAll();
    }
    @GetMapping("view/{id}")
    public Optional<Product> getProduct(@PathVariable String id) {
        return productRepository.findById(Integer.valueOf(id));
    }
    @PostMapping("add")
    public Product addProduct(@RequestBody Product newProduct){
        Product product = new Product();
        product.setName(newProduct.getName());
        product.setDescription(newProduct.getDescription());
        product.setQuantity(newProduct.getQuantity());
        product.setPrice(newProduct.getPrice());
        productRepository.save(product);
        return product;
    }

    @RequestMapping (value = "update/{id}",method = RequestMethod.PUT)
    public ResponseEntity<Object> updateProduct(@PathVariable("id") String id,@RequestBody Product newProduct) {
        Optional<Product> productOptional = getProduct(id);
        if (productOptional.isPresent()) {
            Product product = productOptional.get();

            // Update the product properties with the new values

            product.setName(newProduct.getName());
            product.setDescription(newProduct.getDescription());
            product.setPrice(newProduct.getPrice());
            product.setQuantity(newProduct.getQuantity());
            // Save the updated product

            // Your code for saving the product goes here
            productRepository.save(product);
            return new ResponseEntity<>(product, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
        }
    }

}
