package com.gauravrmsc.critterchronologer.repository.entity;

import java.util.List;
import javax.persistence.*;
import lombok.Data;

@Entity
@Data
public class CustomerEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  private String phoneNumber;
  private String notes;
  @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "owner", fetch = FetchType.EAGER)
  private List<PetEntity> pets;
}
