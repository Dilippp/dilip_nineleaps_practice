package com.nineleaps.productservice.controller;

import com.nineleaps.productservice.model.Product;
import com.nineleaps.productservice.service.ProductService;
import com.nineleaps.productservice.utilities.AppConstant;
import com.nineleaps.productservice.utilities.CommonUtil;
import com.nineleaps.productservice.utilities.JsonUtils;
import org.assertj.core.api.Assertions;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

/**
 * @author Dilip Chauhan
 * Created on 30/03/2020
 */
@ExtendWith(MockitoExtension.class)
public class ProductControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    private String productString;

    private String productStringFail;

    private Product product;

    @BeforeEach
    public void setup() {
        mockMvc = standaloneSetup(productController).build();
        productString = CommonUtil.convertInputStreamToString(getClass().getResourceAsStream("/product.json"));
        product = JsonUtils.deserialize(productString, Product.class);
        productStringFail = CommonUtil.convertInputStreamToString(getClass().getResourceAsStream("/product_fail.json"));

    }

    @Test
    public void getProductByIdTest() throws Exception {
        //Given
        Product product = new Product();
        product.setProductId(UUID.randomUUID());
        product.setPrice(123.90);
        product.setName("Nike Shoe");
        product.setSupplierId(UUID.randomUUID());
        when(productService.getProductById(any())).thenReturn(product);
        //When
        MvcResult mvcResult = mockMvc.perform(get(AppConstant.BASE_URI + "/products/{productId}", UUID.randomUUID())
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", Matchers.is("Nike Shoe")))
                .andExpect(jsonPath("$.price", Matchers.is(123.90))).andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        Assertions.assertThat(mvcResult.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
        Assertions.assertThat(JsonUtils.deserialize(content, Product.class).getSupplierId()).isInstanceOf(UUID.class);
        verify(productService, times(1)).getProductById(any());
        verifyNoMoreInteractions(productService);
    }

    @Test
    public void shouldSaveOrUpdateProductPassTest() throws Exception {
        //Given
        when(productService.saveOrUpdateProduct(any())).thenReturn(new Product());
        //When
        MvcResult mvcResult = mockMvc.perform(post(AppConstant.BASE_URI + "/products/")
                .content(productString)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()).andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        Product order = JsonUtils.deserialize(content, Product.class);
        //Then
        Assertions.assertThat(order).isNotNull();
        Assertions.assertThat(mvcResult.getResponse().getStatus()).isEqualTo(HttpStatus.CREATED.value());
        verify(productService, times(1)).saveOrUpdateProduct(any());
    }

    @Test
    public void shouldThrowExceptionSaveOrUpdateProductTest() throws Exception {
        MvcResult mvcResult = mockMvc.perform(post(AppConstant.BASE_URI + "/products/")
                .content(productStringFail)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError()).andReturn();
        int status = mvcResult.getResponse().getStatus();
        Assertions.assertThat(status).isEqualTo(400);
    }

    @Test
    public void updateProductTest() throws Exception {
        //Given
        when(productService.saveOrUpdateProduct(any())).thenReturn(product);
        //When
        MvcResult mvcResult = mockMvc.perform(put(AppConstant.BASE_URI + "/products/")
                .content(productString)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        Product order = JsonUtils.deserialize(content, Product.class);
        //Then
        Assertions.assertThat(order).isNotNull();
        Assertions.assertThat(mvcResult.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
        verify(productService, times(1)).saveOrUpdateProduct(any());
        verifyNoMoreInteractions(productService);
    }

    @Test
    public void updateProductExceptionTest() throws Exception {
        MvcResult mvcResult = mockMvc.perform(put(AppConstant.BASE_URI + "/products/")
                .content(productStringFail)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError()).andReturn();
        int status = mvcResult.getResponse().getStatus();
        Assertions.assertThat(status).isEqualTo(400);
        verifyNoMoreInteractions(productService);
    }

    @Test
    public void deleteProductTest() throws Exception {
        //Given
        doNothing().when(productService).deleteProduct(any());
        //When
        MvcResult mvcResult = mockMvc.perform(delete(AppConstant.BASE_URI + "/products/{productId}", UUID.randomUUID())
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent()).andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        //Then
        Assertions.assertThat(response.getStatus()).isEqualTo(204);
        verify(productService, times(1)).deleteProduct(any());
        verifyNoMoreInteractions(productService);
    }
}
