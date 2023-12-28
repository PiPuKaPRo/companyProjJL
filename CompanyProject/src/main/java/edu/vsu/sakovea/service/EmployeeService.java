package edu.vsu.sakovea.service;

import edu.vsu.sakovea.infra.beans.factory.annotation.EvgAutowired;
import edu.vsu.sakovea.infra.beans.factory.stereotype.EvgService;
import edu.vsu.sakovea.model.Department;
import edu.vsu.sakovea.model.Employee;
import edu.vsu.sakovea.repository.DbDepartmentRepository;
import edu.vsu.sakovea.repository.DbEmployeeRepository;

import java.util.List;
@EvgService
public class EmployeeService {
    @EvgAutowired
    private DbEmployeeRepository dbEmployeeRepository;

    public DbDepartmentRepository getDbDepartmentRepository() {
        return dbDepartmentRepository;
    }

    public void setDbDepartmentRepository(DbDepartmentRepository dbDepartmentRepository) {
        this.dbDepartmentRepository = dbDepartmentRepository;
    }

    @EvgAutowired
    private DbDepartmentRepository dbDepartmentRepository;

    public void setDbEmployeeRepository(DbEmployeeRepository dbEmployeeRepository) {
        this.dbEmployeeRepository = dbEmployeeRepository;
    }

    public void addEmployee(Employee employee, int department_id) {
        Department department = dbDepartmentRepository.getDepartmentById(department_id);
        System.out.println(department);
        dbEmployeeRepository.addEmployee(employee, department);
    }

    public void removeEmployee(Employee employee, int department_id) {
        Department department = dbDepartmentRepository.getDepartmentById(department_id);
        System.out.println(department);
        dbEmployeeRepository.deleteEmployee(employee, department);
    }

    public List<Employee> getEmployeesInDepartment(int department_id) {
        Department department = dbDepartmentRepository.getDepartmentById(department_id);
        System.out.println(department);
        List<Employee> employees = dbEmployeeRepository.getEmployeesInDepartment(department);
        return employees;
    }
}
