package com.example.cardealership;

import javafx.util.Duration;

public class Test {
    public static void main(String[] args) {
        System.out.println("Hello World!");
        try {
            Duration delay = Duration.seconds(5);       // 5 seconds delay
            Thread.sleep((long) delay.toMillis());
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Goodbye World!");
    }
}
