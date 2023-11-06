package main.java.edu.vsu.sakovea.controllers;

import main.java.edu.vsu.sakovea.infra.beans.factory.annotation.EvgAutowired;
import main.java.edu.vsu.sakovea.infra.beans.factory.stereotype.EvgComponent;

import java.util.Scanner;

@EvgComponent
public class MainController {
    private final Scanner scanner = new Scanner(System.in);
    @EvgAutowired
    private EmployeeController employeeController;
    @EvgAutowired
    private DepartmentController departmentController;

    public void setEmployeeController(EmployeeController employeeController) {
        this.employeeController = employeeController;
    }

    public void setDepartmentController(DepartmentController departmentController) {
        this.departmentController = departmentController;
    }

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
