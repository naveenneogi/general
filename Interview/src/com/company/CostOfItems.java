package com.company;

import java.util.Arrays;
import java.util.List;

/**
 * Created by naveenmurthy on 4/27/16.
 */
public class CostOfItems {

   public static int getCostOfItems(List list) {
      int sum = 0;
      try {
         List<Integer> myList = Arrays.asList(3, 1, 4, 2, 8, 10, 20);
         sum = 0;
         StringBuilder itemsThatCostUs = new StringBuilder("itemsThatCostUs: ");
         for (int x : myList) {
            if (x >= sum) {
               sum += x;
               //itemsThatCostUs.append(Integer.toString(x)).append(" ");
               itemsThatCostUs.append(x).append(" ");
               //System.out.println(x + " ");
            }
         }

         System.out.println(itemsThatCostUs);
      } catch (NullPointerException e) {
         System.out.println(e);
      } finally {

      }
      return sum;
   }



}
