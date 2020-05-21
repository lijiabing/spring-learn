package com.drips.java.lambda;

import java.util.function.BiFunction;
import java.util.function.Function;

public class FunctionDemo {
    public static void main(String[] args){

    }

    public Integer stepA(String p0){
        return p0==null?0:1;
    }

    public Integer stepB(String p0,Integer p1){

        return p1==0?p1:p0.length();
    }

    public Integer stepC(String p0,Integer p1){
       return p1==0?p1:p0.length();
    }


    public Integer step(String p0,Function<String,Integer> func, BiFunction<String,Integer,Integer> biFunc0,BiFunction<String,Integer,Integer> biFunc1){

        return biFunc1.apply(p0,biFunc0.apply(p0,func.apply(p0)));
    }




}
