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
public class EnquiryFormat {

    private Long id;
    private int quantity;
    @JsonFormat(pattern="dd-MM-yyyy")
    private LocalDate expectedDeliveryDate;
}
