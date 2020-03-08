package com.kota.adv.api;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import com.kota.adv.dao.model.Order;
import com.kota.adv.svc.OrderService;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "orders")
public class OrderController {

  @Autowired private OrderService service;

  @GetMapping
  @ResponseStatus(OK)
  public List<Order> retrieveOrders(
      @RequestParam("start") String start, @RequestParam("end") String end) {
    return service.fetchOrders(LocalDateTime.parse(start), LocalDateTime.parse(end));
  }

  @PostMapping
  @ResponseStatus(CREATED)
  public Order placeOrder(
      @RequestParam("buyer") String buyer, @RequestParam("products") List<Long> products) {
    return service.place(buyer, products);
  }
}
