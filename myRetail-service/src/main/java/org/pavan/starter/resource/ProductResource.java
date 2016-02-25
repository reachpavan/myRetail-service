package org.pavan.starter.resource;

import io.swagger.annotations.*;
import org.pavan.starter.dto.ErrorType;
import org.pavan.starter.dto.ProductResponse;
import org.pavan.starter.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@Controller
@RequestMapping("/v1/product")
public class ProductResource {
  private static final Logger logger = LoggerFactory.getLogger(ProductResource.class);
  @Autowired
  private ProductService productService;

  @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  @ApiOperation(value = "product", nickname = "Get All Product")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "page", value = "page", required = false, dataType = "int", paramType = "query", defaultValue = "0"),
      @ApiImplicitParam(name = "size", value = "size", required = false, dataType = "int", paramType = "query")
  })
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success", response = ResourceSupport.class),
      @ApiResponse(code = 500, message = "Failure")})
  public ResponseEntity<?> showAll(@RequestParam(value = "page", defaultValue = "0", required = false) Integer page,
                                   @RequestParam(value = "size", required = false) Integer size) {

    List<Link> links = new ArrayList<>();
    links.add(new Link(
        new UriTemplate(
            linkTo(ProductResource.class).toUriComponentsBuilder().build().toUriString(),
            buildBaseTemplateVariables()
        ),
        Link.REL_SELF
    ));

    page = page != null ? page : 0;
    size = size != null ? size : Integer.MAX_VALUE;
    List<ProductResponse> productResponseList = productService.listAll(page, size);
    List<Resource> resourceList = null;
    if (productResponseList != null) {
      productResponseList = productResponseList != null ? productResponseList : new ArrayList<>();
      resourceList = productResponseList.stream()
          .map(this::buildResource)
          .collect(Collectors.toList());
    }

    return new ResponseEntity<>(new Resources<>(resourceList, links), HttpStatus.OK);
  }

  @RequestMapping(value = "/{productId}",
      method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ApiOperation(value = "product", nickname = "Get Product ")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "productId", value = "product ID", required = false, dataType = "string", paramType = "path")
  })
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success", response = ProductResponse.class),
      @ApiResponse(code = 404, message = "Not Found"),
      @ApiResponse(code = 500, message = "Failure")})
  @ResponseBody
  public ResponseEntity<?> getProduct(@PathVariable String productId) {
    ProductResponse product = productService.getProductById(productId);
    if (product == null) {
      Resource<?> errorResponse = buildErrorResponse(ErrorType.PRODUCT_NOT_FOUND, productId);
      return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(buildResource(product), HttpStatus.OK);
  }

  @RequestMapping(value = "/{productId}",
      method = RequestMethod.PUT,
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ApiOperation(value = "product", nickname = "Update Product ")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "productId", value = "product ID", required = false, dataType = "string", paramType = "path")
  })
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success", response = ProductResponse.class),
      @ApiResponse(code = 400, message = "Bad Request"),
      @ApiResponse(code = 404, message = "Not Found"),
      @ApiResponse(code = 500, message = "Failure")})
  public ResponseEntity<?> updateProduct(@PathVariable String productId,
                                         @RequestBody ProductResponse input) {
    if (!productService.exists(productId)) {
      Resource<?> errorResponse = buildErrorResponse(ErrorType.PRODUCT_NOT_FOUND, productId);
      return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
    if (input == null || input.getPrice() == null) {
      Resource<?> errorResponse = buildErrorResponse(ErrorType.GENERIC_BAD_REQUEST, productId);
      return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
    ProductResponse product = productService.updateProductPrice(productId, input.getPrice());
    return new ResponseEntity<>(buildResource(product), HttpStatus.OK);
  }

  private Resource<?> buildResource(ProductResponse productResponse) {
    Link link = linkTo(ProductResource.class)
        .slash(productResponse.getProductId())
        .withSelfRel();
    return new Resource<>(productResponse, link);
  }

  private Resource<?> buildErrorResponse(ErrorType errorType, String args) {
    VndErrors.VndError vndError = new VndErrors.VndError(errorType.getErrorCode(), errorType.getErrorMessage(args));
    return new Resource<>(new VndErrors(vndError));
  }

  private static TemplateVariables buildBaseTemplateVariables() {
    return new TemplateVariables(
        new TemplateVariable("page", TemplateVariable.VariableType.REQUEST_PARAM),
        new TemplateVariable("size", TemplateVariable.VariableType.REQUEST_PARAM)
    );
  }
}
