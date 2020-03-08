package com.kota.adv.api;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import com.kota.adv.api.model.OrderListRequest;
import com.kota.adv.api.model.OrderPlacementRequest;
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
  public List<Order> retrieveOrders(@RequestBody OrderListRequest req) {
    return service.fetchOrders(
        LocalDateTime.parse(req.getStart()), LocalDateTime.parse(req.getEnd()));
  }

  @PostMapping
  @ResponseStatus(CREATED)
  public Order placeOrder(@RequestBody OrderPlacementRequest req) {
    return service.place(req.getBuyer(), req.getProducts());
  }
}
