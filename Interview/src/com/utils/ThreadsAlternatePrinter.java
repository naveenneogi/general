package com.utils;

/**
 * Created by naveenmurthy
 */
public class ThreadsAlternatePrinter {
    int nextToPrint = 1;
    int maxToPrint = 10;

    public ThreadsAlternatePrinter() {
    }

    public ThreadsAlternatePrinter(int nextToPrint, int maxToPrint) {
        this.nextToPrint = nextToPrint;
        this.maxToPrint = maxToPrint;
    }

    public void printOdd() {
        synchronized (this) {
            while (nextToPrint <= maxToPrint) {
                // while its not your turn yet, wait
                while (nextToPrint % 2 == 0) {
                    try {
                        this.wait();
                    } catch (InterruptedException exception) {
                        exception.printStackTrace();
                    }
                }
                System.out.println(Thread.currentThread().getName() + " -> " + nextToPrint);
                nextToPrint += 1;
                this.notify();
            }
        }
    }

    public void printEven() {
        synchronized (this) {
            while (nextToPrint <= maxToPrint) {
                // while its not your turn yet, wait
                while (nextToPrint % 2 == 1) {
                    try {
                        this.wait();
                    } catch (InterruptedException exception) {
                        exception.printStackTrace();
                    }
                }
                System.out.println(Thread.currentThread().getName() + " -> " + nextToPrint);
                nextToPrint += 1;
                this.notify();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {

        ThreadsAlternatePrinter printer = new ThreadsAlternatePrinter(1, 10);

        Thread oddPrinter = new Thread(printer::printOdd, "oddPrinter");
        Thread evenPrinter = new Thread(printer::printEven, "evenPrinter");

        oddPrinter.start();
        evenPrinter.start();
    }

}