package main.java.edu.vsu.sakovea.service;

import main.java.edu.vsu.sakovea.infra.beans.factory.annotation.EvgAutowired;
import main.java.edu.vsu.sakovea.infra.beans.factory.stereotype.EvgService;
import main.java.edu.vsu.sakovea.model.Department;
import main.java.edu.vsu.sakovea.repository.DepartmentRepository;
import main.java.edu.vsu.sakovea.repository.MemoryDepartmentRepository;

import java.util.List;

@EvgService
public class DepartmentService {
    @EvgAutowired
    private MemoryDepartmentRepository departmentRepository;

    public void setDepartmentRepository(MemoryDepartmentRepository departmentRepository) {
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
