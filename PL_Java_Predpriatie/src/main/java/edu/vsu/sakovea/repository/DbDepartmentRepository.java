package main.java.edu.vsu.sakovea.repository;

import lombok.Data;
import main.java.edu.vsu.sakovea.infra.beans.factory.stereotype.EvgComponent;
import main.java.edu.vsu.sakovea.infra.orm.DatabaseConnector;
import main.java.edu.vsu.sakovea.infra.orm.EntityManager;
import main.java.edu.vsu.sakovea.model.Department;

import java.util.List;

@EvgComponent
public class DbDepartmentRepository  implements DepartmentRepository{

    @Override
    public void addDepartment(Department department) {
        EntityManager.save(department, DatabaseConnector.getConnection());
    }

    @Override
    public void deleteDepartment(Department department) {
        EntityManager.delete(department, DatabaseConnector.getConnection());
    }

    @Override
    public List<Department> getAllDepartments() {
        List<Department> departments = EntityManager.findAll(Department.class, DatabaseConnector.getConnection());
        return departments;
    }

    @Override
    public Department getDepartmentByName(String name) {
        return null;
    }

    @Override
    public Department getDepartmentById(int id) {
        Department department = EntityManager.findById(Department.class, id, DatabaseConnector.getConnection());
        return department;
    }
}
