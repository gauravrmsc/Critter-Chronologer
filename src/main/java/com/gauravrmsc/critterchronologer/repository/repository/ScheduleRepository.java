package com.gauravrmsc.critterchronologer.repository.repository;

import com.gauravrmsc.critterchronologer.repository.entity.EmployeeEntity;
import com.gauravrmsc.critterchronologer.repository.entity.PetEntity;
import com.gauravrmsc.critterchronologer.repository.entity.ScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface ScheduleRepository extends JpaRepository<ScheduleEntity, Long> {
  List<ScheduleEntity> findAllByEmployeesContaining(EmployeeEntity employeeEntity);
  List<ScheduleEntity> findAllByPetsContaining(PetEntity pet);
}
