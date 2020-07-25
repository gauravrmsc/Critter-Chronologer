package com.gauravrmsc.critterchronologer.pet;

import com.gauravrmsc.critterchronologer.service.PetService;
import java.util.List;
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
    return petService.savePet(petDTO);
  }

  @GetMapping("/{petId}")
  public PetDTO getPet(@PathVariable long petId) {
    return petService.getPet(petId);
  }

  @GetMapping
  public List<PetDTO> getPets() {
    throw new UnsupportedOperationException();
  }

  @GetMapping("/owner/{ownerId}")
  public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
    return petService.getPetsByOwner(ownerId);
  }
}
