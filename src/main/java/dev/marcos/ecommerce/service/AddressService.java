package dev.marcos.ecommerce.service;

import dev.marcos.ecommerce.entity.Address;
import dev.marcos.ecommerce.entity.User;
import dev.marcos.ecommerce.entity.enums.Role;
import dev.marcos.ecommerce.exception.ResourceNotFoundException;
import dev.marcos.ecommerce.mapper.AddressMapper;
import dev.marcos.ecommerce.mapper.UserMapper;
import dev.marcos.ecommerce.model.dto.address.AddressCreateRequest;
import dev.marcos.ecommerce.model.dto.user.UserDTO;
import dev.marcos.ecommerce.repository.AddressRepository;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AddressService {

    private final AddressRepository addressRepository;
    private final UserService userService;

    public AddressService(AddressRepository addressRepository, UserService userService) {
        this.addressRepository = addressRepository;
        this.userService = userService;
    }

    @Transactional
    public Address save(Long userId, UserDetails userDetails, AddressCreateRequest dto) {
        checkPermission((User) userDetails, userId);
        UserDTO userDTO = userService.findById(userId);
        User user = UserMapper.toEntity(userDTO);
        Address address = AddressMapper.toEntity(dto);
        address.setUser(user);
        return addressRepository.save(address);
    }

    public List<Address> getAddress(Long userId, UserDetails userDetails) {
        checkPermission((User) userDetails, userId);
        return addressRepository.findAllByUserId(userId);
    }

    public Address getById(Long addressId) {
        return getAddress(addressId);
    }

    @Transactional
    public void deleteById(Long addressId, UserDetails userDetails) {
        Address address = getAddress(addressId);
        checkPermission((User) userDetails, address.getUser().getId());
        addressRepository.delete(address);
    }

    private Address getAddress(Long addressId) {
        return addressRepository.findById(addressId)
                .orElseThrow(() -> new ResourceNotFoundException("Endereço não encontrado"));
    }

    private void checkPermission(User user, Long userId) {
        if (user.getRole() != Role.ADMIN && !user.getId().equals(userId)) {
            throw new AuthorizationDeniedException("Não é possível acessar este recurso");
        }
    }
}
