package com.Kunchala.Ecommerce.Controller;

import com.Kunchala.Ecommerce.Dto.AddressDto;
import com.Kunchala.Ecommerce.Service.AddressService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/addresses")
public class AddressController {

    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @PostMapping("/createAddress/{customer_id}")
    public ResponseEntity<AddressDto> createAddress(@RequestBody @Validated AddressDto addressDto, @PathVariable  Long customer_id) {
        AddressDto createdAddress = addressService.createAddress(addressDto,customer_id);
        return new ResponseEntity<>(createdAddress, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AddressDto> getAddressById(@PathVariable Long id) {
        AddressDto address = addressService.getAddressById(id);
        return ResponseEntity.ok(address);
    }

    @GetMapping
    public ResponseEntity<List<AddressDto>> getAllAddresses() {
        List<AddressDto> addresses = addressService.getAllAddresses();
        return ResponseEntity.ok(addresses);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AddressDto> updateAddress(@PathVariable Long id, @RequestBody @Validated AddressDto addressDto) {
        AddressDto updatedAddress = addressService.updateAddress(id, addressDto);
        return ResponseEntity.ok(updatedAddress);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAddress(@PathVariable Long id) {
        addressService.deleteAddress(id);
        return ResponseEntity.noContent().build();
    }
}
