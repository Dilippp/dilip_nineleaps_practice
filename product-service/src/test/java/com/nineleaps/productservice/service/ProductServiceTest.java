package com.nineleaps.productservice.service;

import com.nineleaps.productservice.exception.ResourceNotFoundException;
import com.nineleaps.productservice.exception.ServiceException;
import com.nineleaps.productservice.model.Product;
import com.nineleaps.productservice.repository.ProductRepository;
import com.nineleaps.productservice.service.impl.ProductServiceImpl;
import com.nineleaps.productservice.utilities.CommonUtil;
import com.nineleaps.productservice.utilities.JsonUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

/**
 * @author Dilip Chauhan
 * Created on 30/03/2020
 */
@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    private Product product;

    @BeforeEach
    public void setUp() {
        String string = CommonUtil.convertInputStreamToString(getClass().getResourceAsStream("/product.json"));
        product = JsonUtils.deserialize(string, Product.class);
    }

    @Test
    public void getProductByIdTest() {
        //Given
        when(productRepository.findById(any())).thenReturn(Optional.of(product));
        //When
        Product product = productService.getProductById(any());
        //Then
        assertThat(product).isNotNull();
        assertThat(product.getProductId()).isInstanceOf(UUID.class);
        verify(productRepository, times(1)).findById(any());
    }

    @Test
    public void getProductByIdThrowExceptionTest() {
        //Given
        when(productRepository.findById(any())).thenReturn(Optional.empty());
        //When
        ResourceNotFoundException notFoundException = assertThrows(ResourceNotFoundException.class, () -> productService.getProductById(any()));
        //Then
        assertThat(notFoundException).hasMessage("No product found by id: null");
        verify(productRepository, times(1)).findById(any());
    }

    @Test
    public void saveOrUpdateProductTest() {
        //Given
        when(productRepository.save(any())).thenReturn(product);
        //When
        Product product = productService.saveOrUpdateProduct(any());
        //Then
        assertThat(product).isNotNull();
        assertThat(product.getProductId()).isInstanceOf(UUID.class);
        verify(productRepository, times(1)).save(any());
    }

    @Test
    public void saveOrUpdateProductThrowExceptionTest() {
        //Given
        when(productRepository.save(any())).thenThrow(ServiceException.class);
        //When
        ServiceException violationException = assertThrows(ServiceException.class, () -> productService.saveOrUpdateProduct(any()));
        //Then
        assertThat(violationException).hasMessage("Error while persisting or updating data");
        verify(productRepository, times(1)).save(any());
    }

    @Test
    public void deleteProductTest() {
        //Given
        when(productRepository.findById(any())).thenReturn(Optional.of(product));
        doNothing().when(productRepository).deleteById(any());
        //When
        productService.deleteProduct(any());
        //Then
        verify(productRepository, times(1)).findById(any());
        verify(productRepository, times(1)).deleteById(any());
    }

    @Test
    public void deleteProductThrowExceptionTest() {
        //Given
        when(productRepository.findById(any())).thenReturn(Optional.empty());
        //When
        ResourceNotFoundException notFoundException = assertThrows(ResourceNotFoundException.class, () -> productService.deleteProduct(any()));
        assertThat(notFoundException).hasMessage("No record found to delete for product id: " + "null");
    }
}
