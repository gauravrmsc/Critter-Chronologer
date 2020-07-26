package com.gauravrmsc.critterchronologer.service;

import com.gauravrmsc.critterchronologer.repository.entity.EmployeeEntity;
import com.gauravrmsc.critterchronologer.repository.entity.PetEntity;
import com.gauravrmsc.critterchronologer.repository.entity.ScheduleEntity;
import com.gauravrmsc.critterchronologer.repository.repository.ScheduleRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ScheduleService {

  @Autowired
  ScheduleRepository scheduleRepository;
  @Autowired
  EmployeeService employeeService;
  @Autowired
  PetService petService;

  public ScheduleEntity createSchedule(ScheduleEntity scheduleEntity) {
    scheduleRepository.save(scheduleEntity);
    return scheduleEntity;
  }

  public List<ScheduleEntity> getScheduleForEmployee(Long employeeId) {

    Optional<EmployeeEntity> employeeEntity = employeeService.getEmployeeEntity(employeeId);
    List<ScheduleEntity> scheduleEntities = new ArrayList<>();
    if (employeeEntity.isPresent()) {
      scheduleEntities = scheduleRepository.findAllByEmployeesContaining(employeeEntity.get());
    }

    return scheduleEntities;
  }

  public List<ScheduleEntity> getScheduleForPet(Long petId) {
    Optional<PetEntity> petEntity = petService.getPetEntity(petId);
    List<ScheduleEntity> scheduleEntities = new ArrayList<>();
    if (petEntity.isPresent()) {
      scheduleEntities = scheduleRepository.findAllByPetsContaining(petEntity.get());
    }
    return scheduleEntities;
  }

  public List<ScheduleEntity> getScheduleForCustomer(Long customerId) {
    List<PetEntity> petEntities = petService.getPetsByOwner(customerId);
    List<ScheduleEntity> scheduleEntities = new ArrayList<>();
    for (PetEntity petEntity : petEntities) {
      List<ScheduleEntity> scheduleEntitiesForPet = getScheduleForPet(petEntity.getId());
      scheduleEntities.addAll(scheduleEntitiesForPet);
    }
    return scheduleEntities;
  }

  public List<ScheduleEntity> getAllSchedule() {
    List<ScheduleEntity> scheduleEntities = scheduleRepository.findAll();

    return scheduleEntities;
  }


}
