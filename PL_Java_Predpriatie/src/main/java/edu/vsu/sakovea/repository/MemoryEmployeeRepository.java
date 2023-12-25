package main.java.edu.vsu.sakovea.repository;

import main.java.edu.vsu.sakovea.infra.beans.factory.stereotype.EvgComponent;
import main.java.edu.vsu.sakovea.model.Department;
import main.java.edu.vsu.sakovea.model.Employee;

import java.util.ArrayList;
import java.util.List;
@EvgComponent
public class MemoryEmployeeRepository implements EmployeeRepository {
    private List<Employee> employeeStorage = new ArrayList<>();

    @Override
    public void addEmployee(Employee employee, Department department) {
        employeeStorage.add(employee);
    }

    @Override
    public void deleteEmployee(Employee employee, Department department) {
        employeeStorage.remove(employee);
    }

    @Override
    public List<Employee> getEmployeesInDepartment(Department department) {
        List<Employee> departmentEmployees = new ArrayList<>();
        for (Employee employee : employeeStorage) {
            if (employee.getDepartmentName() != null && employee.getDepartmentName().equals(department.getName())) {
                departmentEmployees.add(employee);
            }
        }
        return departmentEmployees;
    }
}
