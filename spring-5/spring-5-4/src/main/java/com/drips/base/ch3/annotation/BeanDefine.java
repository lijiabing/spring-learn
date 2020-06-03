package com.drips.base.ch3.annotation;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author lijb
 */
@Configuration
public class BeanDefine {


    @Bean
    public Reader reader() {
        return new Reader();
    }
}
