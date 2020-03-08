package com.kota.adv.api.model;

import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class OrderListRequest {

  @NotBlank private String start;

  @NotBlank private String end;
}
