package main.java.edu.vsu.sakovea.repository;

import main.java.edu.vsu.sakovea.model.Department;

import java.util.List;

public interface DepartmentRepository {

    void addDepartment(Department department);
    void deleteDepartment(Department department);
    List<Department> getAllDepartments();

    Department getDepartmentByName(String name);
}
