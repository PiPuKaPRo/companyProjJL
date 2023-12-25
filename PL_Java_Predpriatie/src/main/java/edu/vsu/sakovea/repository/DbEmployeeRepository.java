package main.java.edu.vsu.sakovea.repository;

import main.java.edu.vsu.sakovea.infra.beans.factory.stereotype.EvgComponent;
import main.java.edu.vsu.sakovea.infra.orm.DatabaseConnector;
import main.java.edu.vsu.sakovea.infra.orm.EntityManager;
import main.java.edu.vsu.sakovea.model.Department;
import main.java.edu.vsu.sakovea.model.Employee;

import java.util.List;

@EvgComponent
public class DbEmployeeRepository implements EmployeeRepository{
    @Override
    public void addEmployee(Employee employee, Department department) {
        EntityManager.save(employee, DatabaseConnector.getConnection());
    }

    @Override
    public void deleteEmployee(Employee employee, Department department) {
        EntityManager.delete(employee, DatabaseConnector.getConnection());
    }

    @Override
    public List<Employee> getEmployeesInDepartment(Department department) {
        List<Employee> employees = EntityManager.findAll(Employee.class, DatabaseConnector.getConnection())
                .stream().filter((employee -> employee.getDepartmentName() != null && employee.getDepartmentName()
                        .equals(department.getName()))).toList();
        return employees;
    }
}
