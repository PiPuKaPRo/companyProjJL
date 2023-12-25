package main.java.edu.vsu.sakovea.model;

import main.java.edu.vsu.sakovea.infra.beans.factory.stereotype.EvgComponent;
import main.java.edu.vsu.sakovea.infra.orm.annotations.Entity;
import main.java.edu.vsu.sakovea.infra.orm.annotations.Id;
import main.java.edu.vsu.sakovea.infra.orm.annotations.OneToMany;
import main.java.edu.vsu.sakovea.infra.orm.annotations.Table;

import java.util.ArrayList;
import java.util.List;

@EvgComponent
@Entity
@Table(name = "departments")
public class Department {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    @Id
    private int id;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    private String name;


    @OneToMany(mappedBy = "employees")
    private List<Employee> employees = new ArrayList<>();

    public Department(String name) {
        this.name = name;
    }
    public Department(int id){
        this.id = id;
    }
    public  Department(){}
}
