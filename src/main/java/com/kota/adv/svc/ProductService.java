package com.kota.adv.svc;

import static java.util.stream.Collectors.*;

import com.kota.adv.dao.ProductRepository;
import com.kota.adv.dao.model.Product;
import java.util.*;
import java.util.stream.StreamSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductsService {

  @Autowired private ProductRepository repository;

  public List<Product> fetchProducts() {
    return StreamSupport.stream(repository.findAll().spliterator(), false)
        .collect(groupingBy(Product::getId, maxBy(Product::compareTo)))
        .values()
        .stream()
        .map(Optional::get)
        .collect(toList());
  }

  public Product save(Product product) {
    return repository.save(product);
  }
}
