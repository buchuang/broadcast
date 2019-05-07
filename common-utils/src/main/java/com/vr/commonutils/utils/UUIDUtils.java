package com.vr.commonutils.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

public class UUIDUtils {

    private final static char[] number={'1','2','3','4','5','6','7','8','9','0'};
    public static String randomFileName(){
        StringBuilder builder=new StringBuilder();
        for (int i = 0; i <5 ; i++) {
            int num = new Random().nextInt(10);
            builder.append(number[num]);
        }
        return new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date())+builder.toString();
    }
    public static String randomUUID(){
        return UUID.randomUUID().toString().replace("-","");
    }

    public static void main(String[] args) {
        System.out.println(UUIDUtils.randomFileName());
        //System.out.println(new Random().nextInt(10)+1);
    }
}
