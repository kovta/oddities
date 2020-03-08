package com.kota.adv.svc;

import static java.util.stream.Collectors.*;

import com.kota.adv.dao.ProductRepository;
import com.kota.adv.dao.model.Product;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.StreamSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

  @Autowired private ProductRepository repository;

  public List<Product> fetchProducts() {
    return StreamSupport.stream(repository.findAll().spliterator(), false)
        .collect(groupingBy(Product::getLineId, maxBy(Product::compareTo)))
        .values()
        .stream()
        .map(Optional::get)
        .collect(toList());
  }

  public Product save(Product p) {
    p.setStartTime(LocalDateTime.now());
    return repository.save(p);
  }

  public Product save(Long id, Product p) {
    final var now = LocalDateTime.now();

    Product origin =
        repository
            .findById(id)
            .orElseThrow(
                () -> new IllegalArgumentException("No product found for id: " + id.toString()));
    origin.setEndTime(now);
    repository.save(origin);

    Product mod = new Product();
    mod.setLineId(origin.getLineId());
    mod.setName(p.getName());
    mod.setDescription(p.getDescription());
    mod.setPrice(p.getPrice());
    mod.setStartTime(now);
    return repository.save(mod);
  }
}
