package com.example.StoreManagement.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data

public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String productName;

    @JsonFormat(pattern="dd-MM-yyyy")
    private LocalDate manufacturingDate;

    @JsonFormat(pattern="dd-MM-yyyy")
    private LocalDate expiryDate;
    private boolean consumable;
    private String uom;
    private int minQty;
    private int minPrice;

}
