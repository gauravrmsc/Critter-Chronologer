package com.gauravrmsc.critterchronologer.service;

import com.gauravrmsc.critterchronologer.repository.entity.EmployeeEntity;
import com.gauravrmsc.critterchronologer.repository.repository.EmployeeRepository;
import com.gauravrmsc.critterchronologer.user.EmployeeDTO;
import com.gauravrmsc.critterchronologer.user.EmployeeSkill;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import javax.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class EmployeeService {

  @Autowired
  EmployeeRepository employeeRepository;

  public EmployeeDTO saveEmployee(EmployeeDTO employeeDTO) {
    EmployeeEntity employeeEntity = new EmployeeEntity();
    BeanUtils.copyProperties(employeeDTO, employeeEntity);
    employeeRepository.save(employeeEntity);
    BeanUtils.copyProperties(employeeEntity, employeeDTO);
    return employeeDTO;
  }

  public Optional<EmployeeEntity> getEmployeeEntity(Long employeeId) {
    Optional<EmployeeEntity> employeeEntity = employeeRepository.findById(employeeId);
    return employeeEntity;
  }

  public List<EmployeeEntity> getEmployees(List<Long> employeeIds) {
    List<EmployeeEntity> employeeeEntities = new ArrayList<>();
    for (Long employeeId : employeeIds) {
      Optional<EmployeeEntity> eemployeeEntity = getEmployeeEntity(employeeId);
      if (eemployeeEntity.isPresent()) {
        employeeeEntities.add(eemployeeEntity.get());
      }
    }
    return employeeeEntities;
  }
  public EmployeeDTO getEmployee(Long employeeId) {
    EmployeeDTO employeeDTO = new EmployeeDTO();
    Optional<EmployeeEntity> employeeEntity = getEmployeeEntity(employeeId);
    if (employeeEntity.isPresent()) {
      BeanUtils.copyProperties(employeeEntity.get(), employeeDTO);
    }
    return employeeDTO;
  }

  public void setAvailability(Set<DayOfWeek> daysAvailable, Long employeeId) {
    Optional<EmployeeEntity> employeeEntity = employeeRepository.findById(employeeId);
    if (employeeEntity.isPresent()) {
      employeeEntity.get().setDaysAvailable(daysAvailable);
    }
  }

  public List<EmployeeDTO> findEmployeesWithSpecifiedSkillsOnAParticualrDay(DayOfWeek day,
      Set<EmployeeSkill> skills) {
    List<EmployeeEntity> employeeEntities = employeeRepository.findAllByDaysAvailableContains(day);
    List<EmployeeEntity> candidateEmployees = new ArrayList<>();
    for (EmployeeEntity employeeEntity : employeeEntities) {
      if (employeeEntity.getSkills().containsAll(skills)) {
        candidateEmployees.add(employeeEntity);
      }
    }
    List<EmployeeDTO> employeeDTOS = new ArrayList<>();
    for (EmployeeEntity employeeEntity : candidateEmployees) {
      EmployeeDTO employeeDTO = new EmployeeDTO();
      BeanUtils.copyProperties(employeeEntity, employeeDTO);
      employeeDTOS.add(employeeDTO);
    }
    return employeeDTOS;
  }

  public List<Long> mapEmployeeToEmployeeId(List<EmployeeEntity> employeeEntities) {
    List<Long> employeeIds = new ArrayList<>();
    for (EmployeeEntity employeeEntity : employeeEntities) {
      employeeIds.add(employeeEntity.getId());
    }
    return employeeIds;
  }
}
