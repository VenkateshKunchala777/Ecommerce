package com.Kunchala.Ecommerce.Service;

import com.Kunchala.Ecommerce.Dto.AddressDto;
import com.Kunchala.Ecommerce.Entity.Address;
import com.Kunchala.Ecommerce.Entity.Customer;
import com.Kunchala.Ecommerce.Exception.ResourceNotFoundException;
import com.Kunchala.Ecommerce.Repository.AddressRepository;
import com.Kunchala.Ecommerce.Repository.CustomerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AddressService {

    private final AddressRepository addressRepository;
    private final ModelMapper modelMapper;
    private final CustomerRepository customerRepository;

    public AddressService(AddressRepository addressRepository, ModelMapper modelMapper,CustomerRepository customerRepository) {
        this.addressRepository = addressRepository;
        this.modelMapper = modelMapper;
        this.customerRepository=customerRepository;
    }

    public AddressDto createAddress(AddressDto addressDto,Long customer_id) {
        Address address = modelMapper.map(addressDto, Address.class);
        boolean isCustomerExisted=customerRepository.existsById(customer_id);
        if(!isCustomerExisted) throw new ResourceNotFoundException("Customer is not found with the id "+customer_id+" to attach to address");
        Customer customer=customerRepository.findById(customer_id).get();
        address.setCustomer(customer);
        Address savedAddress = addressRepository.save(address);
        return modelMapper.map(savedAddress, AddressDto.class);
    }

    public AddressDto getAddressById(Long id) {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Address not found with id: " + id));
        return modelMapper.map(address, AddressDto.class);
    }

    public List<AddressDto> getAllAddresses() {
        List<Address> addresses = addressRepository.findAll();
        return addresses.stream()
                .map(address -> modelMapper.map(address, AddressDto.class))
                .collect(Collectors.toList());
    }

    public AddressDto updateAddress(Long id, AddressDto addressDto) {
        Address existingAddress = addressRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Address not found with id: " + id));

        addressDto.setId(id);
        modelMapper.map(addressDto, existingAddress);
        Address updatedAddress = addressRepository.save(existingAddress);
        return modelMapper.map(updatedAddress, AddressDto.class);
    }

    public void deleteAddress(Long id) {
        if (!addressRepository.existsById(id)) {
            throw new ResourceNotFoundException("Address not found with id: " + id);
        }
        addressRepository.deleteById(id);
    }
}
