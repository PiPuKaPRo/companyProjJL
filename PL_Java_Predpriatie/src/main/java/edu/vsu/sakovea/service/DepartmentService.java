package main.java.edu.vsu.sakovea.service;

import main.java.edu.vsu.sakovea.model.Department;
import main.java.edu.vsu.sakovea.repository.DepartmentRepository;

import java.util.List;

public class DepartmentService {
    private DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public void addDepartment(Department department) {
        departmentRepository.addDepartment(department);
    }

    public void removeDepartment(Department department) {
        departmentRepository.deleteDepartment(department);
    }

    public List<Department> getAllDepartments() {
        return departmentRepository.getAllDepartments();
    }
}
