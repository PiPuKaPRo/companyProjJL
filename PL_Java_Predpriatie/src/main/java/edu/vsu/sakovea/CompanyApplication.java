package main.java.edu.vsu.sakovea;

import main.java.edu.vsu.sakovea.model.Company;
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

public class CompanyApplication {
    public static void main(String[] args) {
        DepartmentRepository departmentRepository = new memoryDepartmentRepository();
        EmployeeRepository employeeRepository = new memoryEmployeeRepository();

        DepartmentService departmentService = new DepartmentService(departmentRepository);
        EmployeeService employeeService = new EmployeeService(employeeRepository);

        Company company = new Company(departmentService, employeeService);

        Scanner scanner = new Scanner(System.in);

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
                    System.out.println("Введите название отдела:");
                    String departmentName = scanner.nextLine();
                    Department department = new Department(departmentName);
                    company.addDepartment(department);
                    break;

                case 2:
                    System.out.println("Введите название отдела для удаления:");
                    departmentName = scanner.nextLine();
                    Department departmentToRemove = new Department(departmentName);
                    company.deleteDepartment(departmentToRemove);
                    break;

                case 3:
                    System.out.println("Введите ФИО сотрудника:");
                    String fullName = scanner.nextLine();
                    System.out.println("Введите возраст сотрудника:");
                    int age = scanner.nextInt();
                    System.out.println("Введите ЗП сотрудника:");
                    double salary = scanner.nextDouble();
                    scanner.nextLine();
                    System.out.println("Введите название отдела, в котором работает сотрудник:");
                    departmentName = scanner.nextLine();
                    Employee employee = new Employee(fullName, age, salary);
                    Department employeeDepartment = departmentRepository.getDepartmentByName(departmentName);
                    if (employeeDepartment != null) {
                        company.addEmployee(employee, employeeDepartment);
                    } else {
                        System.out.println("Отдел не найден.");
                    }
                    break;

                case 4:
                    System.out.println("Введите ФИО сотрудника для удаления:");
                    String employeeFullName = scanner.nextLine();
                    System.out.println("Введите название отдела, из которого удалить сотрудника:");
                    departmentName = scanner.nextLine();

                    Employee employeeToRemove = new Employee(employeeFullName, 0, 0);
                    Department departmentToRemoveFrom = departmentRepository.getDepartmentByName(departmentName);

                    if (departmentToRemoveFrom != null) {
                        company.deleteEmployee(employeeToRemove, departmentToRemoveFrom);
                        System.out.println("Сотрудник успешно удален.");
                    } else {
                        System.out.println("Отдел или сотрудник не найдены.");
                    }
                    break;

                case 5:
                    List<Department> allDepartments = departmentRepository.getAllDepartments();
                    System.out.println("Список отделов:");
                    for (Department dep : allDepartments) {
                        System.out.println("Название: " + dep.getName() + ", Количество сотрудников: " + dep.getNumberOfEmployees());
                    }
                    break;

                case 6:
                    System.out.println("Введите название отдела для вывода сотрудников:");
                    departmentName = scanner.nextLine();
                    Department departmentToDisplay = departmentRepository.getDepartmentByName(departmentName);

                    if (departmentToDisplay != null) {
                        List<Employee> departmentEmployees = company.getEmployeesInDepartment(departmentToDisplay);
                        System.out.println("Сотрудники в отделе " + departmentToDisplay.getName() + ":");
                        for (Employee emp : departmentEmployees) {
                            System.out.println("ФИО: " + emp.getFullName() + ", Возраст: " + emp.getAge() + ", ЗП: " + emp.getSalary());
                        }
                    } else {
                        System.out.println("Отдел не найден.");
                    }
                    break;

                case 7:
                    System.exit(0);

                default:
                    System.out.println("Неверный выбор. Попробуйте снова.");
            }
        }
    }
}


