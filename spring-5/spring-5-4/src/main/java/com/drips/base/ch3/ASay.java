package com.drips.base.ch3;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Service;

/**
 * @author lijb
 */
//@Service("aSay")

public class ASay implements Say {
    @Override
    public void say() {
        System.out.println("A");
    }
}
