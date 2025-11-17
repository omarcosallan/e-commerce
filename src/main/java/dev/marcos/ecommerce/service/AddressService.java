package dev.marcos.ecommerce.service;

import dev.marcos.ecommerce.entity.Address;
import dev.marcos.ecommerce.entity.User;
import dev.marcos.ecommerce.exception.ResourceNotFoundException;
import dev.marcos.ecommerce.mapper.AddressMapper;
import dev.marcos.ecommerce.model.dto.address.AddressCreateRequest;
import dev.marcos.ecommerce.repository.AddressRepository;
import dev.marcos.ecommerce.utils.CheckPermission;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AddressService {

    private final AddressRepository addressRepository;

    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public List<Address> findAll(UserDetails userDetails) {
        User user = (User) userDetails;
        return addressRepository.findAllByUserId(user.getId());
    }

    public Address findById(Long addressId, UserDetails userDetails) {
        Address address = getAddress(addressId);
        CheckPermission.verify((User) userDetails, address.getUser().getId());
        return address;
    }

    @Transactional
    public Address save(UserDetails userDetails, AddressCreateRequest dto) {
        User user = (User) userDetails;
        Address address = AddressMapper.toEntity(dto);
        address.setUser(user);
        return addressRepository.save(address);
    }

    @Transactional
    public void deleteById(UserDetails userDetails, Long addressId) {
        Address address = getAddress(addressId);
        CheckPermission.verify((User) userDetails, address.getUser().getId());
        addressRepository.delete(address);
    }

    private Address getAddress(Long addressId) {
        return addressRepository.findById(addressId)
                .orElseThrow(() -> new ResourceNotFoundException("Endereço não encontrado"));
    }
}
