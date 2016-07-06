package com.company;

/**
 * Created by naveenmurthy on 6/11/16.
 */
public class Temp {

    static void StairCase(int n) throws Exception {

        //validate n for (1..100), throw
        if (n<1 || n>100) {
            throw new Exception("please enter a valid n, 1<n<100");
        }

        //got a valid n, proceed to print
        // line counter
        for(int i=1; i<=n; i++) {
            StringBuilder str = new StringBuilder(getSpaces(n-i)).append(getHashes(i));
            System.out.println(str);
        }

    }

    private static String getSpaces(int num) {

        int i = 0;
        StringBuilder str = new StringBuilder();
        while (i++ < num) {
            str.append(" ");
        }
        return str.toString();
    }

    private static String getHashes(int num) {
        int i = 0;
        StringBuilder str = new StringBuilder();
        while (i++ < num) {
            str.append("#");
        }
        return str.toString();
    }

    static int sum(int[] numbers) {

        int sum = 0;
        for (int i = 0; i < numbers.length; i++) {
            sum += numbers[i];
        }
        return sum;
    }

    static String toBitString(int n) {
        StringBuilder str = new StringBuilder();
        for (int i=31; i>=0; --i) {
            int v = (1<<i) & n;
            str.append(v!=0 ? "1":"0");
        }
        return str.toString();
    }

}