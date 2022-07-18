package com.example.StoreManagement.controller;

import com.example.StoreManagement.model.Product;
//import com.example.StoreManagement.model.NonConsumable;
import com.example.StoreManagement.model.BuyerEnquiry;
import com.example.StoreManagement.model.Response;
import com.example.StoreManagement.service.ProductService;
//import com.example.StoreManagement.service.NonConsumableService;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @Autowired
    ProductService productService;


    Logger log = LoggerFactory.getLogger(Controller.class);

    @PostMapping("/add")
    public ResponseEntity<?> addProduct(@RequestBody Product product){
            log.info("Inside /add of Controller");
            Product productObj = productService.addProductService(product);
            log.info("Product: " + new Gson().toJson(productObj));
            return new ResponseEntity<>(productObj, HttpStatus.OK);

    }
    @PostMapping("/getEnquiryDetails")
    public ResponseEntity<?> getProductDetails(@RequestBody BuyerEnquiry buyerEnquiry){
        log.info("Inside /getEnquiryDetails of Controller");
        Response response = productService.getProductDetailsService(buyerEnquiry);
        log.info("Response: " + new Gson().toJson(response));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
