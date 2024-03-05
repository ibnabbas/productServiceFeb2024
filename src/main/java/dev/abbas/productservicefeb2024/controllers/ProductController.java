package dev.abbas.productservicefeb2024.controllers;

import dev.abbas.productservicefeb2024.dtos.CreateProductRequestDto;
import dev.abbas.productservicefeb2024.exceptions.ProductNotFoundException;
import dev.abbas.productservicefeb2024.models.Product;
import dev.abbas.productservicefeb2024.services.FakeStoreProductService;
import dev.abbas.productservicefeb2024.services.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
public class ProductController {

    //private ProductService ps = new FakeStoreProductService();
    private ProductService productService;
    private RestTemplate restTemplate;

    public ProductController(ProductService productService,
                             RestTemplate restTemplate
    ) {
        this.productService = productService;
        this.restTemplate = restTemplate;
    }

    @PostMapping("/products")
    public Product createProduct(@RequestBody CreateProductRequestDto request) {
        return productService.createProduct(
                request.getTitle(),
                request.getDescription(),
                request.getImage(),
                request.getPrice(),
                request.getCategory()
        );
    }
    @GetMapping("/products/{id}")
    public Product getProductDetails(@PathVariable("id") Long productId) {
        return productService.getSingleProduct(productId);
    }
    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts() throws ProductNotFoundException {

        List<Product> products = productService.getProducts();

//        throw new ProductNotFoundException("Bla bla bla");

        ResponseEntity<List<Product>> response = new ResponseEntity<>(products, HttpStatus.NOT_FOUND);
        return response;
    }

    @PutMapping("/products/{id}")
    public Product updateProduct(@PathVariable("id") Long productId ,
                                 @RequestBody CreateProductRequestDto request){
        return productService.updateProduct(productId,
                request.getTitle(),
                request.getDescription(),
                request.getImage(),
                request.getPrice(),
                request.getCategory()
        );
    }
    @DeleteMapping("/products/{id}")
    public void deleteProduct(@PathVariable("id") int id){

        productService.deleteById(id);
    }

    @GetMapping("/products/categories")
    public List<String> getAllCategories(){
        return  productService.getAllCategories();
    }
}
