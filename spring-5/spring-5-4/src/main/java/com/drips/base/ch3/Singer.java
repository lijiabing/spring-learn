package com.drips.base.ch3;

import org.springframework.context.annotation.Scope;
import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

/**
 * @author lijb
 */
@Component("singer")
@Scope("prototype")
public class Singer {
    private String lyric = "i am a singer!";

    private Singer() {
    }

    public void sing() {
//        System.out.println(lyric);
    }
}
