package com.drips.base.ch3;

import org.springframework.stereotype.Service;

/**
 * @author lijb
 */
//@Service("bSay")
public class BSay implements Say {
    @Override
    public void say() {
        System.out.println("B");
    }
}
