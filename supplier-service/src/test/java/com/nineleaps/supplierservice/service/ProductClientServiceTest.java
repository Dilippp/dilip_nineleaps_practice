package com.nineleaps.supplierservice.service;

import com.nineleaps.supplierservice.email.EmailService;
import com.nineleaps.supplierservice.model.Supplier;
import com.nineleaps.supplierservice.response.ProductResponse;
import com.nineleaps.supplierservice.service.impl.ProductClientServiceImpl;
import com.nineleaps.supplierservice.utilities.CommonUtil;
import com.nineleaps.supplierservice.utilities.JsonUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import static org.mockito.Mockito.*;

/**
 * @author Dilip Chauhan
 * Created on 30/03/2020
 */
@ExtendWith(MockitoExtension.class)
public class ProductClientServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private EmailService emailService;

    @Mock
    private SupplierService supplierService;

    @InjectMocks
    private ProductClientServiceImpl productClientService;

    private ProductResponse productResponse;

    private Supplier supplier;

    @BeforeEach
    public void setUp() {
        productClientService = new ProductClientServiceImpl(restTemplate, emailService, supplierService, "productUrl");
        productResponse = JsonUtils.deserialize(CommonUtil.convertInputStreamToString(getClass().getResourceAsStream("/product.json")), ProductResponse.class);
        supplier = JsonUtils.deserialize(CommonUtil.convertInputStreamToString(getClass().getResourceAsStream("/supplier.json")), Supplier.class);
    }

    @Test
    public void getProductFromProductServiceTest() {
        String orderJsonString = "{\"orderId\":\"e26f11fd-a3b9-4eb4-b8e8-3cb73da61f24\",\"customerId\":123,\"customerName\":\"Dilip Chauhan\",\"products\":[{\"productId\":\"9623556a-6d87-4db7-a8f7-01fe57c866df\",\"quantity\":34}],\"shippingAddress\":\"cisco\"}";
        when(restTemplate.getForObject(anyString(), any())).thenReturn(productResponse);
        when(supplierService.getSupplier(any())).thenReturn(supplier);
        doNothing().when(emailService).sendSimpleMessage(any());
        productClientService.getProduct(orderJsonString);
        verify(restTemplate, times(1)).getForObject(anyString(), any());
        verify(supplierService, times(1)).getSupplier(any());
        verify(emailService, times(1)).sendSimpleMessage(any());
    }
}