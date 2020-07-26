package com.gauravrmsc.critterchronologer.user;

import com.gauravrmsc.critterchronologer.repository.entity.CustomerEntity;
import com.gauravrmsc.critterchronologer.repository.entity.EmployeeEntity;
import com.gauravrmsc.critterchronologer.repository.entity.PetEntity;
import com.gauravrmsc.critterchronologer.service.CustomerService;
import com.gauravrmsc.critterchronologer.service.EmployeeService;
import com.gauravrmsc.critterchronologer.service.PetService;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.beans.BeanUtils;
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

  @Autowired
  PetService petService;

  @PostMapping("/customer")
  public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO) {
    CustomerEntity customerEntity = toCustomerEntity(customerDTO);
    customerService.addCustomer(customerEntity);
    return toCustmerDTO(customerEntity);
  }

  @GetMapping("/customer")
  public List<CustomerDTO> getAllCustomers() {
    List<CustomerEntity> customerEntities = customerService.getAllCustomers();
    return toCustomerDTOs(customerEntities);
  }

  @GetMapping("/customer/pet/{petId}")
  public CustomerDTO getOwnerByPet(@PathVariable Long petId) {
    CustomerEntity customerEntity = customerService.findOwnerByPet(petId);
    return toCustmerDTO(customerEntity);
  }

  @PostMapping("/employee")
  public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
    EmployeeEntity employeeEntity = toEmployeeEntity(employeeDTO);
    employeeEntity = employeeService.saveEmployee(employeeEntity);
    return toEmployeeDTO(employeeEntity);
  }

  @PostMapping("/employee/{employeeId}")
  public EmployeeDTO getEmployee(@PathVariable Long employeeId) {
    EmployeeEntity employeeEntity = employeeService.getEmployee(employeeId);
    return toEmployeeDTO(employeeEntity);
  }

  @PutMapping("/employee/{employeeId}")
  public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable,
      @PathVariable Long employeeId) {
    employeeService.setAvailability(daysAvailable, employeeId);
  }

  @GetMapping("/employee/availability")
  public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {
    List<EmployeeEntity> availableEmployees = employeeService
        .findEmployeesWithSpecifiedSkillsOnAParticualrDay(employeeDTO.getDate().getDayOfWeek(),
            employeeDTO.getSkills());
    return toEmployeeDTOs(availableEmployees);
  }

  public CustomerDTO toCustmerDTO(CustomerEntity customerEntity) {
    CustomerDTO customerDTO = new CustomerDTO();
    BeanUtils.copyProperties(customerEntity, customerDTO);
    List<Long> petIds = new ArrayList<>();
    if (customerEntity.getPets() != null) {
      for (PetEntity petEntity : customerEntity.getPets()) {
        petIds.add(petEntity.getId());
      }
    }
    customerDTO.setPetIds(petIds);
    return customerDTO;
  }

  public CustomerEntity toCustomerEntity(CustomerDTO customerDTO) {
    CustomerEntity customerEntity = new CustomerEntity();
    BeanUtils.copyProperties(customerDTO, customerEntity);
    if (customerDTO.getPetIds() != null) {
      List<PetEntity> petEntities = new ArrayList<>();
      for (Long petId : customerDTO.getPetIds()) {
        Optional<PetEntity> petEntity = petService.getPetEntity(petId);
        if (petEntity.isPresent()) {
          petEntities.add(petEntity.get());
        }
      }
      customerEntity.setPets(petEntities);
    }
    return customerEntity;
  }

  public List<CustomerDTO> toCustomerDTOs(List<CustomerEntity> customerEntities) {
    List<CustomerDTO> customerDTOs = new ArrayList<>();
    for (CustomerEntity customerEntity : customerEntities) {
      CustomerDTO customerDTO = toCustmerDTO(customerEntity);
      customerDTOs.add(customerDTO);
    }
    return customerDTOs;
  }

  private EmployeeEntity toEmployeeEntity(EmployeeDTO employeeDTO) {
    EmployeeEntity employeeEntity = new EmployeeEntity();
    BeanUtils.copyProperties(employeeDTO, employeeEntity);
    return employeeEntity;
  }

  private EmployeeDTO toEmployeeDTO(EmployeeEntity employeeEntity) {
    EmployeeDTO employeeDTO = new EmployeeDTO();
    BeanUtils.copyProperties(employeeEntity, employeeDTO);
    return employeeDTO;
  }

  private List<EmployeeDTO> toEmployeeDTOs(List<EmployeeEntity> employeeEntities) {
    List<EmployeeDTO> employeeDTOs = new ArrayList<>();
    if (employeeEntities != null) {
      for (EmployeeEntity employeeEntity : employeeEntities) {
        EmployeeDTO employeeDTO = toEmployeeDTO(employeeEntity);
        employeeDTOs.add(employeeDTO);
      }
    }
    return employeeDTOs;
  }

}
