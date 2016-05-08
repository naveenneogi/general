package com.ejenta;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by naveenmurthy on 5/6/16.
 */
public class EjentaPartyHelper {
   public static void loadContactsListFromStdin() {

   }

   // get contacts list from a hardcoded test set
   public static ContactsDump loadContactsListFromStatic() {

      ContactsDump contactsList = new ContactsDump();
      contactsList.put(1, new HashSet<Integer>(Arrays.asList(3,5,7)));
      contactsList.put(2, new HashSet<Integer>(Arrays.asList(4,6,8,10)));
      contactsList.put(3, new HashSet<Integer>(Arrays.asList(4,5)));
      //System.out.println(mapOflistOfContacts);
      return contactsList;
   }

   private static String getHashKeyGivenTwoContacts(Integer p, Integer contact) {
      return (p<=contact)? p.toString()+"-"+contact.toString(): contact.toString()+"-"+p.toString();
   }


}
