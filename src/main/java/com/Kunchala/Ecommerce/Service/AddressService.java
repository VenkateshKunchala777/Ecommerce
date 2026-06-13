package com.Kunchala.Ecommerce.Service;

import com.Kunchala.Ecommerce.Dto.AddressDto;
import com.Kunchala.Ecommerce.Entity.Address;
import com.Kunchala.Ecommerce.Exception.ResourceNotFoundException;
import com.Kunchala.Ecommerce.Repository.AddressRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AddressService {

    private final AddressRepository addressRepository;
    private final ModelMapper modelMapper;

    public AddressService(AddressRepository addressRepository, ModelMapper modelMapper) {
        this.addressRepository = addressRepository;
        this.modelMapper = modelMapper;
    }

    public AddressDto createAddress(AddressDto addressDto) {
        Address address = modelMapper.map(addressDto, Address.class);
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
