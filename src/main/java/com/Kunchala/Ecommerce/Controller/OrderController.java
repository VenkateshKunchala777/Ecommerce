package com.Kunchala.Ecommerce.Controller;

import com.Kunchala.Ecommerce.Dto.OrderDto;
import com.Kunchala.Ecommerce.Service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/addOrder/{customer_id}")
    public ResponseEntity<OrderDto> createOrder(@RequestBody @Validated OrderDto orderDto,@PathVariable Long customer_id) {
        OrderDto createdOrder = orderService.createOrder(orderDto,customer_id);
        return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable Long id) {
        OrderDto order = orderService.getOrderById(id);
        return ResponseEntity.ok(order);
    }

    @GetMapping
    public ResponseEntity<List<OrderDto>> getAllOrders() {
        List<OrderDto> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderDto> updateOrder(@PathVariable Long id, @RequestBody @Validated OrderDto orderDto) {
        OrderDto updatedOrder = orderService.updateOrder(id, orderDto);
        return ResponseEntity.ok(updatedOrder);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<OrderDto> patchOrder(@PathVariable Long id, @RequestBody OrderDto orderDto) {
        OrderDto patchedOrder = orderService.patchOrder(id, orderDto);
        return ResponseEntity.ok(patchedOrder);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }
}
