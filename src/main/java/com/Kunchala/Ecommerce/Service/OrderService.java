package com.Kunchala.Ecommerce.Service;

import com.Kunchala.Ecommerce.Dto.OrderDto;
import com.Kunchala.Ecommerce.Entity.Customer;
import com.Kunchala.Ecommerce.Entity.Order;
import com.Kunchala.Ecommerce.Exception.ResourceNotFoundException;
import com.Kunchala.Ecommerce.Repository.CustomerRepository;
import com.Kunchala.Ecommerce.Repository.OrderRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;
    private final CustomerRepository customerRepository;

    public OrderService(OrderRepository orderRepository, ModelMapper modelMapper,CustomerRepository customerRepository) {
        this.orderRepository = orderRepository;
        this.modelMapper = modelMapper;
        this.customerRepository=customerRepository;
    }

    public OrderDto createOrder(OrderDto orderDto,Long customer_id) {
        Order order = modelMapper.map(orderDto, Order.class);
        boolean isCustomerExisted=customerRepository.existsById(customer_id);
        if(!isCustomerExisted) throw new ResourceNotFoundException("Customer with the id "+ customer_id+ " is not found to attach to order");
        Customer customer=customerRepository.findById(customer_id).get();
        order.setCustomer(customer);
        Order savedOrder = orderRepository.save(order);
        return modelMapper.map(savedOrder, OrderDto.class);
    }

    public OrderDto getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
        return modelMapper.map(order, OrderDto.class);
    }

    public List<OrderDto> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream()
                .map(order -> modelMapper.map(order, OrderDto.class))
                .collect(Collectors.toList());
    }

    public OrderDto updateOrder(Long id, OrderDto orderDto) {
        Order existingOrder = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));

        orderDto.setId(id);
        modelMapper.map(orderDto, existingOrder);
        Order updatedOrder = orderRepository.save(existingOrder);
        return modelMapper.map(updatedOrder, OrderDto.class);
    }

    public void deleteOrder(Long id) {
        if (!orderRepository.existsById(id)) {
            throw new ResourceNotFoundException("Order not found with id: " + id);
        }
        orderRepository.deleteById(id);
    }
}
