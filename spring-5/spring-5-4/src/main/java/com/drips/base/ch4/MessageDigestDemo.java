package com.drips.base.ch4;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.context.support.ResourceBundleMessageSource;

/**
 * @author lijb
 */
public class MessageDigestDemo {

    public static void main(String[] args) {
//        GenericXmlApplicationContext ctx=new GenericXmlApplicationContext();
//        ctx.load("classpath:spring/app-context-04.xml");
//        ctx.refresh();
//        MessageDigester digester=ctx.getBean("digester",MessageDigester.class);
//        digester.digest("Hello World !");
//        ctx.close();

        GenericApplicationContext ctx = new AnnotationConfigApplicationContext(MessageDigesterConfig.class);

        MessageDigester messageDigester = (MessageDigester) ctx.getBean("messageDigester");
        messageDigester.digest("Hello World !");
        ctx.close();


    }

    @Configuration
    static class MessageDigesterConfig {

        @Bean
        public MessageDigestFactoryBean shaDigest() {
            MessageDigestFactoryBean factoryBean = new MessageDigestFactoryBean();
            factoryBean.setAlgorithmName("SHA1");
            return factoryBean;
        }

        @Bean
        public MessageDigestFactoryBean defaultDigest() {
            return new MessageDigestFactoryBean();
        }

        @Bean
        public MessageDigester messageDigester() throws Exception {
            MessageDigester messageDigester = new MessageDigester();
            messageDigester.setMessageDigest1(shaDigest().getObject());
            messageDigester.setMessageDigest2(defaultDigest().getObject());
            return messageDigester;
        }


    }
}
