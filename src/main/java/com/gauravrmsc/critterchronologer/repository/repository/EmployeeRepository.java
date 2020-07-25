package com.gauravrmsc.critterchronologer.repository.repository;

import com.gauravrmsc.critterchronologer.repository.entity.EmployeeEntity;
import com.gauravrmsc.critterchronologer.user.EmployeeSkill;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Long> {
  List<EmployeeEntity> findAllByDaysAvailableContains(DayOfWeek day);

}
