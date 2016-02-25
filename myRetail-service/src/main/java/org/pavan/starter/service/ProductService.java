package org.pavan.starter.service;

import org.pavan.starter.dto.ProductResponse;

import java.util.List;

/**
 * Created by parepu on 2/23/2016.
 */
public interface ProductService {
  List<ProductResponse> listAll(int page, int size);

  boolean exists(String productId);

  ProductResponse getProductById(String productId);

  ProductResponse updateProductPrice (String productId, Double price);
}
