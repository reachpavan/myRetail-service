package org.pavan.starter.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpHost;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Created by parepu on 2/23/2016.
 */
@Configuration
public class RestClientConfig {
  private static final Logger logger = LoggerFactory.getLogger(RestClientConfig.class);

  private static final int DEFAULT_MAX_TOTAL_CONNECTIONS = 100;

  private static final int DEFAULT_MAX_CONNECTIONS_PER_ROUTE = 5;

  private static final int DEFAULT_READ_TIMEOUT_MILLISECONDS = (60 * 1000);


  @Autowired
  private ObjectMapper objectMapper;

  @Bean
  public ClientHttpRequestFactory httpRequestFactory() {
    return new HttpComponentsClientHttpRequestFactory(httpClient());
  }

  @Bean
  public RestTemplate restTemplate() {
    RestTemplate restTemplate = new RestTemplate(httpRequestFactory());
    List<HttpMessageConverter<?>> converters = restTemplate
        .getMessageConverters();

    for (HttpMessageConverter<?> converter : converters) {
      if (converter instanceof MappingJackson2HttpMessageConverter) {
        MappingJackson2HttpMessageConverter jsonConverter = (MappingJackson2HttpMessageConverter) converter;
        jsonConverter.setObjectMapper(objectMapper);
      }
    }

    return restTemplate;
  }

  @Bean
  public HttpClient httpClient() {
    PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
    connectionManager.setMaxTotal(DEFAULT_MAX_TOTAL_CONNECTIONS);
    connectionManager.setDefaultMaxPerRoute(DEFAULT_MAX_CONNECTIONS_PER_ROUTE);
    connectionManager.setMaxPerRoute(new HttpRoute(new HttpHost("api.target.com")), 20);

    RequestConfig requestConfig = RequestConfig.custom()
        .setConnectTimeout(DEFAULT_READ_TIMEOUT_MILLISECONDS)
        .build();

    return HttpClientBuilder.create()
        .setConnectionManager(connectionManager)
        .setDefaultRequestConfig(requestConfig)
        .build();
  }
}
