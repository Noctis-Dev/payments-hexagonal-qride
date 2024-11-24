package com.qride.payments.utils;

public class ThreadsUtil {

    public static void runTask(Runnable task) {
        new Thread(task).start();
    }

}
