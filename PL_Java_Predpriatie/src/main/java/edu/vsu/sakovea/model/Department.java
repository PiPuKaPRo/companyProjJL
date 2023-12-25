package main.java.edu.vsu.sakovea.model;

import lombok.Data;
import main.java.edu.vsu.sakovea.controllers.DepartmentController;
import main.java.edu.vsu.sakovea.infra.beans.factory.stereotype.EvgComponent;
import main.java.edu.vsu.sakovea.infra.orm.annotations.Entity;
import main.java.edu.vsu.sakovea.infra.orm.annotations.Id;
import main.java.edu.vsu.sakovea.infra.orm.annotations.Table;

@Data
@EvgComponent
@Entity
@Table(name = "department")
public class Department {
    @Id
    private int id;

    private String name;

    private int numberOfEmployees;

    public Department(String name) {
        this.name = name;
        this.numberOfEmployees = 0;
    }
    public Department(){}
    public void increaseEmployeeCount() {
        numberOfEmployees++;
    }

    public void decreaseEmployeeCount() {
        numberOfEmployees--;
    }
}
