package com.gauravrmsc.critterchronologer.service;

import com.gauravrmsc.critterchronologer.repository.entity.EmployeeEntity;
import com.gauravrmsc.critterchronologer.repository.repository.EmployeeRepository;
import com.gauravrmsc.critterchronologer.user.EmployeeSkill;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class EmployeeService {

  @Autowired
  EmployeeRepository employeeRepository;

  public EmployeeEntity saveEmployee(EmployeeEntity employeeEntity) {
    return employeeRepository.save(employeeEntity);
  }

  public Optional<EmployeeEntity> getEmployeeEntity(Long employeeId) {
    Optional<EmployeeEntity> employeeEntity = employeeRepository.findById(employeeId);
    return employeeEntity;
  }

  public List<EmployeeEntity> getEmployees(List<Long> employeeIds) {
    List<EmployeeEntity> employeeEntities = new ArrayList<>();
    for (Long employeeId : employeeIds) {
      Optional<EmployeeEntity> eemployeeEntity = getEmployeeEntity(employeeId);
      if (eemployeeEntity.isPresent()) {
        employeeEntities.add(eemployeeEntity.get());
      }
    }
    return employeeEntities;
  }

  public EmployeeEntity getEmployee(Long employeeId) {
    Optional<EmployeeEntity> employeeEntity = getEmployeeEntity(employeeId);
    if (employeeEntity.isPresent()) {
      return employeeEntity.get();
    } else {
      return new EmployeeEntity();
    }
  }

  public void setAvailability(Set<DayOfWeek> daysAvailable, Long employeeId) {
    Optional<EmployeeEntity> employeeEntity = employeeRepository.findById(employeeId);
    if (employeeEntity.isPresent()) {
      employeeEntity.get().setDaysAvailable(daysAvailable);
    }
  }

  public List<EmployeeEntity> findEmployeesWithSpecifiedSkillsOnAParticualrDay(DayOfWeek day,
      Set<EmployeeSkill> skills) {
    List<EmployeeEntity> employeeEntities = employeeRepository.findAllByDaysAvailableContains(day);
    List<EmployeeEntity> candidateEmployees = new ArrayList<>();
    for (EmployeeEntity employeeEntity : employeeEntities) {
      if (employeeEntity.getSkills().containsAll(skills)) {
        candidateEmployees.add(employeeEntity);
      }
    }
    return candidateEmployees;
  }

}
