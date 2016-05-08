package com.company;

/**
 * Created by naveenmurthy on 4/27/16.
 */
public class Util {

   public static String getThreadInfo(Thread thread) {
      StringBuffer str = new StringBuffer("Thread: name->" + thread.getName() + ", id->" + thread.getId() + ", state->" + thread.getState());
      return str.toString();
   }

   public static void logThreadInfo(Thread thread) {
      System.out.println(getThreadInfo(thread));
   }
}
