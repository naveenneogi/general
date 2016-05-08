package com.company;

/**
 * Created by naveenmurthy on 4/27/16.
 */
public class WorkerIf implements Runnable {

   @Override
   public void run() {
      Util.logThreadInfo(Thread.currentThread());

      try {
         while (!Thread.interrupted()) {
            //System.out.print('.');
         }
         //TODO: adding a throw here since the compiler is complaining that try block has to throw, validate later!!!
         throw new InterruptedException();
      } catch (InterruptedException e) {
         System.out.println(Util.getThreadInfo(Thread.currentThread()) + " interrupted, exiting...");

      }
   }
}
