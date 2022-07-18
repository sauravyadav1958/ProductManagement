package com.example.StoreManagement.service;

import com.example.StoreManagement.model.Response;
import com.example.StoreManagement.model.Product;
import com.example.StoreManagement.model.BuyerEnquiry;
import com.example.StoreManagement.repository.ProductRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

            try{
                product = productRepo.findById(buyerEnquiry.getId());
            }catch (Exception e){
                e.printStackTrace();
                log.error("Error: " + e);
            }

            Response response = new Response();
            if(product.isPresent()){
                log.info("product is present in Database");
                //If products is consumable then only expiration validation should be done.
                if(product.get().isConsumable()) {
                    log.info("product is Consumable");
                    if (product.get().getExpiryDate().toEpochDay() - buyerEnquiry.getExpectedDeliveryDate().toEpochDay() <= 0) {
                        response.setMessage("Product is expired !!!");
                        return response;
                    }
                }else{
                    log.info("product is Non-Consumable");
                }

                // MinOrderQty validation
                if(buyerEnquiry.getQuantity() < product.get().getMinQty()){
                    log.info("product should be minimum " + product.get().getMinQty() + " " + product.get().getUom());
                    response.setMessage("You have to order atleast " + product.get().getMinQty() + " " + product.get().getUom());
                    return response;

                }
                response.setMessage("Successful Response");
                response.setDeliveredBy(buyerEnquiry.getExpectedDeliveryDate());
                response.setOrderedQuantity(buyerEnquiry.getQuantity());
                response.setTotalPrice((buyerEnquiry.getQuantity() * product.get().getMinPrice())/product.get().getMinQty());
                response.setProduct(product.get());
                return response;

            }
                log.info("product is not present in DB");
                 response.setMessage("product is not present in DB");
                 return response;

    }
}
