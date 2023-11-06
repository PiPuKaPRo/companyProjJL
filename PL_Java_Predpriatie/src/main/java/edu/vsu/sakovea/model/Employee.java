package main.java.edu.vsu.sakovea.model;

import main.java.edu.vsu.sakovea.infra.beans.factory.stereotype.EvgComponent;

@EvgComponent
public class Employee {
    private String fullName;
    private int age;
    private double salary;
    private String departmentName;

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public Employee(String fullName, int age, double salary) {
        this.fullName = fullName;
        this.age = age;
        this.salary = salary;
    }

    public Employee(){}

    public String getFullName() {
        return fullName;
    }

    public int getAge() {
        return age;
    }

    public double getSalary() {
        return salary;
    }
}
