package com.kota.adv.dao.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@DynamicUpdate
@Table(name = "products")
public class Product implements Comparable<Product> {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "line_id", nullable = false)
  private UUID lineId;

  @NotBlank
  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "description")
  private String description;

  @DecimalMin(value = "0.0")
  @Digits(integer = 7, fraction = 2)
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
