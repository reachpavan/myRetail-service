package org.pavan.starter.dao;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by parepu on 2/23/2016.
 */
@Document
public class Product {
  @Id private String id;

  @Indexed private String productId;
  private Double price;
  private String currency;

  public Product(String productId, Double price, String currency) {
    this.productId = productId;
    this.price = price;
    this.currency = currency;
  }

  public String getProductId() {
    return productId;
  }

  public Double getPrice() {
    return price;
  }

  public String getCurrency() {
    return currency;
  }


  public void setProductId(String productId) {
    this.productId = productId;
  }

  public void setPrice(Double price) {
    this.price = price;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  @Override
  public String toString() {
    return String.format(
        "Customer[id=%s, productId='%s', price='%s', currency='%s']",
        id, productId, price, currency);
  }
}
