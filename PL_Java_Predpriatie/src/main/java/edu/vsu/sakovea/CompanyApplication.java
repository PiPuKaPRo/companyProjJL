package main.java.edu.vsu.sakovea;

import main.java.edu.vsu.sakovea.controllers.MainController;
import main.java.edu.vsu.sakovea.infra.beans.factory.BeanFactory;

public class CompanyApplication {
    public static void main(String[] args) {
        BeanFactory componentFactory = new BeanFactory();
        componentFactory.;

        MainController mainController = (MainController) (componentFactory.getBean("edu.vsu.sakovea.controllers.MainController"));
        mainController.runApp();
    }
}


