package com.Kunchala.Ecommerce.Service;

import com.Kunchala.Ecommerce.Dto.CustomerDto;
import com.Kunchala.Ecommerce.Entity.Customer;
import com.Kunchala.Ecommerce.Exception.ResourceNotFoundException;
import com.Kunchala.Ecommerce.Repository.CustomerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;

    public CustomerService(CustomerRepository customerRepository, ModelMapper modelMapper) {
        this.customerRepository = customerRepository;
        this.modelMapper = modelMapper;
    }

    public CustomerDto createCustomer(CustomerDto customerDto) {
        Customer customer = modelMapper.map(customerDto, Customer.class);
        Customer savedCustomer = customerRepository.save(customer);
        return modelMapper.map(savedCustomer, CustomerDto.class);
    }

    public CustomerDto getCustomerById(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + id));
        return modelMapper.map(customer, CustomerDto.class);
    }

    public List<CustomerDto> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        return customers.stream()
                .map(customer -> modelMapper.map(customer, CustomerDto.class))
                .collect(Collectors.toList());
    }

    public CustomerDto updateCustomer(Long id, CustomerDto customerDto) {
        Customer existingCustomer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + id));

        customerDto.setId(id);
        modelMapper.map(customerDto, existingCustomer);
        Customer updatedCustomer = customerRepository.save(existingCustomer);
        return modelMapper.map(updatedCustomer, CustomerDto.class);
    }

    public void deleteCustomer(Long id) {
        if (!customerRepository.existsById(id)) {
            throw new ResourceNotFoundException("Customer not found with id: " + id);
        }
        customerRepository.deleteById(id);
    }
}
