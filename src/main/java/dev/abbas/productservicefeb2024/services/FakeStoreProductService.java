package dev.abbas.productservicefeb2024.services;

import dev.abbas.productservicefeb2024.dtos.FakeStoreProductDto;
import dev.abbas.productservicefeb2024.models.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class FakeStoreProductService implements ProductService{

    private RestTemplate restTemplate;

    public FakeStoreProductService(RestTemplate restTemplate) {

        this.restTemplate = restTemplate;
    }

    @Override
    public Product getSingleProduct(Long productId) {
        FakeStoreProductDto fakeStoreProduct = restTemplate.getForObject(
                "https://fakestoreapi.com/products/" + productId,
                FakeStoreProductDto.class);
        return fakeStoreProduct.toProduct();
    }

    @Override
    public List<Product> getProducts() {
        FakeStoreProductDto[] fakeStoreProducts =
                restTemplate.getForObject(
                        "https://fakestoreapi.com/products",
                        FakeStoreProductDto[].class
                );

        List<Product> products = new ArrayList<>();
        for (FakeStoreProductDto fakeStoreProduct: fakeStoreProducts) {
            products.add(fakeStoreProduct.toProduct());
        }
        return products;
    }

    public Product createProduct (String title, String description,
                                  String image, double price, String category ) {

        FakeStoreProductDto fakeStoreProductDto = new FakeStoreProductDto();

        fakeStoreProductDto.setTitle(title);
        fakeStoreProductDto.setDescription(description);
        fakeStoreProductDto.setImage(image);
        fakeStoreProductDto.setPrice(price);
        fakeStoreProductDto.setCategory(category);

        FakeStoreProductDto response = restTemplate.postForObject("https://fakestoreapi.com/products",
                fakeStoreProductDto,
                FakeStoreProductDto.class);
        return response.toProduct();
    }

    @Override
    public void deleteById(int id) {
        restTemplate.delete("https://fakestoreapi.com/products/" + id);
    }

    @Override
    public Product updateProduct(Long productId, String title, String desc,
                                 String image, Double price, String category) {
        FakeStoreProductDto fakeStoreProductDto = new FakeStoreProductDto();
        fakeStoreProductDto.setId(productId);

        if (title != null) { fakeStoreProductDto.setTitle(title); }
        if (desc != null) { fakeStoreProductDto.setDescription(desc); }
        if (image != null) { fakeStoreProductDto.setImage(image); }
        if (price != null) { fakeStoreProductDto.setPrice(price); }
        if (category != null) { fakeStoreProductDto.setCategory(category); }

        restTemplate.put("https://fakestoreapi.com/products/" + productId, fakeStoreProductDto);
        return getSingleProduct(productId);
    }

    @Override
    public List<String> getAllCategories() {

        ResponseEntity<String[]> responseEntity = restTemplate.getForEntity(
                "https://fakestoreapi.com/products/categories",
                String[].class
        );

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            List<String> categoryList = Arrays.stream(responseEntity.getBody()).toList();

            return categoryList;
        } else {
            // Handle the case where the request was not good
            return Collections.emptyList();
        }
    }

}
