package com.kota.adv.dao.model;

import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "orders")
public class Order {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(name = "buyer", nullable = false)
  private String buyer;

  @Column(name = "time", nullable = false)
  private LocalDateTime time;

  @ManyToMany
  @JoinTable(
      name = "order_products",
      joinColumns = @JoinColumn(name = "order_id"),
      inverseJoinColumns = @JoinColumn(name = "product_id"))
  private List<Product> products;
}
