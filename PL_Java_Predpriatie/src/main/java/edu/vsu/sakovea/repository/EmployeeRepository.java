package main.java.edu.vsu.sakovea.repository;

import main.java.edu.vsu.sakovea.model.Department;
import main.java.edu.vsu.sakovea.model.Employee;

import java.util.List;

public interface EmployeeRepository {
    void addEmployee(Employee employee, Department department);
    void deleteEmployee(Employee employee, Department department);
    List<Employee> getEmployeesInDepartment(Department department);
}
