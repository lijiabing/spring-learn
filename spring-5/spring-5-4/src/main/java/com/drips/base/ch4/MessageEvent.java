package com.drips.base.ch4;

import org.springframework.context.ApplicationEvent;

/**
 * @author lijb
 */
public class MessageEvent extends ApplicationEvent {

    private String msg;

    /**
     * Create a new {@code ApplicationEvent}.
     *
     * @param source the object on which the event initially occurred or with
     *               which the event is associated (never {@code null})
     */
    public MessageEvent(Object source, String msg) {
        super(source);
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
