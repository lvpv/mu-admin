package com.lvpb.mu.utils;

/**
 * @author lvpb
 * @version 1.0.0
 * @date 2024/4/3 20:03
 * @description RuntimeTest
 */
public class RuntimeTest {

    public static void main(String[] args) {
        int processors = Runtime.getRuntime().availableProcessors();
        System.out.println("processors = " + processors);
    }
}
