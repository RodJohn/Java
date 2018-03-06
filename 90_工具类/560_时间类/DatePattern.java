package com.john.util.date;


public enum DatePattern {

    DateTime("yyyy-MM-dd HH:mm:ss:SSS"),
    Date("yyyy-MM-dd");

    public String pattern;

    DatePattern(String pattern){
        this.pattern = pattern;
    }



}
