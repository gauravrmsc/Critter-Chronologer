package com.gauravrmsc.critterchronologer.repository.entity;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class CustomerEntity extends UserEntity {
  private String phoneNumber;
  private String notes;
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner", fetch = FetchType.EAGER)
  private List<PetEntity> pets;
}
