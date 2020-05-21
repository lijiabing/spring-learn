package com.drips.base.ch3;

import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Component;

/**
 * @author lijb
 */
@Component("abstractLookupBean")
public abstract class AbstractLookUpDemoBean implements DemoBean {

    @Lookup("singer")
    public Singer getMySinger() {
        return null;
    }

    @Override
    public void toDo() {
        getMySinger().sing();
    }
}
