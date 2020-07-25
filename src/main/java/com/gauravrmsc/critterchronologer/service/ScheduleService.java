package com.gauravrmsc.critterchronologer.service;

import com.gauravrmsc.critterchronologer.pet.PetDTO;
import com.gauravrmsc.critterchronologer.repository.entity.EmployeeEntity;
import com.gauravrmsc.critterchronologer.repository.entity.PetEntity;
import com.gauravrmsc.critterchronologer.repository.entity.ScheduleEntity;
import com.gauravrmsc.critterchronologer.repository.repository.ScheduleRepository;
import com.gauravrmsc.critterchronologer.schedule.ScheduleDTO;
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

  public ScheduleDTO createSchedule(ScheduleDTO scheduleDTO) {
    ScheduleEntity scheduleEntity = new ScheduleEntity();
    scheduleEntity.setDate(scheduleDTO.getDate());
    scheduleEntity.setActivities(scheduleDTO.getActivities());
    List<EmployeeEntity> employeeEntities =
        employeeService.getEmployees(scheduleDTO.getEmployeeIds());
    scheduleEntity.setEmployees(employeeEntities);
    List<PetEntity> petEntities = petService.getPetEntities(scheduleDTO.getPetIds());
    scheduleEntity.setPets(petEntities);
    scheduleRepository.save(scheduleEntity);
    scheduleDTO.setId(scheduleEntity.getId());
    return scheduleDTO;
  }

  public List<ScheduleDTO> getScheduleForEmployee(Long employeeId) {

    Optional<EmployeeEntity> employeeEntity = employeeService.getEmployeeEntity(employeeId);
    List<ScheduleEntity> scheduleEntities = new ArrayList<>();
    if (employeeEntity.isPresent()) {
      scheduleEntities = scheduleRepository.findAllByEmployeesContaining(employeeEntity.get());
    }
    List<ScheduleDTO> scheduleDTOS = toScheduleDTOS(scheduleEntities);
    return scheduleDTOS;
  }

  public List<ScheduleDTO> getScheduleForPet(Long petId) {
    Optional<PetEntity> petEntity = petService.getPetEntity(petId);
    List<ScheduleEntity> scheduleEntities = new ArrayList<>();
    if (petEntity.isPresent()) {
      scheduleEntities = scheduleRepository.findAllByPetsContaining(petEntity.get());
    }
    List<ScheduleDTO> scheduleDTOS = toScheduleDTOS(scheduleEntities);
    return scheduleDTOS;
  }

  public List<ScheduleDTO> getScheduleForCustomer(Long customerId) {
    List<PetDTO> petEntities = petService.getPetsByOwner(customerId);
    List<ScheduleDTO> scheduleDTOS = new ArrayList<>();
    for (PetDTO petDTO : petEntities) {
      List<ScheduleDTO> scheduleDTOSForPet = getScheduleForPet(petDTO.getId());
      scheduleDTOS.addAll(scheduleDTOSForPet);
    }
    return scheduleDTOS;
  }

  public List<ScheduleDTO> getAllSchedule() {
    List<ScheduleEntity> scheduleEntities = scheduleRepository.findAll();
    List<ScheduleDTO> scheduleDTOS = toScheduleDTOS(scheduleEntities);

    return scheduleDTOS;
  }

  public ScheduleDTO toScheduleDTO(ScheduleEntity scheduleEntity) {
    List<Long> employeeIds = employeeService.mapEmployeeToEmployeeId(scheduleEntity.getEmployees());
    List<Long> petIds = petService.mapPetToPetId(scheduleEntity.getPets());
    ScheduleDTO scheduleDTO =
        new ScheduleDTO(scheduleEntity.getId(), employeeIds, petIds, scheduleEntity.getDate(),
            scheduleEntity.getActivities());
    return scheduleDTO;
  }

  public List<ScheduleDTO> toScheduleDTOS(List<ScheduleEntity> scheduleEntities) {
    List<ScheduleDTO> scheduleDTOS = new ArrayList<>();
    for (ScheduleEntity scheduleEntity : scheduleEntities) {
      scheduleDTOS.add(toScheduleDTO(scheduleEntity));
    }
    return scheduleDTOS;
  }
}
