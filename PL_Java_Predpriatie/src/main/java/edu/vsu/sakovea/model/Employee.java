package main.java.edu.vsu.sakovea.model;

import lombok.Data;
import main.java.edu.vsu.sakovea.infra.beans.factory.stereotype.EvgComponent;
import main.java.edu.vsu.sakovea.infra.orm.annotations.Entity;
import main.java.edu.vsu.sakovea.infra.orm.annotations.Id;
import main.java.edu.vsu.sakovea.infra.orm.annotations.Table;

@Data
@EvgComponent
@Entity
@Table(name = "department")
public class Employee {
    @Id
    private int id;

    private String fullName;

    private int age;

    private double salary;

    private String departmentName;

    public Employee(String fullName, int age, double salary) {
        this.fullName = fullName;
        this.age = age;
        this.salary = salary;
    }

    public Employee(){}

}
