package main.java.edu.vsu.sakovea.repository;

import main.java.edu.vsu.sakovea.infra.beans.factory.stereotype.EvgComponent;
import main.java.edu.vsu.sakovea.model.Department;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@EvgComponent
public class MemoryDepartmentRepository implements DepartmentRepository{
    private Map<String, Department> departmentStorage = new HashMap<>();

    @Override
    public void addDepartment(Department department) {
        departmentStorage.put(department.getName(), department);
    }

    @Override
    public void deleteDepartment(Department department) {
        departmentStorage.remove(department.getName());
    }

    @Override
    public List<Department> getAllDepartments() {
        return new ArrayList<>(departmentStorage.values());
    }

    @Override
    public Department getDepartmentByName(String name) {
        return departmentStorage.get(name);
    }

    @Override
    public Department getDepartmentById(int id) {
        return null;
    }
}
