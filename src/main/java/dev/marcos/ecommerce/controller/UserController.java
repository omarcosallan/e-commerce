package dev.marcos.ecommerce.controller;

import dev.marcos.ecommerce.entity.Address;
import dev.marcos.ecommerce.entity.User;
import dev.marcos.ecommerce.mapper.UserMapper;
import dev.marcos.ecommerce.model.dto.UserCreateRequest;
import dev.marcos.ecommerce.model.dto.UserUpdateRequest;
import dev.marcos.ecommerce.model.dto.address.AddressCreateRequest;
import dev.marcos.ecommerce.model.dto.user.UserDTO;
import dev.marcos.ecommerce.model.PaginatedResponse;
import dev.marcos.ecommerce.model.dto.user.UserWithAddressesDTO;
import dev.marcos.ecommerce.service.AddressService;
import dev.marcos.ecommerce.service.UserService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final AddressService addressService;

    public UserController(UserService userService, AddressService addressService) {
        this.userService = userService;
        this.addressService = addressService;
    }

    @GetMapping
    public ResponseEntity<PaginatedResponse<UserDTO>> getUsers(@RequestParam(required = false, defaultValue = "0") int page,
                                                               @RequestParam(required = false, defaultValue = "10") int size,
                                                               @RequestParam(required = false, defaultValue = "ASC") Sort.Direction direction,
                                                               @RequestParam(required = false, defaultValue = "lastModifiedDate") String sortField) {
        return ResponseEntity.ok().body(userService.findAll(page, size, direction, sortField));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable long id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserCreateRequest dto) {
        UserDTO user = userService.save(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(user.id()).toUri();
        return ResponseEntity.created(uri).body(user);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @Valid @RequestBody UserUpdateRequest dto) {
        return ResponseEntity.ok(userService.updateUser(id, dto));
    }

    @GetMapping("/current/address")
    public ResponseEntity<UserWithAddressesDTO> getAddress(@AuthenticationPrincipal UserDetails userDetails) {
        UserDTO user = UserMapper.toDTO((User) userDetails);
        return ResponseEntity.ok(new UserWithAddressesDTO(user, addressService.getAddress(user)));
    }

    @PostMapping("/current/address")
    public ResponseEntity<Address> createAddress(@AuthenticationPrincipal UserDetails userDetails, @Valid @RequestBody AddressCreateRequest dto) {
        UserDTO user = UserMapper.toDTO((User) userDetails);
        return ResponseEntity.status(HttpStatus.CREATED).body(addressService.save(user, dto));
    }

    @GetMapping("/{id}/address")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserWithAddressesDTO> getAddress(@PathVariable Long id) {
        UserDTO user = userService.findById(id);
        return ResponseEntity.ok(new UserWithAddressesDTO(user, addressService.getAddress(user)));
    }

    @PostMapping("/{id}/address")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Address> createAddress(@PathVariable Long id, @Valid @RequestBody AddressCreateRequest dto) {
        UserDTO user = userService.findById(id);
        return ResponseEntity.status(HttpStatus.CREATED).body(addressService.save(user, dto));
    }

    @DeleteMapping("/address/{id}")
    public ResponseEntity<Void> deleteAddress(@PathVariable Long id,@AuthenticationPrincipal UserDetails userDetails) {
        UserDTO user = UserMapper.toDTO((User) userDetails);
        addressService.deleteById(id, user);
        return ResponseEntity.noContent().build();
    }
}