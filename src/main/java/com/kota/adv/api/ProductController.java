package com.kota.adv.api;

import com.kota.adv.dao.model.Product;
import com.kota.adv.svc.ProductService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "products")
public class ProductsController {

  @Autowired private ProductService service;

  @GetMapping
  public List<Product> retrieveProducts() {
    return service.fetchProducts();
  }

  @PostMapping
  public Product createProduct(
      @RequestParam("name") String name,
      @RequestParam("description") String description,
      @RequestParam("price") Double price) {
    return service.save(name, description, price);
  }

  @PostMapping
  public Product updateProduct(
      @RequestParam("id") Long id,
      @RequestParam("name") String name,
      @RequestParam("description") String description,
      @RequestParam("price") Double price) {
    return service.save(id, name, description, price);
  }
}
