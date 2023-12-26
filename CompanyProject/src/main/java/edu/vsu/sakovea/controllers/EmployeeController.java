package edu.vsu.sakovea.controllers;

import edu.vsu.sakovea.infra.beans.factory.annotation.EvgAutowired;
import edu.vsu.sakovea.infra.beans.factory.stereotype.EvgComponent;
import edu.vsu.sakovea.model.Department;
import edu.vsu.sakovea.model.Employee;
import edu.vsu.sakovea.repository.DbDepartmentRepository;
import edu.vsu.sakovea.service.CompanyService;

import java.util.List;
import java.util.Scanner;

@EvgComponent
public class EmployeeController {
    private final Scanner scanner = new Scanner(System.in);
    @EvgAutowired
    private Department departmentId;
    @EvgAutowired
    private DbDepartmentRepository dbDepartmentRepository;
    @EvgAutowired
    private CompanyService companyService;

    public void setDepartmentId(Department departmentId) {
        this.departmentId = departmentId;
    }

    public void setDbDepartmentRepository(DbDepartmentRepository dbDepartmentRepository) {
        this.dbDepartmentRepository = dbDepartmentRepository;
    }

    public void setCompanyService(CompanyService companyService) {
        this.companyService = companyService;
    }

    public void createEmployee(){
        System.out.println("Введите ФИО сотрудника:");
        String fullName = scanner.nextLine();
        System.out.println("Введите возраст сотрудника:");
        int age = scanner.nextInt();
        System.out.println("Введите ЗП сотрудника:");
        double salary = scanner.nextDouble();
        scanner.nextLine();
        System.out.println("Введите название отдела, в котором работает сотрудник:");
        departmentId.setId(Integer.parseInt(scanner.nextLine()));
        Employee employee = new Employee(fullName, age, salary);
        Department employeeDepartment = dbDepartmentRepository.getDepartmentById(departmentId.getId());
        if (employeeDepartment != null) {
            companyService.addEmployee(employee, employeeDepartment);
        } else {
            System.out.println("Отдел не найден.");
        }
    }

    public void deleteEmployee(){
        System.out.println("Введите ФИО сотрудника для удаления:");
        String employeeFullName = scanner.nextLine();
        System.out.println("Введите название отдела, из которого удалить сотрудника:");
        departmentId.setId(Integer.parseInt(scanner.nextLine()));

        Employee employeeToRemove = new Employee(employeeFullName, 0, 0);
        Department departmentToRemoveFrom = dbDepartmentRepository.getDepartmentById(departmentId.getId());

        if (departmentToRemoveFrom != null) {
            companyService.deleteEmployee(employeeToRemove, departmentToRemoveFrom);
            System.out.println("Сотрудник успешно удален.");
        } else {
            System.out.println("Отдел или сотрудник не найдены.");
        }
    }

    public void showAllEmployeeInDepartment(){
        System.out.println("Введите название отдела для вывода сотрудников:");
        departmentId.setId(Integer.parseInt(scanner.nextLine()));
        Department departmentToDisplay = dbDepartmentRepository.getDepartmentById(departmentId.getId());

        if (departmentToDisplay != null) {
            List<Employee> departmentEmployees = companyService.getEmployeesInDepartment(departmentToDisplay);
            System.out.println("Сотрудники в отделе " + departmentToDisplay.getName() + ":");
            for (Employee emp : departmentEmployees) {
                System.out.println("ФИО: " + emp.getFull_name() + ", Возраст: " + emp.getAge() + ", ЗП: " + emp.getSalary());
            }
        } else {
            System.out.println("Отдел не найден.");
        }
    }
}
