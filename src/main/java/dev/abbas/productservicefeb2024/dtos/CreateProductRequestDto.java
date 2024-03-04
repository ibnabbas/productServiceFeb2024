package dev.abbas.productservicefeb2024.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateProductRequestDto {
    //private Long id;
    private String title;
    private String image;
    private String description;
    private String category;
    private double price;
    //private Long userID;
}
