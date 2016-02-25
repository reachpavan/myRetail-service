package org.pavan.starter.service.impl;

import org.pavan.starter.dao.Product;
import org.pavan.starter.dao.ProductRepository;
import org.pavan.starter.dto.ProductResponse;
import org.pavan.starter.service.ProductDescriptionService;
import org.pavan.starter.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Created by parepu on 2/23/2016.
 */
@Component
public class ProductServiceImpl implements ProductService {
  private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

  @Autowired
  ProductRepository productRepository;

  @Autowired
  private ProductDescriptionService productDescriptionService;

  @Override
  public List<ProductResponse> listAll(int page, int size) {
    Page<Product> productPage = productRepository.findAll(new PageRequest(page, size));
    Iterable<Product> iterable = () -> productPage.iterator();
    return StreamSupport.stream(iterable.spliterator(), false)
        .map(p -> ProductResponse.builder()
            .withProductId(p.getProductId())
//            .withPrice(p.getPrice())
//            .withCurrency(p.getCurrency())
            .build())
        .collect(Collectors.toList());
  }

  @Override
  public ProductResponse getProductById(String productId) {
    Product product = productRepository.findByProductId(productId);
    logger.info(String.format("Database response for productId: %s [%s]", productId, String.valueOf(product)));
    if (product != null) {
      String description = productDescriptionService.getProductDescription(productId);

      return ProductResponse.builder()
          .withProductId(productId)
          .withPrice(product.getPrice())
          .withCurrency(product.getCurrency())
          .withName(description)
          .build();
    }
    return null;
  }

  @Override
  public ProductResponse updateProductPrice(String productId, Double price) {
    Product product = productRepository.findByProductId(productId);
    if (product != null) {
      product.setPrice(price);
      product = productRepository.save(product);
      return ProductResponse.builder()
          .withProductId(productId)
          .withPrice(product.getPrice())
          .withCurrency(product.getCurrency())
          .build();
    }
    return null;
  }

  @Override
  public boolean exists(String productId) {
    return productRepository.findByProductId(productId) != null;
  }
}
