package dev.marcos.ecommerce.controller;

import dev.marcos.ecommerce.entity.Address;
import dev.marcos.ecommerce.model.dto.address.AddressCreateRequest;
import dev.marcos.ecommerce.model.dto.address.AddressUpdateRequest;
import dev.marcos.ecommerce.service.AddressService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/address")
public class AddressController {

    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping
    public ResponseEntity<List<Address>> getAll(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(addressService.findAll(userDetails));
    }

    @GetMapping("/{addressId}")
    public ResponseEntity<Address> getById(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long addressId) {
        return ResponseEntity.ok(addressService.findById(addressId, userDetails));
    }

    @PostMapping
    public ResponseEntity<Address> create(@AuthenticationPrincipal UserDetails userDetails, @Valid @RequestBody AddressCreateRequest dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(addressService.save(userDetails, dto));
    }

    @PutMapping("/{addressId}")
    public ResponseEntity<Address> update(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long addressId, @Valid @RequestBody AddressUpdateRequest dto) {
        return ResponseEntity.ok(addressService.update(userDetails, addressId, dto));
    }

    @DeleteMapping("/{addressId}")
    public ResponseEntity<Void> delete(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long addressId) {
        addressService.deleteById(userDetails, addressId);
        return ResponseEntity.noContent().build();
    }
}
