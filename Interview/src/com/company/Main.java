package com.company;

import java.util.Arrays;

/**
 * Created by naveenmurthy on 4/27/16.
 */
public class Main {


   private static int maxThreads=10;

   public static void main(String[] args) throws InterruptedException {

      Util.logThreadInfo(Thread.currentThread());


      try {
         Temp.StairCase(5);
      } catch (Exception e) {
         System.out.println("exception: " + e.getMessage());
      }

      try {
         int n = 170;
         System.out.println(Temp.toBitString(n));
         System.out.println(Integer.toBinaryString(n));

      } catch (Exception e) {
         System.out.println("exception: " + e.getMessage());
      }

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

      try {
         System.out.println("##############");
         HashMap<Integer, String> hashMap = new HashMap<Integer, String>();
         hashMap.put(10,"ten");
         hashMap.put(11,"eleven");
         hashMap.put(12,"twelve");

         hashMap.put(1007,"1007");
         hashMap.put(10,"10");
         System.out.println(hashMap.get(10));
      } catch (Exception e) {
         System.out.println("exception: " + e.getMessage());
      }

      try {
         System.out.println("##############");
         Tree<Integer> tree = new Tree<Integer>(15);
         tree.add(10);
         tree.add(20);
         tree.add(13);
         tree.add(25);
         tree.add(11);
         tree.printInOrder();
         //tree.mirror();
         System.out.println("---");
         //tree.printInOrder();
         tree.levelOrder();
         tree.columnOrderTraversal();
      } catch (Exception e) {
         System.out.println("exception: " + e.getMessage());
      }




   }

}