package com.drips.base.ch4;

import java.security.MessageDigest;

/**
 * @author lijb
 */
public class MessageDigester {

    private MessageDigest messageDigest1;
    private MessageDigest messageDigest2;

    public void setMessageDigest1(MessageDigest messageDigest1) {
        this.messageDigest1 = messageDigest1;
    }

    public void setMessageDigest2(MessageDigest messageDigest2) {
        this.messageDigest2 = messageDigest2;
    }

    public void digest(String msg) {
        System.out.println("using messageDigest1");
        digest(msg, messageDigest1);
        System.out.println("using messageDigest2");
        digest(msg, messageDigest2);
    }

    private void digest(String msg, MessageDigest messageDigest) {
        System.out.println("Using algorithm: " + messageDigest.getAlgorithm());
        messageDigest.reset();
        byte[] bytes = msg.getBytes();
        byte[] out = messageDigest.digest(bytes);
        System.out.println(out);
    }
}
