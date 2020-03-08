package com.kota.adv.svc;

import static org.junit.jupiter.api.Assertions.*;

import com.kota.adv.dao.ProductRepository;
import com.kota.adv.dao.model.Product;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

  @Mock private ProductRepository repo;

  @InjectMocks private ProductService service;

  @Test
  @DisplayName("Test retrieving only latest product versions")
  public void testFetchAll() {
    final UUID id1 = UUID.randomUUID();
    final UUID id2 = UUID.randomUUID();
    List<Product> products = new ArrayList<>();
    Product a1 =
        new Product(
            1L,
            id1,
            "Dagger",
            null,
            BigDecimal.valueOf(1.0),
            LocalDateTime.now().minusDays(2),
            null);
    Product b1 =
        new Product(
            2L,
            id2,
            "Pellet",
            null,
            BigDecimal.valueOf(1.5),
            LocalDateTime.now().minusDays(3),
            LocalDateTime.now().minusDays(1));
    Product b2 =
        new Product(
            3L,
            id2,
            "Pellet",
            null,
            BigDecimal.valueOf(0.5),
            LocalDateTime.now().minusDays(1),
            null);
    products.add(a1);
    products.add(b1);
    products.add(b2);

    Mockito.when(repo.findAll()).thenReturn(products);

    final List<Product> productList = service.fetchProducts();

    assertEquals(2, productList.size());
    assertTrue(productList.contains(a1) && productList.contains(b2));
    assertFalse(productList.contains(b1));
  }
}
