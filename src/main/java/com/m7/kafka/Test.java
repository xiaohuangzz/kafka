package com.m7.kafka;

import java.time.Clock;
import java.util.Random;
import java.util.UUID;

public class Test {
    public static void main(String[] args) {
//        System.out.println(UUID.randomUUID().toString());
//        System.out.println(Clock.systemDefaultZone().millis());
        System.out.println(
                "@common,N00000003636,10018224," +
                        UUID.randomUUID().toString()+","+UUID.randomUUID().toString());
//        String source = "10018224";
//        Random r = new Random();
//        StringBuilder rs = new StringBuilder();
//        for (int i = 0; i< 8; i++) {
//            rs.append(source.charAt(r.nextInt(10)));
//        }
//        System.out.println(rs.toString());
    }
}
