package com.drips.base.ch3;

import com.drips.base.ch3.util.CommonUtils;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.util.StopWatch;

/**
 * @author lijb
 */
public class LookupConfigDemo {

    @Configuration
    @ComponentScan(basePackages = {"com.drips.base.ch3"})
    public static class LookupConfig {
    }

    public static void main(String[] args) {
        GenericApplicationContext ctx = new AnnotationConfigApplicationContext(LookupConfig.class);
        DemoBean abstractBean = ctx.getBean("abstractLookupBean", DemoBean.class);
        DemoBean standardBean = ctx.getBean("standardLookupBean", DemoBean.class);

        CommonUtils.displayInfo("abstractLookupBean", abstractBean);
        CommonUtils.displayInfo("standardLookupBean", standardBean);
        ctx.close();

    }


}
