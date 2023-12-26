package edu.vsu.sakovea.model;

import lombok.Getter;
import lombok.Setter;
import edu.vsu.sakovea.infra.beans.factory.stereotype.EvgComponent;
import edu.vsu.sakovea.infra.orm.annotations.Entity;
import edu.vsu.sakovea.infra.orm.annotations.Id;
import edu.vsu.sakovea.infra.orm.annotations.OneToMany;
import edu.vsu.sakovea.infra.orm.annotations.Table;

import java.util.ArrayList;
import java.util.List;
@EvgComponent
@Entity
@Table(name = "departments")
public class Department {

    @Id
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;

    public Department(String name) {
        this.name = name;
    }
    public Department(int id){
        this.id = id;
    }
    public  Department(){}
}
