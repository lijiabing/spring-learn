package com.drips.base.ch3.annotation;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author lijb
 */
public class DemoSpringAnnotated {
    public static void main(String[] args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(DemoConfig.class);
        Reader reader = ctx.getBean("reader", Reader.class);
        reader.say();
    }
}
