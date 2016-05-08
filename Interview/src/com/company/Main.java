package com.company;

import java.util.Arrays;
import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by naveenmurthy on 4/27/16.
 */
public class Main {


   private static int maxThreads=10;

   public static void main(String[] args) throws InterruptedException {

      Util.logThreadInfo(Thread.currentThread());

      // get cost of items
      int cost = CostOfItems.getCostOfItems(Arrays.asList(3, 1, 4, 2, 8, 10, 20));
      System.out.println("CostOfItems: " + cost);

      // create thread through class
      Thread reader = new Worker("reader");
      reader.start();
      //Util.logThreadInfo(reader);

      // create thread through interface
      Thread writer = new Thread(new WorkerIf(), "writer");
      writer.start();
      //Util.logThreadInfo(writer);

      // main thread sleep for 3s, then interrupt the writer thread
      System.out.println(Util.getThreadInfo(Thread.currentThread()) + " sleeping for 3s...");
      Thread.sleep(1000);
      writer.interrupt();


      //ExecutorService execService = Executors.newFixedThreadPool(maxThreads);

      //assertEquals("naveen", "naeen");




   }

}