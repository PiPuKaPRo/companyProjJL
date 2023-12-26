package edu.vsu.sakovea.repository;

import edu.vsu.sakovea.infra.beans.factory.stereotype.EvgComponent;
import edu.vsu.sakovea.infra.orm.DatabaseConnector;
import edu.vsu.sakovea.infra.orm.EntityManager;
import edu.vsu.sakovea.model.Department;
import edu.vsu.sakovea.model.Employee;

import java.util.List;

import static java.util.stream.Collectors.toList;

@EvgComponent
public class DbEmployeeRepository implements EmployeeRepository{
    @Override
    public void addEmployee(Employee employee, Department department) {
        employee.setDepartment_id(department.getId());
        EntityManager.save(employee, DatabaseConnector.getConnection());
    }

    @Override
    public void deleteEmployee(Employee employee, Department department) {
        employee.setDepartment_id(department.getId());
        EntityManager.delete(employee, DatabaseConnector.getConnection());
    }

    @Override
    public List<Employee> getEmployeesInDepartment(Department department) {
        List<Employee> employees = EntityManager.findAll(Employee.class, DatabaseConnector.getConnection())
                .stream()
                .filter(employee -> employee.getDepartment_id() == department.getId())
                .toList();
        return employees;
    }
}
