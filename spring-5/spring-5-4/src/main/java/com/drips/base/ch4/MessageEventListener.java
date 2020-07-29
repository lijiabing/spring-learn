package com.drips.base.ch4;

import org.springframework.context.ApplicationListener;

/**
 * @author lijb
 */
public class MessageEventListener implements ApplicationListener<MessageEvent> {


    @Override
    public void onApplicationEvent(MessageEvent event) {
        System.out.println("Receive: " + event.getMsg());
    }
}
