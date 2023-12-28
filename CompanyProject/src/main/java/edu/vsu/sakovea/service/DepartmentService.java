package edu.vsu.sakovea.service;

import edu.vsu.sakovea.infra.beans.factory.annotation.EvgAutowired;
import edu.vsu.sakovea.infra.beans.factory.stereotype.EvgService;
import edu.vsu.sakovea.model.Department;
import edu.vsu.sakovea.repository.DbDepartmentRepository;
import edu.vsu.sakovea.repository.DepartmentRepository;
import edu.vsu.sakovea.repository.MemoryDepartmentRepository;

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
