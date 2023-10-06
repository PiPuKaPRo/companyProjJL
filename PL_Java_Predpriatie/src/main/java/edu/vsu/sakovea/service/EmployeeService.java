package main.java.edu.vsu.sakovea.service;

import main.java.edu.vsu.sakovea.model.Department;
import main.java.edu.vsu.sakovea.model.Employee;
import main.java.edu.vsu.sakovea.repository.EmployeeRepository;

import java.util.List;

public class EmployeeService {
    private EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
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
