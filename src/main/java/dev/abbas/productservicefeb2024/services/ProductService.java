package dev.abbas.productservicefeb2024.services;

import dev.abbas.productservicefeb2024.models.Product;

import java.util.List;

public interface ProductService {

    Product getSingleProduct(Long productId);

    List<Product> getProducts();

    Product createProduct(String title, String description,
                          String image, double price, String category);

    void deleteById(int id);

    Product updateProduct(Long productId , String title, String desc,
                          String image, Double price, String category);

    List<String> getAllCategories();
}
