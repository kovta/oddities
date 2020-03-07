package com.kota.adv.dao;

import com.kota.adv.dao.model.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, Long> {}
