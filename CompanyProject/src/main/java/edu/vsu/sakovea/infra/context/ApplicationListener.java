package edu.vsu.sakovea.infra.context;

public interface ApplicationListener<E>{
    void onApplicationEvent(E event);
}