package com.gauravrmsc.critterchronologer.repository.entity;

import com.gauravrmsc.critterchronologer.pet.PetType;
import java.time.LocalDate;
import javax.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Data
public class PetEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private PetType type;
  private String name;
  @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
  @JoinColumn(name = "owner" )
  private CustomerEntity owner;
  private LocalDate birthDate;
  private String notes;
  @ManyToMany
  private List<ScheduleEntity> schedules;

}
