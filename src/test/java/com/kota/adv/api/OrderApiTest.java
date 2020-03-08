package com.kota.adv.api;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

import com.kota.adv.App;
import com.kota.adv.api.model.OrderListRequest;
import com.kota.adv.api.model.OrderPlacementRequest;
import com.kota.adv.dao.model.Product;
import io.restassured.response.Response;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest(classes = App.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ExtendWith(SpringExtension.class)
public class OrderApiTest {

  private static final String API_ROOT = "http://localhost:8088/api/v0/odd-svc/orders";
  private static final String PRODUCT_API_ROOT = "http://localhost:8088/api/v0/odd-svc/products";

  @Test
  @DisplayName("When fetching all orders return 200 (OK)")
  public void testGetAll() {
    OrderListRequest req = new OrderListRequest();
    req.setStart(LocalDateTime.now().minusDays(10).toString());
    req.setEnd(LocalDateTime.now().toString());

    Response response =
        given().contentType(MediaType.APPLICATION_JSON_VALUE).body(req).get(API_ROOT);

    assertEquals(HttpStatus.OK.value(), response.getStatusCode());
  }

  @Test
  @DisplayName("When placing a new order return 201 (CREATED)")
  public void testRegister() {
    OrderPlacementRequest req = new OrderPlacementRequest();
    req.setBuyer("marcus.wulfhart@mail.com");
    req.setProducts(Arrays.asList(1L, 3L));

    Response response =
        given().contentType(MediaType.APPLICATION_JSON_VALUE).body(req).post(API_ROOT);

    assertEquals(HttpStatus.CREATED.value(), response.getStatusCode());
  }

  @Test
  @DisplayName("When placing a new order with an invalid product return 400 (BAD_REQUEST)")
  public void testInvalidRegister() {
    OrderPlacementRequest req = new OrderPlacementRequest();
    req.setBuyer("marcus.wulfhart@mail.com");
    req.setProducts(Arrays.asList(1L, -3L));

    Response response =
        given().contentType(MediaType.APPLICATION_JSON_VALUE).body(req).post(API_ROOT);

    assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode());
  }

  @Test
  @DisplayName("Test placed order contains originally provided products even after update")
  public void testUpdateResilience() {

    // Register 2 products
    Product p = new Product();
    p.setName("Flamberge");
    p.setPrice(BigDecimal.valueOf(11L));
    final var r1 =
        given().contentType(MediaType.APPLICATION_JSON_VALUE).body(p).post(PRODUCT_API_ROOT);
    final var id1 = ((Integer) r1.jsonPath().get("id")).longValue();
    p.setName("Moos clump");
    p.setPrice(BigDecimal.valueOf(0.5));
    final var r2 =
        given().contentType(MediaType.APPLICATION_JSON_VALUE).body(p).post(PRODUCT_API_ROOT);
    final var id2 = ((Integer) r2.jsonPath().get("id")).longValue();

    // Order Both products
    OrderPlacementRequest req = new OrderPlacementRequest();
    req.setBuyer("grigs.atakar@mail.com");
    req.setProducts(Arrays.asList(id1, id2));
    final var r3 = given().contentType(MediaType.APPLICATION_JSON_VALUE).body(req).post(API_ROOT);

    // Update product
    p.setName("Estoc");
    p.setPrice(BigDecimal.valueOf(9L));
    final var r4 =
        given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(p)
            .patch(PRODUCT_API_ROOT + "/" + id1);
    final var id3 = ((Integer) r4.jsonPath().get("id")).longValue();

    // Retrieve order
    final String time = r3.jsonPath().get("time").toString();
    OrderListRequest getReq = new OrderListRequest();
    getReq.setStart(LocalDateTime.parse(time).minusMinutes(1).toString());
    getReq.setEnd(LocalDateTime.now().toString());
    final var r5 = given().contentType(MediaType.APPLICATION_JSON_VALUE).body(getReq).get(API_ROOT);
    final List<LinkedHashMap> products = r5.jsonPath().get("[0].products");

    assertTrue(
        products.stream().anyMatch(product -> ((Integer) product.get("id")).longValue() == id1));
    assertTrue(
        products.stream().anyMatch(product -> ((Integer) product.get("id")).longValue() == id2));
    assertFalse(
        products.stream().anyMatch(product -> ((Integer) product.get("id")).longValue() == id3));
    assertEquals(
        new BigDecimal("11.5"),
        BigDecimal.valueOf(((Float) r5.jsonPath().get("[0].value")).doubleValue()));
  }
}
