package com.example.StoreManagement.service;

import com.example.StoreManagement.model.Product;
import com.example.StoreManagement.model.BuyerEnquiry;
import com.example.StoreManagement.model.Response;
import org.springframework.stereotype.Service;

@Service
public interface ProductService {

    Product addProductService(Product product);

    Response getProductDetailsService(BuyerEnquiry buyerEnquiry);

}
