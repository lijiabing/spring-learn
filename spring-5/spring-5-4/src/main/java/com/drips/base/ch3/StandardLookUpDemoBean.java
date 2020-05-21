package com.drips.base.ch3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * @author lijb
 */
@Component("standardLookupBean")
public class StandardLookUpDemoBean implements DemoBean {

    private Singer mySinger;

    @Autowired
    @Qualifier("singer")
    public void setMySinger(Singer mySinger) {
        this.mySinger = mySinger;
    }

    @Override
    public Singer getMySinger() {
        return this.mySinger;
    }

    @Override
    public void toDo() {
        getMySinger().sing();
    }
}
