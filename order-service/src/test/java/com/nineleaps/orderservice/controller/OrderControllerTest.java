package com.nineleaps.orderservice.controller;

import com.nineleaps.orderservice.model.Order;
import com.nineleaps.orderservice.service.OrderService;
import com.nineleaps.orderservice.utilities.AppConstants;
import com.nineleaps.orderservice.utilities.CommonUtil;
import com.nineleaps.orderservice.utilities.JsonUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

/**
 * The type Order controller test.
 *
 * @author Dilip Chauhan
 * Created on 22/03/2020
 */
@ExtendWith(MockitoExtension.class)
public class OrderControllerTest {

    private MockMvc mockMvc;

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;

    /**
     * A method, which gets executed before each test case.
     */
    @BeforeEach
    public void setUp() {
        mockMvc = standaloneSetup(orderController).build();
    }

    /**
     * Should place order successful test.
     *
     * @throws Exception the exception
     */
    @Test
    public void shouldPlaceOrderSuccessfulTest() throws Exception {
        //Given
        when(orderService.saveOrUpdateOrder(any())).thenReturn(new Order());
        //When
        MvcResult mvcResult = mockMvc.perform(post(AppConstants.BASE_URI + "/orders")
                .content(CommonUtil.convertInputStreamToString(getClass().getResourceAsStream("/order.json")))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()).andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        Order order = JsonUtils.deserialize(content, Order.class);
        //Then
        Assertions.assertThat(order).isNotNull();
        Assertions.assertThat(mvcResult.getResponse().getStatus()).isEqualTo(HttpStatus.CREATED.value());
        verify(orderService, times(1)).saveOrUpdateOrder(any());
    }

    /**
     * Should throw exception while placing order test.
     *
     * @throws Exception the exception
     */
    @Test
    public void shouldThrowExceptionPlaceOrderTest() throws Exception {
        //When
        MvcResult mvcResult = mockMvc.perform(post(AppConstants.BASE_URI + "/orders")
                .content(CommonUtil.convertInputStreamToString(getClass().getResourceAsStream("/order_exception.json")))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError()).andReturn();
        int status = mvcResult.getResponse().getStatus();
        //Then
        Assertions.assertThat(status).isEqualTo(400);
    }
}
