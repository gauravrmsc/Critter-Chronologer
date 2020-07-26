package com.gauravrmsc.critterchronologer.service;

import com.gauravrmsc.critterchronologer.repository.entity.CustomerEntity;
import com.gauravrmsc.critterchronologer.repository.entity.PetEntity;
import com.gauravrmsc.critterchronologer.repository.repository.PetRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class PetService {

  @Autowired
  PetRepository petRepository;

  @Autowired
  CustomerService customerService;


  public PetEntity savePet(PetEntity petEntity) {
    return petRepository.save(petEntity);
  }

  public Optional<PetEntity> getPetEntity(Long petId) {
    Optional<PetEntity> petEntity = petRepository.findById(petId);
    return petEntity;
  }


  public PetEntity getPet(Long petId) {
    Optional<PetEntity> petEntity = getPetEntity(petId);
    if (petEntity.isPresent()) {
      return petEntity.get();
    } else {
      return new PetEntity();
    }
  }

  public List<PetEntity> getPetsByOwner(Long ownerId) {
    Optional<CustomerEntity> customerEntity = customerService.getCustomerForId(ownerId);
    List<PetEntity> petEntities = new ArrayList<>();
    if (customerEntity.isPresent()) {
      CustomerEntity owner = customerEntity.get();
      petEntities = petRepository.findAllByOwner(owner);
    }
    return petEntities;
  }

  public List<PetEntity> getAllPets() {
    return petRepository.findAll();
  }

}
