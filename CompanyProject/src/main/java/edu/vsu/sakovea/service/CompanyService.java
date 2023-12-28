package edu.vsu.sakovea.service;

import lombok.Data;
import edu.vsu.sakovea.infra.beans.factory.annotation.EvgAutowired;
import edu.vsu.sakovea.infra.beans.factory.stereotype.EvgService;
import edu.vsu.sakovea.model.Department;
import edu.vsu.sakovea.model.Employee;

import java.util.List;

@EvgService
public class CompanyService {
    public DepartmentService getDepartmentService() {
        return departmentService;
    }

    public void setDepartmentService(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    public EmployeeService getEmployeeService() {
        return employeeService;
    }

    public void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @EvgAutowired
    private DepartmentService departmentService;
    @EvgAutowired
    private EmployeeService employeeService;

    public void addDepartment(Department department) {
        departmentService.addDepartment(department);
    }

    public void deleteDepartment(Department department) {
        departmentService.removeDepartment(department);
    }

    public void addEmployee(Employee employee, Department department) {
        employeeService.addEmployee(employee, department.getId());
    }

    public void deleteEmployee(Employee employee, Department department) {
        employeeService.removeEmployee(employee, department.getId());
    }

    public List<Employee> getEmployeesInDepartment(Department department) {
        return employeeService.getEmployeesInDepartment(department.getId());
    }
}
