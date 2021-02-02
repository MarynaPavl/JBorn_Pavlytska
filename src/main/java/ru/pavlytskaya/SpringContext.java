package ru.pavlytskaya;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;



public class SpringContext {
    private static ApplicationContext context;
    public static ApplicationContext getContext(){
        if(context == null){
            try {
                Class.forName("org.postgresql.Driver");
            } catch (ClassNotFoundException ignored) {
            }
            context = new AnnotationConfigApplicationContext("ru.pavlytskaya");
        }
        return context;
    }

}
