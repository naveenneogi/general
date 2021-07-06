package com.utils;

/**
 * Created by naveenmurthy
 */
public class TestingUtility {


   public static void main(String[] args) throws InterruptedException {


   }

    public static String findLargestBranchInBinaryTree(long[] arr) {
        // Type your solution here
        if(arr.length == 0) return "";
        long leftBranchSum = arr[0];
        long rightBranchSum = arr[0];
        for (int i = 1; i < arr.length; i++) {
            int parentIndex = findLeftOrRightBranchGivenNodeIndex(i);
            long val = arr[i];

            System.out.println("\n i = " + i + " --> parentIndex = " + parentIndex + " val = " + val);
            //System.out.println("\n BEFORE: leftBranchSum = " + leftBranchSum + " ; rightBranchSum = " + rightBranchSum);
            if (parentIndex == 0) continue;
            if (val == -1) continue;
            if (parentIndex == 1) leftBranchSum += val;
            if (parentIndex == 2) rightBranchSum += val;
            //System.out.println("\n AFTER: leftBranchSum = " + leftBranchSum + " ; rightBranchSum = " + rightBranchSum);
        }
        if (leftBranchSum > rightBranchSum) return "Left " + leftBranchSum;
        else if (rightBranchSum > leftBranchSum) return "Right " + rightBranchSum;
        else return "";
    }

    private static int findLeftOrRightBranchGivenNodeIndex(int index) {
        if(index == 0 || index == 1 || index == 2) return index;
        int node = index;
        // traverse up till the root's left or right node to figure the leftbranch or rightbranch
        // TODO: can this be a simple math function based on the index itself?
        while( (node = (node-1)/2) > 2 ) {
            //
        }
        return node; // 1 => left, 2 => right, 0 => root
    }



}