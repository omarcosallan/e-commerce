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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AddressService {

    private final AddressRepository repository;

    public AddressService(AddressRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Address save(UserDTO userDTO, AddressCreateRequest dto) {
        Address address = AddressMapper.toEntity(dto);
        User user = UserMapper.toEntity(userDTO);
        address.setUser(user);
        return repository.save(address);
    }

    public List<Address> getAddress(UserDTO user) {
        return repository.findAllByUserId(user.id());
    }

    public void deleteById(Long id, UserDTO user) {
        Address address = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Endereço não encontrado"));
        if (user.role() == Role.ADMIN || address.getUser().getId().equals(user.id())) {
            repository.delete(address);
        } else {
            throw new AuthorizationDeniedException("Não é possível acessar este recurso");
        }
    }
}
