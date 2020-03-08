package com.kota.adv.dao.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import javax.persistence.*;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Type;

@Data
@Entity
@Table(name = "products")
public class Product implements Comparable<Product> {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(name = "line_id", updatable = false, nullable = false)
  @ColumnDefault("random_uuid()")
  @Type(type = "uuid-char")
  private UUID lineId;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "description")
  private String description;

  @Column(name = "price", nullable = false)
  private BigDecimal price;

  @JsonIgnore
  @Column(name = "start_time", nullable = false)
  private LocalDateTime startTime;

  @JsonIgnore
  @Column(name = "end_time", nullable = false)
  private LocalDateTime endTime;

  @Override
  public int compareTo(Product o) {
    return this.getStartTime().compareTo(o.getStartTime());
  }
}
