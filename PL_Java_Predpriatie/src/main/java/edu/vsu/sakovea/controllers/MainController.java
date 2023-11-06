package main.java.edu.vsu.sakovea.controllers;

import main.java.edu.vsu.sakovea.infra.beans.factory.annotation.EvgAutowired;
import main.java.edu.vsu.sakovea.infra.beans.factory.stereotype.EvgComponent;
import main.java.edu.vsu.sakovea.service.CompanyService;
import main.java.edu.vsu.sakovea.model.Department;
import main.java.edu.vsu.sakovea.model.Employee;
import main.java.edu.vsu.sakovea.repository.DepartmentRepository;
import main.java.edu.vsu.sakovea.repository.EmployeeRepository;
import main.java.edu.vsu.sakovea.repository.memoryDepartmentRepository;
import main.java.edu.vsu.sakovea.repository.memoryEmployeeRepository;
import main.java.edu.vsu.sakovea.service.DepartmentService;
import main.java.edu.vsu.sakovea.service.EmployeeService;

import java.util.List;
import java.util.Scanner;

@EvgComponent
public class MainController {
    private Scanner scanner;
    @EvgAutowired
    private EmployeeController employeeController;
    @EvgAutowired
    private DepartmentController departmentController;

    public void runApp() {
        while (true) {
            System.out.println("Выберите действие:");
            System.out.println("1. Добавить отдел");
            System.out.println("2. Удалить отдел");
            System.out.println("3. Добавить сотрудника");
            System.out.println("4. Удалить сотрудника");
            System.out.println("5. Вывести все отделы");
            System.out.println("6. Вывести сотрудников в отделе");
            System.out.println("7. Выйти");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    departmentController.createDepartment();
                    break;

                case 2:
                    departmentController.deleteDepartment();
                    break;

                case 3:
                    employeeController.createEmployee();
                    break;

                case 4:
                    employeeController.deleteEmployee();
                    break;

                case 5:
                    departmentController.showAllDepartment();
                    break;

                case 6:
                    employeeController.showAllEmployeeInDepartment();
                    break;

                case 7:
                    System.exit(0);

                default:
                    System.out.println("Неверный выбор. Попробуйте снова.");
            }
        }
    }
}
