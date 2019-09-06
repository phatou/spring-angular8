package com.example.hello.controller;

import com.example.hello.exception.ResourceNotFoundException;
import com.example.hello.model.Employee;
import com.example.hello.repository.EmployeeRepository;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeController {

  private final EmployeeRepository repository;

  @Autowired
  public EmployeeController(EmployeeRepository repository) {
    this.repository = repository;
  }

  @GetMapping
  public List<Employee> getAllEmployees() {
    return repository.findAll();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Employee> getEmployeeById(@PathVariable(value = "id") Long employeeId)
      throws ResourceNotFoundException {
    Employee employee = repository.findById(employeeId)
        .orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + employeeId));
    return ResponseEntity.ok().body(employee);
  }

  @PostMapping
  public Employee createEmployee(@Valid @RequestBody Employee employee) {
    return repository.save(employee);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Employee> updateEmployee(@PathVariable(value = "id") Long employeeId,
                                                 @Valid @RequestBody Employee employeeDetails)
      throws ResourceNotFoundException {
    Employee employee = repository.findById(employeeId)
        .orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + employeeId));
    employee.setEmail(employeeDetails.getEmail());
    employee.setFirstName(employeeDetails.getFirstName());
    employee.setLastName(employeeDetails.getLastName());
    final Employee updateEmployee = repository.save(employee);
    return ResponseEntity.ok(updateEmployee);
  }

  @DeleteMapping("/{id}")
  public Map<String, Boolean> deleteEmployee(@PathVariable(value = "id") Long employeeId)
      throws ResourceNotFoundException {
    Employee employee = repository.findById(employeeId)
        .orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id : " + employeeId));
    repository.delete(employee);
    Map<String, Boolean> response = new HashMap<>();
    response.put("deleted", Boolean.TRUE);
    return response;
  }
}
