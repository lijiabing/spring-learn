package com.drips.base.ch5;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * @author lijb
 */
public class AgentDecorator implements MethodInterceptor {
    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {

        System.out.println("JAVA");
        Object retVal = methodInvocation.proceed();
        System.out.println("!!!!");
        return retVal;
    }
}
