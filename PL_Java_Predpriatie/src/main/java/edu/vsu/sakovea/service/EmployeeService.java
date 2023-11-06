package main.java.edu.vsu.sakovea.service;

import main.java.edu.vsu.sakovea.infra.beans.factory.annotation.EvgAutowired;
import main.java.edu.vsu.sakovea.infra.beans.factory.stereotype.EvgService;
import main.java.edu.vsu.sakovea.model.Department;
import main.java.edu.vsu.sakovea.model.Employee;
import main.java.edu.vsu.sakovea.repository.EmployeeRepository;
import main.java.edu.vsu.sakovea.repository.MemoryEmployeeRepository;

import java.util.List;
@EvgService
public class EmployeeService {
    @EvgAutowired
    private MemoryEmployeeRepository employeeRepository;

    public void setEmployeeRepository(MemoryEmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public void addEmployee(Employee employee, Department department) {
        employeeRepository.addEmployee(employee, department);
    }

    public void removeEmployee(Employee employee, Department department) {
        employeeRepository.deleteEmployee(employee, department);
    }

    public List<Employee> getEmployeesInDepartment(Department department) {
        return employeeRepository.getEmployeesInDepartment(department);
    }
}
