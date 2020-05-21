package com.drips.base.ch3;

import com.drips.base.ch3.util.CommonUtils;
import org.springframework.beans.factory.support.MethodReplacer;
import org.springframework.context.support.GenericXmlApplicationContext;

/**
 * @author lijb
 */
public class LookupDemo {

    public static void main(String[] args) {
        GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
        ctx.load("classpath:spring/app-context-xml.xml");
        ctx.refresh();
        DemoBean abstractBean = ctx.getBean("abstractLookupBean", DemoBean.class);
        DemoBean standardBean = ctx.getBean("standardLookupBean", DemoBean.class);

        CommonUtils.displayInfo("abstractLookupBean", abstractBean);
        CommonUtils.displayInfo("standardLookupBean", standardBean);
        ctx.close();
    }


}
