package com.drips.base.ch3;

import org.springframework.context.annotation.Lazy;
import org.springframework.context.support.GenericXmlApplicationContext;

/**
 * @author lijb
 */
public class Target {
    private Foo fooOne;
    private Foo fooTwo;
    private Bar bar;

    public Target() {
    }

    public Target(Foo foo) {
        System.out.println("target foo call");
    }

    public Target(Foo foo, Bar bar) {
        System.out.println("target foo and bar call");
    }

    public void setFooOne(Foo fooOne) {
        this.fooOne = fooOne;
        System.out.println("target fooOne set");
    }

    public void setFooTwo(Foo foo) {
        this.fooTwo = foo;
        System.out.println("target fooTwo set");
    }

    public void setBar(Bar bar) {
        this.bar = bar;
        System.out.println("target Bar set");
    }

    public static void main(String[] args) {
        GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
        ctx.load("classpath:spring/app-context-03.xml");
        ctx.refresh();
        Target t = null;

        System.out.println("byName:");
        t = (Target) ctx.getBean("targetByName");

        System.out.println("byType:");
        t = (Target) ctx.getBean("targetByType");
        System.out.println("byConstructor:");
        t = (Target) ctx.getBean("targetConstructor");


        ctx.close();
    }
}
