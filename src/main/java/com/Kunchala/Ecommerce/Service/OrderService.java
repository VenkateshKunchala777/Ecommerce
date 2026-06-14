package com.Kunchala.Ecommerce.Service;

import com.Kunchala.Ecommerce.Dto.OrderDto;
import com.Kunchala.Ecommerce.Dto.OrderItemDto;
import com.Kunchala.Ecommerce.Entity.Customer;
import com.Kunchala.Ecommerce.Entity.Order;
import com.Kunchala.Ecommerce.Entity.OrderItem;
import com.Kunchala.Ecommerce.Entity.Product;
import com.Kunchala.Ecommerce.Exception.ResourceNotFoundException;
import com.Kunchala.Ecommerce.Repository.CustomerRepository;
import com.Kunchala.Ecommerce.Repository.OrderRepository;
import com.Kunchala.Ecommerce.Repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

    public OrderService(OrderRepository orderRepository, ModelMapper modelMapper, CustomerRepository customerRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.modelMapper = modelMapper;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
    }

    public OrderDto createOrder(OrderDto orderDto, Long customer_id) {
        Order order = new Order();
        order.setOrderDate(orderDto.getOrderDate());
        order.setStatus(orderDto.getStatus());

        boolean isCustomerExisted = customerRepository.existsById(customer_id);
        if (!isCustomerExisted) throw new ResourceNotFoundException("Customer with the id " + customer_id + " is not found to attach to order");
        Customer customer = customerRepository.findById(customer_id).get();
        order.setCustomer(customer);

        List<OrderItem> orderItems = new ArrayList<>();
        double totalAmount = 0.0;

        if (orderDto.getOrderItems() != null) {
            for (OrderItemDto itemDto : orderDto.getOrderItems()) {
                Product product = productRepository.findById(itemDto.getProductId())
                        .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + itemDto.getProductId()));

                OrderItem orderItem = new OrderItem();
                orderItem.setQuantity(itemDto.getQuantity());
                orderItem.setPriceAtPurchase(product.getPrice());
                orderItem.setProduct(product);
                orderItem.setOrder(order);
                orderItems.add(orderItem);

                totalAmount += product.getPrice() * itemDto.getQuantity();
            }
        }

        order.setOrderItems(orderItems);
        order.setTotalAmount(totalAmount);

        Order savedOrder = orderRepository.save(order);
        return mapToDto(savedOrder);
    }

    private OrderDto mapToDto(Order order) {
        OrderDto dto = new OrderDto();
        dto.setId(order.getId());
        dto.setOrderDate(order.getOrderDate());
        dto.setStatus(order.getStatus());
        dto.setTotalAmount(order.getTotalAmount());

        if (order.getOrderItems() != null) {
            List<OrderItemDto> itemDtos = order.getOrderItems().stream()
                    .map(item -> OrderItemDto.builder()
                            .id(item.getId())
                            .quantity(item.getQuantity())
                            .priceAtPurchase(item.getPriceAtPurchase())
                            .productId(item.getProduct().getId())
                            .productName(item.getProduct().getName())
                            .build())
                    .collect(Collectors.toList());
            dto.setOrderItems(itemDtos);
        }

        return dto;
    }

    public OrderDto getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
        return mapToDto(order);
    }

    public List<OrderDto> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream()
                .map(this::mapToDto)
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
