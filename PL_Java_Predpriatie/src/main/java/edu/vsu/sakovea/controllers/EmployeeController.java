package main.java.edu.vsu.sakovea.controllers;

import main.java.edu.vsu.sakovea.infra.beans.factory.annotation.EvgAutowired;
import main.java.edu.vsu.sakovea.infra.beans.factory.stereotype.EvgComponent;
import main.java.edu.vsu.sakovea.model.Department;
import main.java.edu.vsu.sakovea.model.Employee;
import main.java.edu.vsu.sakovea.repository.DepartmentRepository;
import main.java.edu.vsu.sakovea.repository.MemoryDepartmentRepository;
import main.java.edu.vsu.sakovea.service.CompanyService;

import java.util.List;
import java.util.Scanner;

@EvgComponent
public class EmployeeController {
    private final Scanner scanner = new Scanner(System.in);
    @EvgAutowired
    private Department departmentName;
    @EvgAutowired
    private MemoryDepartmentRepository departmentRepository;
    @EvgAutowired
    private CompanyService companyService;

    public void setDepartmentName(Department departmentName) {
        this.departmentName = departmentName;
    }

    public void setDepartmentRepository(MemoryDepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
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
        departmentName.setName(scanner.nextLine());
        Employee employee = new Employee(fullName, age, salary);
        Department employeeDepartment = departmentRepository.getDepartmentByName(departmentName.getName());
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
        departmentName.setName(scanner.nextLine());

        Employee employeeToRemove = new Employee(employeeFullName, 0, 0);
        Department departmentToRemoveFrom = departmentRepository.getDepartmentByName(departmentName.getName());

        if (departmentToRemoveFrom != null) {
            companyService.deleteEmployee(employeeToRemove, departmentToRemoveFrom);
            System.out.println("Сотрудник успешно удален.");
        } else {
            System.out.println("Отдел или сотрудник не найдены.");
        }
    }

    public void showAllEmployeeInDepartment(){
        System.out.println("Введите название отдела для вывода сотрудников:");
        departmentName.setName(scanner.nextLine());
        Department departmentToDisplay = departmentRepository.getDepartmentByName(departmentName.getName());

        if (departmentToDisplay != null) {
            List<Employee> departmentEmployees = companyService.getEmployeesInDepartment(departmentToDisplay);
            System.out.println("Сотрудники в отделе " + departmentToDisplay.getName() + ":");
            for (Employee emp : departmentEmployees) {
                System.out.println("ФИО: " + emp.getFullName() + ", Возраст: " + emp.getAge() + ", ЗП: " + emp.getSalary());
            }
        } else {
            System.out.println("Отдел не найден.");
        }
    }
}