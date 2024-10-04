package com.example.apachecamel.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Data
public class ItemDTO {
    private Long id;
    @NotNull(message = "Name cannot be null")
    @Size(min = 1, max = 255, message = "Name must be between 1 and 255 characters")
    private String name;

    @Size(max = 255, message = "Description must be less than 255 characters")
    private String description;

    @Min(value = 1, message = "Quantity must be greater than 0")
    private int quantity;

    @Min(value = 1, message = "Price must be greater than 0")
    private String price;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private String createdAt;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private String updatedAt;
    private boolean processed;
}
