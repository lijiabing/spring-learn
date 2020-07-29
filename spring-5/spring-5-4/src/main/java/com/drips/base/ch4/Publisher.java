package com.drips.base.ch4;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lijb
 */
public class Publisher implements ApplicationContextAware {
    private ApplicationContext ctx;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.ctx = applicationContext;
    }

    public void publish(String message) {
        ctx.publishEvent(new MessageEvent(this, message));
    }

    public static void main(String[] args) {
//        ApplicationContext ctx=new ClassPathXmlApplicationContext("classpath:spring/app-context-04.xml");
//        Publisher push=(Publisher)ctx.getBean("publisher");
//        push.publish("T send");
//        push.publish("----------------");
//        push.publish("!!!!!!!!!!!!!!");
        List<String> s = new ArrayList<>();
        s.add("A");
        s.add("B");
        String[] arr = s.toArray(new String[]{});
        System.out.println(StringUtils.arrayToDelimitedString(arr, ","));

    }
}
