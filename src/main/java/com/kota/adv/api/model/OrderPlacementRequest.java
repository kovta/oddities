package com.kota.adv.api.model;

import java.util.List;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class OrderPlacementRequest {

  @Email private String buyer;
  @NotBlank private List<Long> products;
}
