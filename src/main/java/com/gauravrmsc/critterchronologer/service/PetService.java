package com.gauravrmsc.critterchronologer.service;

import com.gauravrmsc.critterchronologer.pet.PetDTO;
import com.gauravrmsc.critterchronologer.repository.entity.CustomerEntity;
import com.gauravrmsc.critterchronologer.repository.entity.PetEntity;
import com.gauravrmsc.critterchronologer.repository.repository.CustomerRepository;
import com.gauravrmsc.critterchronologer.repository.repository.PetRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class PetService {

  @Autowired
  PetRepository petRepository;
  @Autowired
  CustomerRepository customerRepository;

  public PetDTO savePet(PetDTO petDTO) {
    petDTO.setId(null);
    PetEntity petEntity = new PetEntity();
    BeanUtils.copyProperties(petDTO, petEntity);
    if (petDTO.getOwnerId() != null) {
      Optional<CustomerEntity> customerEntity = customerRepository.findById(petDTO.getOwnerId());
      if (customerEntity.isPresent()) {
        petEntity.setOwner(customerEntity.get());
      }
    }
    petRepository.save(petEntity);
    PetDTO savedPetDTO = new PetDTO();
    BeanUtils.copyProperties(petEntity, savedPetDTO);
    return savedPetDTO;
  }

  public Optional<PetEntity> getPetEntity(Long petId) {
    Optional<PetEntity> petEntity = petRepository.findById(petId);
    return petEntity;
  }

  public List<PetEntity> getPetEntities(List<Long> petIds) {
    List<PetEntity> petEntities = new ArrayList<>();
    for (Long petId : petIds) {
      Optional<PetEntity> petEntity = getPetEntity(petId);
      if (petEntity.isPresent()) {
        petEntities.add(petEntity.get());
      }
    }
    return petEntities;
  }

  public PetDTO getPet(Long petId) {
    Optional<PetEntity> petEntity = getPetEntity(petId);
    PetDTO petDTO = new PetDTO();
    if (petEntity.isPresent()) {
      BeanUtils.copyProperties(petEntity.get(), petDTO);
      if (petEntity.get().getOwner() != null) {
        petDTO.setOwnerId(petEntity.get().getOwner().getId());
      }
    }
    return petDTO;
  }

  public List<PetDTO> getPetsByOwner(Long ownerId) {
    Optional<CustomerEntity> customerEntity = customerRepository.findById(ownerId);
    List<PetDTO> petDTOS = new ArrayList<>();
    if (customerEntity.isPresent()) {
      CustomerEntity owner = customerEntity.get();
      List<PetEntity> petEntities = petRepository.findAllByOwner(owner);
      for (PetEntity petEntity : petEntities) {
        PetDTO petDTO = new PetDTO();
        BeanUtils.copyProperties(petEntity, petDTO);
        petDTO.setOwnerId(petEntity.getOwner().getId());
        petDTOS.add(petDTO);
      }
    }
    return petDTOS;
  }

  public List<Long> mapPetToPetId(List<PetEntity> PetEntities) {
    List<Long> PetIds = new ArrayList<>();
    for (PetEntity PetEntity : PetEntities) {
      PetIds.add(PetEntity.getId());
    }
    return PetIds;
  }
}
