package main.java.edu.vsu.sakovea.model;

public class Department {
    private String name;
    private int numberOfEmployees;

    public Department(String name) {
        this.name = name;
        this.numberOfEmployees = 0;
    }

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
