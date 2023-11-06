package main.java.edu.vsu.sakovea.controllers;

import main.java.edu.vsu.sakovea.infra.beans.factory.annotation.EvgAutowired;
import main.java.edu.vsu.sakovea.infra.beans.factory.stereotype.EvgComponent;
import main.java.edu.vsu.sakovea.model.Department;
import main.java.edu.vsu.sakovea.repository.DepartmentRepository;
import main.java.edu.vsu.sakovea.repository.MemoryDepartmentRepository;
import main.java.edu.vsu.sakovea.service.CompanyService;

import java.util.List;
import java.util.Scanner;

@EvgComponent
public class DepartmentController {
    private final Scanner scanner = new Scanner(System.in);
    @EvgAutowired
    private Department departmentName;
    @EvgAutowired
    private CompanyService companyService;
    @EvgAutowired
    private MemoryDepartmentRepository departmentRepository;

    public void setDepartmentName(Department departmentName) {
        this.departmentName = departmentName;
    }

    public void setCompanyService(CompanyService companyService) {
        this.companyService = companyService;
    }

    public void setDepartmentRepository(MemoryDepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public void createDepartment(){
        System.out.println("Введите название отдела:");
        departmentName.setName(scanner.nextLine());
        Department department = new Department(departmentName.getName());
        companyService.addDepartment(department);
    }

    public void deleteDepartment(){
        System.out.println("Введите название отдела для удаления:");
        departmentName.setName(scanner.nextLine());
        Department departmentToRemove = new Department(departmentName.getName());
        companyService.deleteDepartment(departmentToRemove);
    }
    public void showAllDepartment(){
        List<Department> allDepartments = departmentRepository.getAllDepartments();
        System.out.println("Список отделов:");
        for (Department dep : allDepartments) {
            System.out.println("Название: " + dep.getName() + ", Количество сотрудников: " + dep.getNumberOfEmployees());
        }
    }
}
