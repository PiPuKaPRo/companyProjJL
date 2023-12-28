package edu.vsu.sakovea.repository;

import edu.vsu.sakovea.model.Department;

import java.util.List;

public interface DepartmentRepository {

    void addDepartment(Department department);
    void deleteDepartment(Department department);
    List<Department> getAllDepartments();

    Department getDepartmentByName(String name);

    Department getDepartmentById(int id);
}
