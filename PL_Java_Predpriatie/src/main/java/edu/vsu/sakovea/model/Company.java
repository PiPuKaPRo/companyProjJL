package main.java.edu.vsu.sakovea.model;

import main.java.edu.vsu.sakovea.service.DepartmentService;
import main.java.edu.vsu.sakovea.service.EmployeeService;

import java.util.List;

public class Company {
    private DepartmentService departmentService;
    private EmployeeService employeeService;

    public Company(DepartmentService departmentService, EmployeeService employeeService) {
        this.departmentService = departmentService;
        this.employeeService = employeeService;
    }

    public void addDepartment(Department department) {
        departmentService.addDepartment(department);
    }

    public void deleteDepartment(Department department) {
        departmentService.removeDepartment(department);
    }

    public void addEmployee(Employee employee, Department department) {
        employeeService.addEmployee(employee, department);
    }

    public void deleteEmployee(Employee employee, Department department) {
        employeeService.removeEmployee(employee, department);
    }

    public List<Employee> getEmployeesInDepartment(Department department) {
        return employeeService.getEmployeesInDepartment(department);
    }
}
