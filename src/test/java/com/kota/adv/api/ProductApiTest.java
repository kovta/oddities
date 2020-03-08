package com.kota.adv.api;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.kota.adv.dao.model.Product;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import java.math.BigDecimal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

public class ProductApiTest {

  private static final String API_ROOT = "http://localhost:8088/api/v0/odd-svc/products";

  @Test
  @DisplayName("When fetching all products return 200 (OK)")
  public void testGetAll() {
    Response response = RestAssured.get(API_ROOT);

    assertEquals(HttpStatus.OK.value(), response.getStatusCode());
  }

  @Test
  @DisplayName("When creating new product return 201 (CREATED)")
  public void testRegister() {
    Product p = new Product();
    p.setName("Hatchet");
    p.setPrice(BigDecimal.valueOf(3));

    Response response =
        RestAssured.given().contentType(MediaType.APPLICATION_JSON_VALUE).body(p).post(API_ROOT);

    assertEquals(HttpStatus.CREATED.value(), response.getStatusCode());
  }

  @Test
  @DisplayName("When updating a product return 201 (CREATED)")
  public void testModify() {
    Product p = new Product();
    p.setName("Leather pouch");
    p.setPrice(BigDecimal.valueOf(4.5));

    Response response =
        RestAssured.given().contentType(MediaType.APPLICATION_JSON_VALUE).body(p).post(API_ROOT);
    final Long id = ((Integer) response.jsonPath().get("id")).longValue();

    p.setId(id);
    p.setPrice(BigDecimal.valueOf((long) 4));

    Response updateResponse =
        RestAssured.given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(p)
            .patch(API_ROOT + "/" + id);

    assertEquals(HttpStatus.CREATED.value(), updateResponse.getStatusCode());
    assertEquals(
        BigDecimal.valueOf(4),
        BigDecimal.valueOf(((Integer) updateResponse.jsonPath().get("price")).longValue()));
  }
}
