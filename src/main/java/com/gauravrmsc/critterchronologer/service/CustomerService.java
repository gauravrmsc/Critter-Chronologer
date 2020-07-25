package com.gauravrmsc.critterchronologer.service;

import com.gauravrmsc.critterchronologer.repository.entity.CustomerEntity;
import com.gauravrmsc.critterchronologer.repository.entity.PetEntity;
import com.gauravrmsc.critterchronologer.repository.repository.CustomerRepository;
import com.gauravrmsc.critterchronologer.repository.repository.PetRepository;
import com.gauravrmsc.critterchronologer.user.CustomerDTO;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class CustomerService {

  @Autowired
  CustomerRepository customerRepository;

  @Autowired
  PetRepository petRepository;

  public CustomerDTO addCustomer(CustomerDTO customer) {
    CustomerEntity customerEntity = new CustomerEntity();
    BeanUtils.copyProperties(customer, customerEntity);
    customerEntity.setPets(null);
    customerRepository.save(customerEntity);
    CustomerDTO savedCustomer = new CustomerDTO();
    BeanUtils.copyProperties(customerEntity, savedCustomer);
    return savedCustomer;
  }

  public List<CustomerDTO> getAllCustomers() {
    List<CustomerEntity> customerEntities = customerRepository.findAll();
    List<CustomerDTO> customerDTOS = new ArrayList<>();
    for (CustomerEntity customerEntity : customerEntities) {
      CustomerDTO customerDTO = new CustomerDTO();
      BeanUtils.copyProperties(customerEntity, customerDTO);
      List<Long> petIds = mapPetToPetId(customerEntity.getPets());
      customerDTO.setPetIds(petIds);
      customerDTOS.add(customerDTO);
    }
    return customerDTOS;
  }

  public CustomerDTO findOwnerByPet(Long petId) {
    Optional<PetEntity> pet = petRepository.findById(petId);
    CustomerDTO ownerDTO = new CustomerDTO();
    if(pet.isPresent()) {
      CustomerEntity ownerEntity = pet.get().getOwner();
      BeanUtils.copyProperties(ownerEntity, ownerDTO);
      List<PetEntity> pets = ownerEntity.getPets();
      List<Long> petIds = mapPetToPetId(pets);
      ownerDTO.setPetIds(petIds);
    }
    return ownerDTO;
  }
  private List<Long> mapPetToPetId (List<PetEntity> pets) {
    List<Long> petIds = new ArrayList<>();
    if (pets == null) {
      return petIds;
    }
    for (PetEntity pet : pets) {
      petIds.add(pet.getId());
    }
    return petIds;
  }

}
