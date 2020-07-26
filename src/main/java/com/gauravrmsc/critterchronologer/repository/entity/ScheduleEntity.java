package com.gauravrmsc.critterchronologer.repository.entity;

import com.gauravrmsc.critterchronologer.user.EmployeeSkill;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToMany
  @LazyCollection(LazyCollectionOption.FALSE)
  private List<EmployeeEntity> employees;

  @ManyToMany
  @LazyCollection(LazyCollectionOption.FALSE)
  private List<PetEntity> pets;

  private LocalDate date;

  @ElementCollection
  @LazyCollection(LazyCollectionOption.FALSE)
  private Set<EmployeeSkill> activities;
}
