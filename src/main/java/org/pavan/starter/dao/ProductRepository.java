package org.pavan.starter.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by parepu on 2/23/2016.
 */
public interface ProductRepository extends MongoRepository<Product, String> {
  Product findByProductId(String productId);
}
