package org.pavan.starter.dto;

/**
 * Created by parepu on 2/24/2016.
 */
public enum ErrorType {
  GENERIC_INTERNAL_ERROR ("100", "Internal server exception."),
  GENERIC_BAD_REQUEST ("101", "Bad Request."),
  PRODUCT_NOT_FOUND ("200", "Product with productId: %s not found.");

  private String errorCode;
  private String errorMessage;

  ErrorType (String errorCode, String errorMessage) {
    this.errorCode = errorCode;
    this.errorMessage = errorMessage;
  }

  public String getErrorCode() {
    return errorCode;
  }

  public String getErrorMessage(String... args) {
    return String.format(errorMessage, args);
  }
}
