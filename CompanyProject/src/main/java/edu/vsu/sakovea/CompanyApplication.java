package edu.vsu.sakovea;

import edu.vsu.sakovea.controllers.MainController;
import edu.vsu.sakovea.infra.beans.factory.BeanFactory;
import edu.vsu.sakovea.infra.context.ApplicationContext;

public class CompanyApplication {
    public static void main(String[] args) throws ReflectiveOperationException {
        ApplicationContext applicationContext = new ApplicationContext(CompanyApplication.class.getPackageName());

        System.out.println(applicationContext);
        System.out.println(applicationContext.getBeanFactory().getSingletons());

        MainController mainController = (MainController) (applicationContext.getBeanFactory().getBean("mainController"));
        mainController.runApp();
    }
}

