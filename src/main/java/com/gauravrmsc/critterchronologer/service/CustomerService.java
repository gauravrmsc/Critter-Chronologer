package com.gauravrmsc.critterchronologer.service;

import com.gauravrmsc.critterchronologer.repository.entity.CustomerEntity;
import com.gauravrmsc.critterchronologer.repository.entity.PetEntity;
import com.gauravrmsc.critterchronologer.repository.repository.CustomerRepository;
import com.gauravrmsc.critterchronologer.repository.repository.PetRepository;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class CustomerService {

  @Autowired
  CustomerRepository customerRepository;

  @Autowired
  PetRepository petRepository;

  public CustomerEntity addCustomer(CustomerEntity customerEntity) {
    return customerRepository.save(customerEntity);
  }

  public List<CustomerEntity> getAllCustomers() {
    return customerRepository.findAll();
  }

  public CustomerEntity findOwnerByPet(Long petId) {
    Optional<PetEntity> pet = petRepository.findById(petId);
    CustomerEntity ownerEntity = new CustomerEntity();
    if (pet.isPresent()) {
      ownerEntity = pet.get().getOwner();
    }
    return ownerEntity;
  }

  public Optional<CustomerEntity> getCustomerForId(Long id) {
    return customerRepository.findById(id);
  }

}
