package com.kota.adv.dao;

import com.kota.adv.dao.model.Order;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface OrderRepository extends CrudRepository<Order, Long> {

  List<Order> findByTimeBetween(
      @Param("start") LocalDateTime start, @Param("end") LocalDateTime end);
}
