package com.example.StoreManagement.service;

import com.example.StoreManagement.model.*;
import com.example.StoreManagement.repository.ProductRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImplementation implements ProductService {


    Logger log = LoggerFactory.getLogger(ProductServiceImplementation.class);

    @Autowired
    ProductRepo productRepo;


    public Product addProductService(Product product) {

       log.info("Inside addProductService");
        //setting expiration as null if products is Non-Consumable
       if(!product.isConsumable()){
           product.setExpiryDate(null);
           log.info("product is Non-Consumable hence expiryDate is set as null");
       }

       try{
           productRepo.save(product);
       }catch (Exception e){
           e.printStackTrace();
           log.error("Error: " + e);
       }

       log.info("product details Saved in Database");

       return product;
    }

    public Response getProductDetailsService(BuyerEnquiry buyerEnquiry){
            log.info("Inside getProductDetailsService");

            Optional<Product> product = null;

            List<ResponseFormat> responseFormatList = new ArrayList<>();
            ResponseFormat responseFormat = null;
            Response response= new Response();

            for(EnquiryFormat x : buyerEnquiry.getEnquiryList()){

                try{
                    product = productRepo.findById(x.getId());
                }catch (Exception e){
                    e.printStackTrace();
                    log.error("Error: " + e);
                }

                responseFormat = new ResponseFormat();

                if(product.isPresent()) {
                    log.info("product is present in Database");
                    //If products is consumable then only expiration validation should be done.
                    if (product.get().isConsumable()) {
                        log.info("product is Consumable");
                        if (product.get().getExpiryDate().toEpochDay() - x.getExpectedDeliveryDate().toEpochDay() <= 0) {
                            responseFormat.setMessage("Product is expired !!!");
                            responseFormatList.add(responseFormat);
                            continue;
                        }
                    } else {
                        log.info("product is Non-Consumable");
                    }

                    // MinOrderQty validation
                    if (x.getQuantity() < product.get().getMinQty()) {
                        log.info("product should be minimum " + product.get().getMinQty() + " " + product.get().getUom());
                        responseFormat.setMessage("You have to order atleast " + product.get().getMinQty() + " " + product.get().getUom());
                        responseFormatList.add(responseFormat);
                        continue;

                    }
                    // Multiplier Validation
                    if(x.getQuantity() % product.get().getMinQty() != 0){
                        log.info("Product quantity should be a multiple of " + product.get().getMinQty());
                        responseFormat.setMessage("Product quantity should be a multiple of " + product.get().getMinQty());
                        responseFormatList.add(responseFormat);
                        continue;
                    }
                    responseFormat.setMessage("Successful Response");
                    responseFormat.setDeliveredBy(x.getExpectedDeliveryDate());
                    responseFormat.setOrderedQuantity(x.getQuantity());
                    responseFormat.setTotalPrice((x.getQuantity() * product.get().getMinPrice()) / product.get().getMinQty());
                    responseFormat.setProduct(product.get());
                    responseFormatList.add(responseFormat);

                }
                else{
                    log.info("product is not present in DB");
                    responseFormat.setMessage("product is not present in DB");
                    responseFormatList.add(responseFormat);
//                    response.setResponseList("product is not present in DB");

                }
            }
                response.setResponseList(responseFormatList);
                return response;




    }
}
