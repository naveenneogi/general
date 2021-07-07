package com.buslogic;

public class ControllerUtility {

    public static void main(String[] args) throws InterruptedException {
        String filename = "/tmp/itemCountryPricing.csv";

        PreaggUtility preaggUtility = PreaggUtility.getInstance();
        preaggUtility.init(filename);

        System.out.println("\n\n mango pricingInfo : " + preaggUtility.getItemCountryPricing("mango"));
        System.out.println("\n\n apple pricingInfo : " + preaggUtility.getItemCountryPricing("mango"));
        System.out.println("\n\n pear pricingInfo : " + preaggUtility.getItemCountryPricing("mango"));



    }

}
