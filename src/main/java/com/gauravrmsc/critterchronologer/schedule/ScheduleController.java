package com.gauravrmsc.critterchronologer.schedule;

import com.gauravrmsc.critterchronologer.repository.entity.EmployeeEntity;
import com.gauravrmsc.critterchronologer.repository.entity.PetEntity;
import com.gauravrmsc.critterchronologer.repository.entity.ScheduleEntity;
import com.gauravrmsc.critterchronologer.service.ScheduleService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {
  @Autowired
  ScheduleService scheduleService;

  @PostMapping
  public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
    ScheduleEntity scheduleEntity = toScheduleEntity(scheduleDTO);
    scheduleEntity =  scheduleService.createSchedule(scheduleEntity);
    return toScheduleDTO(scheduleEntity);
  }

  @GetMapping
  public List<ScheduleDTO> getAllSchedules() {
    return toScheduleDTOs(scheduleService.getAllSchedule());
  }

  @GetMapping("/pet/{petId}")
  public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
    return toScheduleDTOs(scheduleService.getScheduleForPet(petId));
  }

  @GetMapping("/employee/{employeeId}")
  public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
    return toScheduleDTOs(scheduleService.getScheduleForEmployee(employeeId));
  }

  @GetMapping("/customer/{customerId}")
  public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
    return toScheduleDTOs(scheduleService.getScheduleForCustomer(customerId));
  }

  public ScheduleDTO toScheduleDTO(ScheduleEntity scheduleEntity) {
    List<Long> employeeIds = mapEmployeeToEmployeeId(scheduleEntity.getEmployees());
    List<Long> petIds = mapPetToPetId(scheduleEntity.getPets());
    ScheduleDTO scheduleDTO =
        new ScheduleDTO(scheduleEntity.getId(), employeeIds, petIds, scheduleEntity.getDate(),
            scheduleEntity.getActivities());
    return scheduleDTO;
  }

  public List<ScheduleDTO> toScheduleDTOs(List<ScheduleEntity> scheduleEntities) {
    List<ScheduleDTO> scheduleDTOS = new ArrayList<>();
    for (ScheduleEntity scheduleEntity : scheduleEntities) {
      scheduleDTOS.add(toScheduleDTO(scheduleEntity));
    }
    return scheduleDTOS;
  }

  public ScheduleEntity toScheduleEntity(ScheduleDTO scheduleDTO) {
    List<EmployeeEntity> employeeEntities = toEmployeeEntities(scheduleDTO.getEmployeeIds());
    List<PetEntity> petEntities = toPetEntities(scheduleDTO.getPetIds());
    ScheduleEntity scheduleEntity =
        new ScheduleEntity(scheduleDTO.getId(), employeeEntities, petEntities,
            scheduleDTO.getDate(), scheduleDTO.getActivities());
    return scheduleEntity;
  }

  public List<ScheduleEntity> toScheduleEntities(List<ScheduleDTO> scheduleDTOs) {
    List<ScheduleEntity> scheduleEntities = new ArrayList<>();
    for (ScheduleDTO scheduleDTO : scheduleDTOs) {
      scheduleEntities.add(toScheduleEntity(scheduleDTO));
    }
    return scheduleEntities;
  }

  public List<Long> mapEmployeeToEmployeeId(List<EmployeeEntity> employeeEntities) {
    List<Long> employeeIds = new ArrayList<>();
    for (EmployeeEntity employeeEntity : employeeEntities) {
      employeeIds.add(employeeEntity.getId());
    }
    return employeeIds;
  }

  public List<Long> mapPetToPetId(List<PetEntity> PetEntities) {
    List<Long> PetIds = new ArrayList<>();
    for (PetEntity PetEntity : PetEntities) {
      PetIds.add(PetEntity.getId());
    }
    return PetIds;
  }

  List<PetEntity> toPetEntities(List<Long> petIds) {
    List<PetEntity> petEntities = new ArrayList<>();
    for (Long petId : petIds) {
      PetEntity petEntity = new PetEntity();
      petEntity.setId(petId);
      petEntities.add(petEntity);
    }
    return petEntities;
  }

  List<EmployeeEntity> toEmployeeEntities(List<Long> employeeIds) {
    List<EmployeeEntity> employeeEntities = new ArrayList<>();
    for (Long EmployeeId : employeeIds) {
      EmployeeEntity employeeEntity = new EmployeeEntity();
      employeeEntity.setId(EmployeeId);
      employeeEntities.add(employeeEntity);
    }
    return employeeEntities;
  }
}
