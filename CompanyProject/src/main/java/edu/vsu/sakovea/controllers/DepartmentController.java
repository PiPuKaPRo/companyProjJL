package edu.vsu.sakovea.controllers;

import edu.vsu.sakovea.infra.beans.factory.annotation.EvgAutowired;
import edu.vsu.sakovea.infra.beans.factory.stereotype.EvgComponent;
import edu.vsu.sakovea.model.Department;
import edu.vsu.sakovea.repository.DbDepartmentRepository;
import edu.vsu.sakovea.repository.DepartmentRepository;
import edu.vsu.sakovea.repository.MemoryDepartmentRepository;
import edu.vsu.sakovea.service.CompanyService;

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
    private DbDepartmentRepository dbDepartmentRepository;

    public void setDepartmentName(Department departmentName) {
        this.departmentName = departmentName;
    }

    public void setCompanyService(CompanyService companyService) {
        this.companyService = companyService;
    }

    public void setDbDepartmentRepository(DbDepartmentRepository dbDepartmentRepository) {
        this.dbDepartmentRepository = dbDepartmentRepository;
    }

    public void createDepartment(){
        System.out.println("Введите название отдела:");
        departmentName.setName(scanner.nextLine());
        Department department = new Department(departmentName.getName());
        companyService.addDepartment(department);
    }

    public void deleteDepartment(){
        System.out.println("Введите название отдела для удаления:");
        int id = scanner.nextInt();
        Department departmentToRemove = new Department(id);
        companyService.deleteDepartment(departmentToRemove);
    }
    public void showAllDepartment(){
        List<Department> allDepartments = dbDepartmentRepository.getAllDepartments();
        System.out.println("Список отделов:");
        for (Department dep : allDepartments) {
            System.out.println("Название: " + dep.getName());
        }
    }
}
