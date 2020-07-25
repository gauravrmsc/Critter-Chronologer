package com.gauravrmsc.critterchronologer.user;

import com.gauravrmsc.critterchronologer.service.CustomerService;
import com.gauravrmsc.critterchronologer.service.EmployeeService;
import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Handles web requests related to Users.
 * <p>
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
public class UserController {

  @Autowired
  CustomerService customerService;
  @Autowired
  EmployeeService employeeService;

  @PostMapping("/customer")
  public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO) {
    return customerService.addCustomer(customerDTO);
  }

  @GetMapping("/customer")
  public List<CustomerDTO> getAllCustomers() {
    return customerService.getAllCustomers();
  }

  @GetMapping("/customer/pet/{petId}")
  public CustomerDTO getOwnerByPet(@PathVariable Long petId) {
    return customerService.findOwnerByPet(petId);
  }

  @PostMapping("/employee")
  public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
    return employeeService.saveEmployee(employeeDTO);
  }

  @PostMapping("/employee/{employeeId}")
  public EmployeeDTO getEmployee(@PathVariable Long employeeId) {
    return employeeService.getEmployee(employeeId);
  }

  @PutMapping("/employee/{employeeId}")
  public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable,
      @PathVariable Long employeeId) {
    employeeService.setAvailability(daysAvailable, employeeId);
  }

  @GetMapping("/employee/availability")
  public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {
    return employeeService
        .findEmployeesWithSpecifiedSkillsOnAParticualrDay(employeeDTO.getDate().getDayOfWeek(),
            employeeDTO.getSkills());
  }

}
