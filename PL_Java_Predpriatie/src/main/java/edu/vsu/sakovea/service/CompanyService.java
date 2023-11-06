package main.java.edu.vsu.sakovea.service;

import main.java.edu.vsu.sakovea.infra.beans.factory.annotation.EvgAutowired;
import main.java.edu.vsu.sakovea.infra.beans.factory.stereotype.EvgService;
import main.java.edu.vsu.sakovea.model.Department;
import main.java.edu.vsu.sakovea.model.Employee;

import java.util.List;

@EvgService
public class CompanyService {
    @EvgAutowired
    private DepartmentService departmentService;
    @EvgAutowired
    private EmployeeService employeeService;

    public void setDepartmentService(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    public void setEmployeeService(EmployeeService employeeService) {
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
