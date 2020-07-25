package com.gauravrmsc.critterchronologer.schedule;

import com.gauravrmsc.critterchronologer.service.ScheduleService;
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
    return scheduleService.createSchedule(scheduleDTO);
  }

  @GetMapping
  public List<ScheduleDTO> getAllSchedules() {
    return scheduleService.getAllSchedule();
  }

  @GetMapping("/pet/{petId}")
  public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
    return scheduleService.getScheduleForPet(petId);
  }

  @GetMapping("/employee/{employeeId}")
  public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
    return scheduleService.getScheduleForEmployee(employeeId);
  }

  @GetMapping("/customer/{customerId}")
  public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
    return scheduleService.getScheduleForCustomer(customerId);
  }
}
