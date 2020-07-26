package com.gauravrmsc.critterchronologer.pet;

import com.gauravrmsc.critterchronologer.repository.entity.CustomerEntity;
import com.gauravrmsc.critterchronologer.repository.entity.PetEntity;
import com.gauravrmsc.critterchronologer.service.PetService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {

  @Autowired
  PetService petService;


  @PostMapping
  public PetDTO savePet(@RequestBody PetDTO petDTO) {
    PetEntity petEntity = toPetEntity(petDTO);
    petEntity = petService.savePet(petEntity);
    return toPetDTO(petEntity);
  }

  @GetMapping("/{petId}")
  public PetDTO getPet(@PathVariable long petId) {

    PetEntity petEntity = petService.getPet(petId);
    return toPetDTO(petEntity);
  }

  @GetMapping
  public List<PetDTO> getPets() {
    List<PetEntity> petEntities = petService.getAllPets();
    return toPetDTOs(petEntities);
  }

  @GetMapping("/owner/{ownerId}")
  public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
    List<PetEntity> petEntities = petService.getPetsByOwner(ownerId);
    return toPetDTOs(petEntities);
  }

  PetDTO toPetDTO(PetEntity petEntity) {
    PetDTO petDTO = new PetDTO();
    BeanUtils.copyProperties(petEntity, petDTO);
    if (petEntity.getOwner() != null) {
      petDTO.setOwnerId(petEntity.getOwner().getId());
    }
    return petDTO;
  }

  PetEntity toPetEntity(PetDTO petDTO) {
    PetEntity petEntity = new PetEntity();
    BeanUtils.copyProperties(petDTO, petEntity);
    if (petDTO.getOwnerId() != null) {
      CustomerEntity owner = new CustomerEntity();
      owner.setId(petDTO.getOwnerId());
      petEntity.setOwner(owner);
    }
    return petEntity;
  }

  private List<PetDTO> toPetDTOs(List<PetEntity> petEntities) {
    List<PetDTO> petDTOs = new ArrayList<>();
    if (petEntities != null) {
      for (PetEntity petEntity : petEntities) {
        petDTOs.add(toPetDTO(petEntity));
      }
    }
    return petDTOs;
  }
}

