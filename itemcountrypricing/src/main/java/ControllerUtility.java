public class ControllerUtility {

    public static void main(String[] args) throws InterruptedException {
        String filename = "/Users/nmurthy/Projects/stash/general/itemcountrypricing/src/main/resources/items.csv";

        PreaggUtility preaggUtility = PreaggUtility.getInstance();
        preaggUtility.init(filename);

        System.out.println(" mango pricingInfo : " + preaggUtility.getItemCountryPricing("mango"));
        System.out.println(" apple pricingInfo : " + preaggUtility.getItemCountryPricing("mango"));
        System.out.println(" pear pricingInfo : " + preaggUtility.getItemCountryPricing("mango"));



    }

}
