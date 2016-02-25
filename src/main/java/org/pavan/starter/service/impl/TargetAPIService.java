package org.pavan.starter.service.impl;

import org.pavan.starter.service.ProductDescriptionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.*;

/**
 * Created by parepu on 2/23/2016.
 */
@Component
public class TargetAPIService implements ProductDescriptionService {
  private static final Logger logger = LoggerFactory.getLogger(TargetAPIService.class);
  private static final String PRODUCT_COMPOSITE_RESPONSE = "product_composite_response";
  private static final String ITEMS = "items";
  private static final String GENERAL_DESCRIPTION = "general_description";
  private static String HOST = "api.target.com";
  private static String URI = "products/v3";
  private static String PARAMS = "fields=descriptions&id_type=TCIN&key=43cJWpLjH8Z8oR18KdrZDBKAgLLQKJjz";
  private static String URL_PATTERN = "http://%s/%s/%s?%s";

  @Autowired
  private RestTemplate restTemplate;

  @Override
  public String getProductDescription(String productId) {
    String URL = String.format(URL_PATTERN, HOST, URI, productId, PARAMS);
    logger.info("Calling Target API service for productId: " + productId);
    String productDescription = null;
    try {
      ResponseEntity<Map> responseEntity = restTemplate.getForEntity(URL, Map.class);
      HttpStatus statusCode = responseEntity != null ? responseEntity.getStatusCode() : null;
      logger.info("Target API service response status code: " + statusCode);
      if (statusCode == HttpStatus.OK) {
        productDescription = (String) extractFirstValueFromJson(responseEntity.getBody(),
            new LinkedList<>(Arrays.asList((String[]) new String[]{PRODUCT_COMPOSITE_RESPONSE, ITEMS, GENERAL_DESCRIPTION})));
      }
    } catch (Exception e) {
      logger.info("Trouble getting the product description for productId: " + productId, e);
    }
    return productDescription;
  }

  private Object extractFirstValueFromJson(Object value, Deque<String> keys) {
    logger.info(value.toString());
    if (value != null && keys != null && !keys.isEmpty()) {
      if (value instanceof List) {
        for (Object obj : (List)value) {
          Object val = extractFirstValueFromJson(obj, keys);
          if (val != null) {
            return val;
          }
        }
      } else if (value instanceof Map){
        Object obj = ((Map) value).get(keys.removeFirst());
        return extractFirstValueFromJson(obj, keys);
      }
    }
    return value;
  }
}
