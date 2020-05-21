package com.drips.base.ch3.util;

import com.drips.base.ch3.DemoBean;
import com.drips.base.ch3.Singer;
import org.springframework.util.StopWatch;

/**
 * @author lijb
 */
public class CommonUtils {
    private CommonUtils() {
    }

    public static void displayInfo(String beanName, DemoBean bean) {
        Singer singer1 = bean.getMySinger();
        Singer singer2 = bean.getMySinger();

        System.out.println("" + beanName + ": Singer Instances the Same ? " + (singer1 == singer2) + " name: " + singer1.getClass().getCanonicalName() + ", " + singer2.getClass().getCanonicalName());

        StopWatch stopWatch = new StopWatch();

        stopWatch.start();

        for (int i = 0; i < 10000; i++) {
            Singer singer = bean.getMySinger();
            singer.sing();
        }

        stopWatch.stop();

        System.out.println("10000 gets took " + stopWatch.getTotalTimeMillis() + " ms");

    }
}
