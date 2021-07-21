package com.nineleaps.orderservice.controller;

import com.nineleaps.orderservice.model.Order;
import com.nineleaps.orderservice.service.OrderService;
import com.nineleaps.orderservice.utilities.AppConstants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;

import static com.nineleaps.orderservice.utilities.AppConstants.BASE_URI;

/**
 * The Order resource handler to accept order's activities.
 *
 * @author Dilip Chauhan
 * Created on 22/03/2020
 */
@RestController
@RequestMapping(value = BASE_URI + "/orders")
@Api(tags = {"Order Resource"})
@Slf4j
public class OrderController {

    private final OrderService orderService;

    /**
     * Instantiates a new Order controller.
     *
     * @param orderService the order service
     */
    @Autowired
    public OrderController(final OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * Gets order by id.
     *
     * @param orderId the order id
     * @return the order by id
     */
    @GetMapping(value = "/{orderId}")
    @ApiOperation(value = "Get order from the system")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = AppConstants.SUCCESS),
            @ApiResponse(code = 401, message = AppConstants.UNAUTHORIZED_ACCESS),
            @ApiResponse(code = 403, message = AppConstants.ACCESS_FORBIDDEN),
            @ApiResponse(code = 404, message = AppConstants.RESOURCE_NOT_FOUND),
            @ApiResponse(code = 500, message = AppConstants.INTERNAL_SERVER_ERROR)
    })
    @ResponseStatus(code = HttpStatus.OK)
    public Order getOrderById(@ApiParam(name = "orderId", value = "The id of the order to be view order",
            required = true) @PathVariable("orderId") final UUID orderId) {
        log.info("Request to fetch order with id: " + orderId);
        return orderService.getOrderById(orderId);
    }

    /**
     * Gets order by customer id
     *
     * @param customerId the customer Id
     * @return the order by customerId
     */
    @GetMapping(value = "/customer/{customerId}")
    @ApiOperation(value = "Get order from the system")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = AppConstants.SUCCESS),
            @ApiResponse(code = 401, message = AppConstants.UNAUTHORIZED_ACCESS),
            @ApiResponse(code = 403, message = AppConstants.ACCESS_FORBIDDEN),
            @ApiResponse(code = 404, message = AppConstants.RESOURCE_NOT_FOUND),
            @ApiResponse(code = 500, message = AppConstants.INTERNAL_SERVER_ERROR)
    })
    @ResponseStatus(code = HttpStatus.OK)
    public Order getOrderByCustomerId(@ApiParam(name = "customerId", value = "The id of the customer to be view order",
            required = true) @PathVariable("customerId") final Integer customerId) {
        log.info("Request to fetch order with id: " + customerId);
        return orderService.getOrderByCustomerId(customerId);
    }

    /**
     * A method to place order in the system.
     *
     * @param order the order
     * @return the order
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Use this API to place order")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = AppConstants.CREATED_SUCCESSFULLY),
            @ApiResponse(code = 401, message = AppConstants.UNAUTHORIZED_ACCESS),
            @ApiResponse(code = 403, message = AppConstants.ACCESS_FORBIDDEN),
            @ApiResponse(code = 400, message = AppConstants.BAD_REQUEST),
            @ApiResponse(code = 422, message = AppConstants.UN_PROCESSABLE_ENTITY),
            @ApiResponse(code = 500, message = AppConstants.INTERNAL_SERVER_ERROR)
    })
    @ResponseStatus(code = HttpStatus.CREATED)
    public Order placeOrder(@ApiParam(name = "order", value = "Add order",
            required = true) @Valid @RequestBody final Order order) {
        log.info("Request for placing order");
        return orderService.saveOrUpdateOrder(order);
    }

    /**
     * Update order order.
     *
     * @param order the order
     * @return the order
     */
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Update order record")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = AppConstants.SUCCESS),
            @ApiResponse(code = 401, message = AppConstants.UNAUTHORIZED_ACCESS),
            @ApiResponse(code = 403, message = AppConstants.ACCESS_FORBIDDEN),
            @ApiResponse(code = 400, message = AppConstants.BAD_REQUEST),
            @ApiResponse(code = 404, message = AppConstants.RESOURCE_NOT_FOUND),
            @ApiResponse(code = 422, message = AppConstants.UN_PROCESSABLE_ENTITY),
            @ApiResponse(code = 500, message = AppConstants.INTERNAL_SERVER_ERROR)
    })
    @ResponseStatus(code = HttpStatus.OK)
    public Order updateOrder(@ApiParam(name = "order", value = "Update order",
            required = true) @Valid @RequestBody final Order order) {
        log.info("Request to update order");
        return orderService.saveOrUpdateOrder(order);
    }

    /**
     * Delete order.
     *
     * @param orderId the order id
     */
    @DeleteMapping(value = "/{orderId}")
    @ApiOperation(value = "Delete order from the system")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = AppConstants.DELETED_SUCCESSFULLY),
            @ApiResponse(code = 401, message = AppConstants.UNAUTHORIZED_ACCESS),
            @ApiResponse(code = 403, message = AppConstants.ACCESS_FORBIDDEN),
            @ApiResponse(code = 404, message = AppConstants.RESOURCE_NOT_FOUND),
            @ApiResponse(code = 500, message = AppConstants.INTERNAL_SERVER_ERROR)
    })
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteOrder(@ApiParam(name = "orderId", value = "Delete order from system",
            required = true) @PathVariable("orderId") final UUID orderId) {
        log.info("Request to delete order with id: " + orderId);
        orderService.deleteOrder(orderId);
    }
}