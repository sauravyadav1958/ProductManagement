package com.example.StoreManagement.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Response {
    private String message;
    @JsonFormat(pattern="dd-MM-yyyy")
    private LocalDate deliveredBy;
    private int orderedQuantity;
    private int totalPrice;
    private Product product;

}
