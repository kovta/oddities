package com.kota.adv.dao;

import com.kota.adv.dao.model.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Long> {}
