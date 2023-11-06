package main.java.edu.vsu.sakovea.infra.beans.factory;


import main.java.edu.vsu.sakovea.infra.beans.factory.annotation.EvgAutowired;
import main.java.edu.vsu.sakovea.infra.beans.factory.config.BeanPostProcessor;
import main.java.edu.vsu.sakovea.infra.beans.factory.stereotype.EvgComponent;
import main.java.edu.vsu.sakovea.infra.beans.factory.stereotype.EvgService;

import javax.annotation.PreDestroy;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;

public class BeanFactory {
    private Map<String, Object> singletons = new HashMap();

    public Object getBean(String beanName) {
        return singletons.get(beanName);
    }

    private List<BeanPostProcessor> postProcessors = new ArrayList<>();

    public void addPostProcessor(BeanPostProcessor postProcessor) {
        postProcessors.add(postProcessor);
    }

    public Map<String, Object> getSingletons() {
        return singletons;
    }

    public void instantiate(String basePackage) {
        String path = basePackage.replace('.', '/');
        scan(new File(getClass().getClassLoader().getResource(path).getFile()), basePackage);
    }

    private void scan(File directory, String basePackage) {
        File[] files = directory.listFiles();
        if (files == null) {
            return;
        }

        for (File file : files) {
            if (file.isDirectory()) {
                scan(file, basePackage + "." + file.getName());
            } else if (file.getName().endsWith(".class")) {
                String className = file.getName().substring(0, file.getName().length() - 6);

                try {
                    Class<?> classObject = Class.forName(basePackage + "." + className);

                    if (classObject.isAnnotationPresent(EvgComponent.class) || classObject.isAnnotationPresent(EvgService.class)) {
                        Object instance = classObject.newInstance();
                        String beanName = className.substring(0, 1).toLowerCase() + className.substring(1);
                        singletons.put(beanName, instance);
                    }
                } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void populateProperties() throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        System.out.println("==populateProperties==");

        for (Object object : singletons.values()) {
            for (Field field : object.getClass().getDeclaredFields()) {
                if (field.isAnnotationPresent(EvgAutowired.class)) {

                    for (Object dependency : singletons.values()) {
                        if (dependency.getClass().equals(field.getType())) {
                            String setterName = "set" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
                            System.out.println("Setter name = " + setterName);
                            Method setter = object.getClass().getDeclaredMethod(setterName, dependency.getClass());
                            setter.invoke(object, dependency);
                        }
                    }
                }
            }
        }
    }

    public void injectBeanNames() {
        for (String name : singletons.keySet()) {
            Object bean = singletons.get(name);

            if (bean instanceof BeanNameAware) {
                ((BeanNameAware) bean).setBeanName(name);
            }
        }
    }

    public void initializeBeans() {
        for (String name : singletons.keySet()) {
            Object bean = singletons.get(name);

            for (BeanPostProcessor postProcessor : postProcessors) {
                postProcessor.postProcessBeforeInitialization(bean, name);
            }

            if (bean instanceof InitializingBean) {
                ((InitializingBean) bean).afterPropertiesSet();
            }

            for (BeanPostProcessor postProcessor : postProcessors) {
                postProcessor.postProcessAfterInitialization(bean, name);
            }
        }
    }

    public void close() {
        for (Object bean : singletons.values()) {
            for (Method method : bean.getClass().getMethods()) {
                if (method.isAnnotationPresent(PreDestroy.class)) {
                    try {
                        method.invoke(bean);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }

            if (bean instanceof DisposableBean) {
                ((DisposableBean) bean).destroy();
            }
        }
    }
}
