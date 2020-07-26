package com.gauravrmsc.critterchronologer.repository.entity;

import com.gauravrmsc.critterchronologer.user.EmployeeSkill;
import java.time.DayOfWeek;
import java.util.Set;
import javax.persistence.*;
import lombok.Data;

@Entity
@Data
public class EmployeeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  @ElementCollection(fetch = FetchType.EAGER)
  private Set<EmployeeSkill> skills;
  @ElementCollection(fetch = FetchType.EAGER)
  private Set<DayOfWeek> daysAvailable;

}
