package com.kota.adv.api;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import com.kota.adv.dao.model.Product;
import com.kota.adv.svc.ProductService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "products")
public class ProductController {

  @Autowired private ProductService service;

  @GetMapping
  @ResponseStatus(OK)
  public List<Product> retrieveProducts() {
    return service.fetchProducts();
  }

  @PostMapping
  @ResponseStatus(CREATED)
  public Product createProduct(@RequestBody Product product) {
    return service.save(product);
  }

  @PatchMapping("/{id}")
  @ResponseStatus(CREATED)
  public Product updateProduct(@PathVariable Long id, @RequestBody Product product) {
    return service.save(id, product);
  }
}
