package main.java.edu.vsu.sakovea.model;

import main.java.edu.vsu.sakovea.controllers.DepartmentController;
import main.java.edu.vsu.sakovea.infra.beans.factory.stereotype.EvgComponent;

@EvgComponent
public class Department {
    private String name;
    private int numberOfEmployees;

    public Department(String name) {
        this.name = name;
        this.numberOfEmployees = 0;
    }
    public Department(){}

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getNumberOfEmployees() {
        return numberOfEmployees;
    }

    public void increaseEmployeeCount() {
        numberOfEmployees++;
    }

    public void decreaseEmployeeCount() {
        numberOfEmployees--;
    }
}
