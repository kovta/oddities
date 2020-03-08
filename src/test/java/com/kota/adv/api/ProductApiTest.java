package com.kota.adv.api;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.kota.adv.App;
import com.kota.adv.dao.model.Product;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import java.math.BigDecimal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest(classes = App.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ExtendWith(SpringExtension.class)
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
    p.setPrice(BigDecimal.valueOf(3L));
    Response response =
        given().contentType(MediaType.APPLICATION_JSON_VALUE).body(p).post(API_ROOT);

    assertEquals(HttpStatus.CREATED.value(), response.getStatusCode());
  }

  @Test
  @DisplayName("When updating a product return 201 (CREATED)")
  public void testModify() {
    Product p = new Product();
    p.setName("Leather pouch");
    p.setPrice(BigDecimal.valueOf(4.5));
    Response response =
        given().contentType(MediaType.APPLICATION_JSON_VALUE).body(p).post(API_ROOT);
    final long id = ((Integer) response.jsonPath().get("id")).longValue();

    p.setPrice(BigDecimal.valueOf(4L));
    Response updateResponse =
        given().contentType(MediaType.APPLICATION_JSON_VALUE).body(p).patch(API_ROOT + "/" + id);

    assertEquals(HttpStatus.CREATED.value(), updateResponse.getStatusCode());
    assertEquals(
        BigDecimal.valueOf(4),
        BigDecimal.valueOf(((Integer) updateResponse.jsonPath().get("price")).longValue()));
  }

  @Test
  @DisplayName("When trying to update an invalid product return 400 (BAD_REQUEST)")
  public void testFalseUpdate() {
    Product p = new Product();
    p.setName("Potion");
    p.setPrice(BigDecimal.valueOf(3L));
    given().contentType(MediaType.APPLICATION_JSON_VALUE).body(p).post(API_ROOT);

    p.setPrice(BigDecimal.valueOf(2L));
    Response updateResponse =
        given().contentType(MediaType.APPLICATION_JSON_VALUE).body(p).patch(API_ROOT + "/" + -1L);

    assertEquals(HttpStatus.BAD_REQUEST.value(), updateResponse.getStatusCode());
  }
}
