package com.kota.adv.svc;

import static java.util.stream.Collectors.toList;

import com.kota.adv.dao.OrderRepository;
import com.kota.adv.dao.ProductRepository;
import com.kota.adv.dao.model.Order;
import com.kota.adv.dao.model.Product;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.StreamSupport;
import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

  @Autowired private OrderRepository repository;

  @Autowired private ProductRepository productRepository;

  public List<Order> fetchOrders(LocalDateTime from, LocalDateTime to) {
    return repository.findByTimeBetween(from, to);
  }

  public Order place(String buyer, List<Long> productList) {
    Order order = new Order();
    order.setBuyer(buyer);
    order.setTime(LocalDateTime.now());
    productList.forEach(
        id -> {
          if (!productRepository.existsById(id)) {
            throw new EntityNotFoundException();
          }
        });
    final var products =
        StreamSupport.stream(productRepository.findAllById(productList).spliterator(), false)
            .collect(toList());
    order.setValue(
        products.stream().map(Product::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add));
    order.setProducts(products);
    return repository.save(order);
  }
}
