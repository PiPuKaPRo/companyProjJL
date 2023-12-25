package main.java.edu.vsu.sakovea.service;

import main.java.edu.vsu.sakovea.infra.beans.factory.annotation.EvgAutowired;
import main.java.edu.vsu.sakovea.infra.beans.factory.stereotype.EvgService;
import main.java.edu.vsu.sakovea.model.Department;
import main.java.edu.vsu.sakovea.model.Employee;
import main.java.edu.vsu.sakovea.repository.DbEmployeeRepository;

import java.util.List;
@EvgService
public class EmployeeService {
    @EvgAutowired
    private DbEmployeeRepository dbEmployeeRepository;

    public void setDbEmployeeRepository(DbEmployeeRepository dbEmployeeRepository) {
        this.dbEmployeeRepository = dbEmployeeRepository;
    }

    public void addEmployee(Employee employee, Department department) {
        dbEmployeeRepository.addEmployee(employee, department);
    }

    public void removeEmployee(Employee employee, Department department) {
        dbEmployeeRepository.deleteEmployee(employee, department);
    }

    public List<Employee> getEmployeesInDepartment(Department department) {
        return dbEmployeeRepository.getEmployeesInDepartment(department);
    }
}
