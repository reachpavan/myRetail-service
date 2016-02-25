package org.pavan.starter.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.pavan.starter.dao.Product;

import java.util.Map;

/**
 * Created by parepu on 2/23/2016.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductResponse {
  private String productId;
  private String name;
  private Double price;
  private String currency;

  @JsonCreator
  public ProductResponse(@JsonProperty("productId") String productId,
                         @JsonProperty("name") String name,
                         @JsonProperty("price") Double price,
                         @JsonProperty("currency") String currency) {
    this.productId = productId;
    this.name = name;
    this.price = price;
    this.currency = currency;
  }

  public String getProductId() {
    return productId;
  }

  public String getName() {
    return name;
  }

  public Double getPrice() {
    return price;
  }

  public String getCurrency() {
    return currency;
  }

  public static Builder builder() {
    return new ProductResponse.Builder();
  }

  public static class Builder {
    private String productId;
    private String name;
    private Double price;
    private String currency;

    Builder() {
    }

    public Builder withProductId(String productId) {
      this.productId = productId;
      return this;
    }

    public Builder withName(String name) {
      this.name = name;
      return this;
    }

    public Builder withPrice(Double price) {
      this.price = price;
      return this;
    }

    public Builder withCurrency(String currency) {
      this.currency = currency;
      return this;
    }

//    public Builder withProduct(Product product) {
//      if (product != null) {
//        this.productId = product.getProductId();
//        this.price = product.getPrice();
//      }
//      return this;
//    }

    public ProductResponse build() {
      return new ProductResponse(productId, name, price, currency);
    }
  }
}
