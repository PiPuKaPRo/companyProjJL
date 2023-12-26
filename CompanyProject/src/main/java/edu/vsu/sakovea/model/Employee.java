package edu.vsu.sakovea.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import edu.vsu.sakovea.infra.beans.factory.stereotype.EvgComponent;
import edu.vsu.sakovea.infra.orm.annotations.*;
@EvgComponent
@Entity
@Table(name = "employees")
public class Employee {
    @Id
    private int id;
    private String full_name;
    private int age;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public int getDepartment_id() {
        return department_id;
    }

    public void setDepartment_id(int department_id) {
        this.department_id = department_id;
    }

    private double salary;
    private int department_id;


    public Employee(String fullName, int age, double salary) {
        this.full_name = fullName;
        this.age = age;
        this.salary = salary;
    }

    public Employee(){}

}
