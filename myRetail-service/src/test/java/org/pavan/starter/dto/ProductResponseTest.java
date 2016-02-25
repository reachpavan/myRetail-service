package org.pavan.starter.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by parepu on 2/24/2016.
 */
public class ProductResponseTest {
  @Test
  public void testDeserialization() throws IOException {
    //assume
    String jsonPattern = "{\"productId\":\"%s\",\"name\":\"%s\",\"price\": %f,\"currency\":\"%s\"}";
    String productId = "13860428";
    String name = "BIG LEBOWSKI, THE Blu-ray";
    Double price = 12.0;
    String currency = "USD";
    //act
    String jsonValue = String.format(jsonPattern, productId, name, price, currency);
    ProductResponse productResponse = new ObjectMapper().readValue(jsonValue, ProductResponse.class);
    //assert
    Assert.assertEquals(productId, productResponse.getProductId());
    Assert.assertEquals(name, productResponse.getName());
    Assert.assertEquals(price, productResponse.getPrice());
    Assert.assertEquals(currency, productResponse.getCurrency());
  }
}