package com.company;

public class Worker extends Thread {

    public Worker(String name) {
        super(name);
    }

    @Override
    public void run() {
       super.run();
       //Util.logThreadInfo(Thread.currentThread());
    }

}
