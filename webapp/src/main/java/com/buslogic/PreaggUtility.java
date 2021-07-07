package com.buslogic;

import com.google.common.base.Splitter;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by naveenmurthy
 */
public class PreaggUtility {

    // Singleton
    private static PreaggUtility instance;
    private PreaggUtility() {}

    public static PreaggUtility getInstance() {
        if(instance == null) {
            synchronized (PreaggUtility.class) {
                instance = new PreaggUtility();
            }
        }
        return instance;
    }

    public void init(String filename) {
        processItemsDataFromFile(filename);
        //System.out.println(itemCountryPricingMap);
    }

    public String getItemCountryPricing(String item) {
        if (StringUtils.isEmpty(item) || !validItemCode(item)) {
            String str = "ERROR: Invalid item passed: " + item;
            System.out.println(str);
            return str;
        }

        if (itemCountryPricingMap.get(StringUtils.lowerCase(item)) != null) {
            String countryPricingInfo = itemCountryPricingMap.get(item).toString();
            return countryPricingInfo;
        } else {
            String str = "ERROR: countryPricingInfo NOT FOUND for the item passed: " + item;
            System.out.println(str);
            return str;
        }


    }

    private class ItemCountryPricing {
        String item;
        String country;
        Float price;

        public ItemCountryPricing(String item, String country, Float price) {
            this.item = item;
            this.country = country;
            this.price = price;
        }

        public Float getPrice() {
            return price;
        }

        @Override
        public String toString() {
            return "CountryPricing{" +
                    "item='" + item + '\'' +
                    ", country='" + country + '\'' +
                    ", price=" + price +
                    '}';
        }
    }

    private ConcurrentHashMap<String, TreeSet<ItemCountryPricing>> itemCountryPricingMap = new ConcurrentHashMap<>();

    private void processItemsDataFromFile(String filename) {
        try {
            LineIterator it = FileUtils.lineIterator(new File(filename), "UTF-8");
            while (it.hasNext()) {
                String line = it.nextLine();
                processLineItem(line);
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            System.out.println("FileNotFoundException");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("IOException");
            e.printStackTrace();
        } finally {
            // TODO: cleanup before exit !!

            // LineIterator.closeQuietly(it);
        }
    }

    private void processLineItem(String lineItem) {
        try {

            if (StringUtils.isEmpty(lineItem)) return;

            //System.out.println(lineItem);

            // csv split and validate
            List<String> itemCountryPricingList = Splitter.on(",").omitEmptyStrings().splitToList(lineItem);
            // Its probably safe to assume that the CSV file does contain all valid entries
            // since this was validated & generated by another microservice, However...
            // ERROR HANDLING: validate for only 3 items, validate each item
            if (itemCountryPricingList.size() != 3) {
                System.out.println("ERROR: Expecting exactly 3 items while parsing the lineItem: " + lineItem);
                return;
            }
            if (!validCountryCode(itemCountryPricingList.get(0))) {
                System.out.println("ERROR: validCountryCode failed while parsing the lineItem: " + lineItem);
                return;
            }
            if (!validItemCode(itemCountryPricingList.get(1))) {
                System.out.println("ERROR: validItemCode failed while parsing the lineItem: " + lineItem);
                return;
            }
            if (!NumberUtils.isParsable(itemCountryPricingList.get(2))) {
                System.out.println("ERROR: valid Pricing failed while parsing the lineItem: " + lineItem);
                return;
            }

            updateMapWithItemInfo(itemCountryPricingList);
        } catch (Exception ex) {

        } finally {

        }
    }

    private void updateMapWithItemInfo(List<String> itemCountryPricingList) {
        //System.out.println(itemCountryPricingList);
        String country = itemCountryPricingList.get(0);
        String item = StringUtils.lowerCase(itemCountryPricingList.get(1));
        Float price = Float.valueOf(itemCountryPricingList.get(2));

        if (itemCountryPricingMap.get(item) != null) {
            // append the countryPricing into the sortedTree
            itemCountryPricingMap.get(item).add(new ItemCountryPricing(item, country, price));
        } else {
            // add the countryPricing into the sortedTree
            TreeSet<ItemCountryPricing> treeSet1 = new TreeSet<>((o1, o2) -> {
                // descending order
                return Float.compare(o2.price, o1.price);
            });

            TreeSet<ItemCountryPricing> treeSet = new TreeSet<>(
                    Comparator.comparing(
                            ItemCountryPricing::getPrice,
                            (o1, o2) -> Float.compare(o2, o1) // needed for descending order, else can be omitted
                    ).thenComparing(ItemCountryPricing::toString) // needed to preserve objects that end up having the same price
            );

            treeSet.add(new ItemCountryPricing(item, country, price));
            itemCountryPricingMap.put(item, treeSet);
        }
    }

    private boolean validItemCode(String s) {
        // TODO: validate item codes
        return true;
    }

    private boolean validCountryCode(String s) {
        // TODO: validate country codes
        return true;
    }


}