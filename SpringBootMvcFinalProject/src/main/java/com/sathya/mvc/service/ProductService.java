package com.sathya.mvc.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sathya.mvc.Entities.ProductEntity;
//import com.sathya.mvc.controller.Product;
import com.sathya.mvc.model.ProductModel;
import com.sathya.mvc.repository.ProductRepository;

@Service
public class ProductService
{
//creating bridge between service class and repository class by calling object of repository class
@Autowired
ProductRepository productRepository;
	//saving the data by converting model class into entity class
	public void saveProductData(ProductModel productModel)
	{
		double price = productModel.getProPrice();
		String category = productModel.getProCategory();
		double dprice = 0.0; 
		
		 switch (category.toLowerCase()) {
         case "mobile":
             dprice = price * 0.1; 
             break;
         case "laptop":
        	 dprice = price * 0.15;
             break;
         case "tablet":
        	 dprice = price * 0.2;
             break;
         case "camera":
        	 dprice = price * 0.25;
           break;

		 }
	

ProductEntity productEntity = new ProductEntity();
productEntity.setProName(productModel.getProName());
productEntity.setProPrice(productModel.getProPrice());
productEntity.setProCategory(productModel.getProCategory()); // Example for category
productEntity.setProDescription(productModel.getProDescription()); // Example for description
productEntity.setProBrand(productModel.getProBrand());

productEntity.setDisPrice(dprice);

productEntity.setCreatedAt(LocalDate.now());
productEntity.setCreatedBy(System.getProperty("user.name"));
	 
	 productRepository.save(productEntity);	 

}
	public List<ProductEntity> getAllProducts(){
		List<ProductEntity> products=productRepository.findAll();
		return products;
	}
	public void deleteProductById(Long proId) {
		productRepository.deleteById(proId);
		
	}
public void updateProductData(long proId,ProductModel productModel) {
		
		
		Optional<ProductEntity> productEntityoptional=productRepository.findById(proId);
		
		if(productEntityoptional.isPresent())
		{
			ProductEntity productEntity=productEntityoptional.get();
			
			Double price=productModel.getProPrice();
			String catgory=productModel.getProCategory();
			Double discountPrice=0.0;
			 switch (catgory.toLowerCase()) {
		     case "mobile":
		    	 discountPrice = price * 0.1; 
		         break;
		     case "laptop":
		    	 discountPrice = price * 0.15;
		         break;
		     case "tablet":
		    	 discountPrice  = price * 0.2;
		         break;
		     case "camera":
		    	 discountPrice  = price * 0.25;
		         break;
			 }
			
			 // Update the entity with new values from ProductModel (DTO)
            productEntity.setProName(productModel.getProName());
            productEntity.setProPrice(price);
            productEntity.setProBrand(productModel.getProBrand());
            productEntity.setProDescription(productModel.getProDescription());
            productEntity.setProCategory(productModel.getProCategory());
            productEntity.setDisPrice(discountPrice);
            // Save the updated entity back to the database
            productRepository.save(productEntity);
		}
		
	}

public ProductModel getProductById(Long proId) {		
	ProductEntity productEntity= productRepository.findById(proId).get();	
    ProductModel productModel = new ProductModel();
    productModel.setProName(productEntity.getProName());
    productModel.setProBrand(productEntity.getProBrand());
    productModel.setProPrice(productEntity.getProPrice());
    productModel.setProCategory(productEntity.getProCategory());
    productModel.setProDescription(productEntity.getProDescription());
    
    return productModel;

}
	// New method to find a product by ID    	
	public ProductEntity searchProductById(long proId) {
		return productRepository.findById(proId).orElse(null);
	}

}