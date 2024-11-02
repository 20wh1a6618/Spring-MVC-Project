package com.sathya.mvc.controller;

import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.sathya.mvc.Entities.ProductEntity;
import com.sathya.mvc.model.ProductModel;
import com.sathya.mvc.service.ProductService;

import jakarta.validation.Valid;

@Controller
public class ProductController {
	//creating the bridge between controller class and service class
	@Autowired
	ProductService productService;
	//get the product form to add details
     @GetMapping("/productform")
     public String getProductForm(Model model){
         //give the form with empty Object
    	 ProductModel productModel = new ProductModel();         
         productModel.setProBrand("sathya");
         //adding the empty object into model to send to view
        model.addAttribute("productModel", productModel);
        model.addAttribute("page","productform");
         return "index";
    }
     //to check the data saved correctly
     @PostMapping("/saveProduct")
 	public String saveProduct(@Valid ProductModel productModel, BindingResult result, Model model)
 	{
    	 if (result.hasErrors()) {
    		 return "add-product";
    	 }
 		productService.saveProductData(productModel);
 		model.addAttribute("page","saveProduct");
 		return "index";
 	}
     //getting all the product details
     @GetMapping("/getProducts")
     public String getProducts(Model model)
     {
    	 List<ProductEntity> products = productService.getAllProducts();
    	 model.addAttribute("products",products);
    	 model.addAttribute("page","getProducts");
    	 return "index";
     }
     //deleting the product by its id
     @GetMapping("/delete")
     public String deleteProduct(@RequestParam  Long proId) {
         productService.deleteProductById(proId); 
         return "redirect:/getProducts";
     }
     // searching the product details by giving it id
     @GetMapping("/searchform")
	 public String getSearchForm(Model model) {
		 ProductModel productModel = new ProductModel();
	     model.addAttribute("productModel", productModel);
	     model.addAttribute("page","searchform");
	     return "index"; 
	 }
     //getting the product details
	 @GetMapping("/searchProduct")
	 public String searchProduct(@RequestParam Long proId, Model model) {
	     ProductEntity product = productService.searchProductById(proId);
	     if (product != null) {
	         model.addAttribute("product", product);
	     } else {
	         model.addAttribute("error", "Product not found!");
	     }
	     model.addAttribute("page","searchform");
	     return "index"; 
	}
     
    // editing the details
	 @GetMapping("/edit")
	    public String showEditForm(@RequestParam("proId") Long proId, Model model) {
	        // Fetch the product by ID and add it to the model
	        ProductModel productModel = productService.getProductById(proId);
	        model.addAttribute("productModel", productModel);
	        model.addAttribute("categories", Arrays.asList("Mobile", "Camera", "tablet", "Laptop"));

	        model.addAttribute("proId", proId);	        
	        return "editform";  
	    }


    //updating the details
	@PostMapping("/update")
	public String updateProduct(@RequestParam("proId") Long proId,@ModelAttribute ProductModel productModel) { // Ensure proper binding
		productService.updateProductData(proId,productModel);
		return "redirect:/getProducts"; // Redirect after successful update
	}
	//giving info to contact
	@GetMapping("/contact")
	public String submitContactForm(Model model ) {
		model.addAttribute("page","contact");
	    return "index";  // Return the view name after processing
	}
	//gives info about us
	@GetMapping("/about")
    public String about(Model model) {
		model.addAttribute("page","about");
		return "index";
    }
	//gives home page
	@GetMapping("/")
    public String homePage() {
		//model.addAttribute("page","home");
		return "index";
    }

}
    
    
    
    
 	
 	
 

