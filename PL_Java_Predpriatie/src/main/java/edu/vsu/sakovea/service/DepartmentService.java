package main.java.edu.vsu.sakovea.service;

import main.java.edu.vsu.sakovea.infra.beans.factory.annotation.EvgAutowired;
import main.java.edu.vsu.sakovea.infra.beans.factory.stereotype.EvgService;
import main.java.edu.vsu.sakovea.model.Department;
import main.java.edu.vsu.sakovea.repository.DbDepartmentRepository;
import main.java.edu.vsu.sakovea.repository.DepartmentRepository;
import main.java.edu.vsu.sakovea.repository.MemoryDepartmentRepository;

import java.util.List;

@EvgService
public class DepartmentService {
    @EvgAutowired
    private DbDepartmentRepository dbDepartmentRepository;

    public void setDbDepartmentRepository(DbDepartmentRepository dbDepartmentRepository) {
        this.dbDepartmentRepository = dbDepartmentRepository;
    }

    public void addDepartment(Department department) {
        dbDepartmentRepository.addDepartment(department);
    }

    public void removeDepartment(Department department) {
        dbDepartmentRepository.deleteDepartment(department);
    }

    public List<Department> getAllDepartments() {
        return dbDepartmentRepository.getAllDepartments();
    }
}
