package com.nineleaps.orderservice.service;

import com.nineleaps.orderservice.exception.ServiceException;
import com.nineleaps.orderservice.model.Order;
import com.nineleaps.orderservice.repository.OrderRepository;
import com.nineleaps.orderservice.service.impl.OrderServiceImpl;
import com.nineleaps.orderservice.utilities.CommonUtil;
import com.nineleaps.orderservice.utilities.JsonUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.util.concurrent.ListenableFuture;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * The type Order service test.
 *
 * @author Dilip Chauhan
 * Created on 22/03/2020
 */
@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private KafkaTemplate<String, Order> kafkaTemplate;

    @Mock
    @SuppressWarnings("rawtypes")
    private ListenableFuture listenableFuture;

    @InjectMocks
    private OrderServiceImpl orderService;

    private Order order;

    /**
     * Sets up.
     */
    @BeforeEach
    public void setUp() {
        orderService = new OrderServiceImpl(orderRepository, kafkaTemplate, "MyTopic");
        String string = CommonUtil.convertInputStreamToString(getClass().getResourceAsStream("/order.json"));
        order = JsonUtils.deserialize(string, Order.class);
    }

    /**
     * Persist order test.
     */
    @Test
    @SuppressWarnings("all")
    public void persistOrderTest() {
        //Given
        when(orderRepository.save(any())).thenReturn(order);
        when(kafkaTemplate.send(anyString(), any())).thenReturn(listenableFuture);
        doNothing().when(listenableFuture).addCallback(any());
        //When
        Order persistOrder = orderService.saveOrUpdateOrder(order);
        //Then
        Assertions.assertThat(persistOrder.getCustomerId()).isNotNull();
        verify(orderRepository, times(1)).save(any());
        verify(kafkaTemplate, times(1)).send(anyString(), any());
    }

    /**
     * Persist order throw exception test.
     */
    @Test
    public void persistOrderThrowExceptionTest() {
        //Given
        when(orderRepository.save(any())).thenReturn(ServiceException.class);
        //When
        ServiceException violationException = assertThrows(ServiceException.class, () -> {
            orderService.saveOrUpdateOrder(order);
        });
        //Then
        assertThat(violationException).hasMessage("Error while persisting or updating data");
        verify(orderRepository, times(1)).save(any());
    }
}
