package com.nineleaps.productservice.controller;

import com.nineleaps.productservice.model.Product;
import com.nineleaps.productservice.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

import static com.nineleaps.productservice.utilities.AppConstant.BASE_URI;

/**
 * @author Dilip Chauhan
 * Created on 30/03/2020
 */
@RestController
@RequestMapping(value = BASE_URI + "/products")
@Api(value = "Product resource rest endpoint", description = "Shows the product information")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{productId}")
    @ApiOperation(value = "Get product from the system")
    public Product getProductById(@PathVariable("productId") final UUID productId) {
        return productService.getProductById(productId);
    }

    @ApiOperation(value = "Save product")
    @PostMapping(value = "/")
    @ResponseStatus(code = HttpStatus.CREATED)
    public Product persistProduct(@Valid @RequestBody final Product product) {
        return productService.saveOrUpdateProduct(product);
    }

    @ApiOperation(value = "Update product")
    @PutMapping(value = "/")
    @ResponseStatus(code = HttpStatus.OK)
    public Product updateProduct(@Valid @RequestBody final Product product) {
        return productService.saveOrUpdateProduct(product);
    }

    @ApiOperation(value = "Delete product from the system")
    @DeleteMapping(value = "/{productId}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable final UUID productId) {
        productService.deleteProduct(productId);
    }
}
