package com.gauravrmsc.critterchronologer.repository.entity;

import com.gauravrmsc.critterchronologer.user.EmployeeSkill;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import javax.persistence.*;
import lombok.Data;

@Entity
@Data
public class ScheduleEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @ManyToMany
  private List<EmployeeEntity> employees;

  @ManyToMany
  private List<PetEntity> pets;

  private LocalDate date;

  @ElementCollection(fetch = FetchType.EAGER)
  private Set<EmployeeSkill> activities;
}
