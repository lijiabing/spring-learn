package com.drips.base.ch3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.*;
import org.springframework.context.support.GenericApplicationContext;

/**
 * @author lijb
 */
public class SayDemo {

    @Configuration
    @ComponentScan(basePackages = {"com.drips.base.ch3"})
    public static class LookupConfig {
        @Bean(name = {"aSay", "bSay"})
        @Scope("prototype")
        public Say aSay() {
            return new ASay();
        }

//        @Bean(value = "bSay")
//        public Say bSay(){
//            return new BSay();
//        }
    }

    public static void main(String[] args) {
        GenericApplicationContext ctx = new AnnotationConfigApplicationContext(LookupConfigDemo.LookupConfig.class);
        Say bSay = ctx.getBean("bSay", Say.class);
        Say aSay = ctx.getBean("aSay", Say.class);
//        bSay.say();

        System.out.println(bSay == aSay);
        System.out.println(bSay.equals(aSay));
        Object obj1 = new Object();
        Object obj2 = new Object();
        System.out.println(obj1 == obj2);
        System.out.println(obj1.equals(obj2));

        ctx.close();
    }
}
