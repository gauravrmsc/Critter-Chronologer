package com.gauravrmsc.critterchronologer.repository.entity;

import com.gauravrmsc.critterchronologer.user.EmployeeSkill;
import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import lombok.Data;

@Entity
@Data
public class EmployeeEntity extends UserEntity{

  @ElementCollection(fetch = FetchType.EAGER)
  private Set<EmployeeSkill> skills;
  @ElementCollection(fetch = FetchType.EAGER)
  private Set<DayOfWeek> daysAvailable;

}
