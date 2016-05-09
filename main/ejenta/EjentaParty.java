package com.ejenta;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import org.jetbrains.annotations.Nullable;
import com.google.common.collect.Sets;
import java.util.*;

public class EjentaParty {

   // min number of contacts a person should know in the party
   private static Integer minKnownContacts;
   private static Integer minNumOFInvites;

   // map of person -> list of his contacts
   private static ContactsDump consolidatedContactsDump;
   //private static Multimap<Integer, Integer> multiContacts;
   private static Set<Integer> individualContactList;
   private static Set<Integer> partyContactList;

   public EjentaParty() {}

   public EjentaParty(Integer minContacts, ContactsDump contacts) {
      setMinKnownContacts(minContacts);
      setContactsDump(contacts);
   }

   public static void setMinKnownContacts(Integer minContacts) {
      minKnownContacts = minContacts;
      minNumOFInvites = minContacts+1;
   }

   public static void setContactsDump(ContactsDump contacts) {
      consolidatedContactsDump = processContactsList(contacts);
      individualContactList = consolidatedContactsDump.keySet();
   }

   private static void printDump() {
      System.out.println("minKnownContacts: " + minKnownContacts);
      System.out.println("minNumOFInvites: " + minNumOFInvites);
      System.out.println("contactsDump: " + consolidatedContactsDump);
      System.out.println("individualContactList : " + individualContactList);
      System.out.println("partyContactList: " + partyContactList);
   }

   @Nullable
   private static Set<Integer> generatePartyContactList(Set<Integer> potentialPartyList) {

      Set<Integer> partyList = new HashSet<>();
      try {

         // STOPPING COND 1: if the potential list of contacts is less than the minimum num of invites, return empty
         if(potentialPartyList.size() < minNumOFInvites) {
            return Collections.emptySet();
         }

         // foreach potential invite,
         //    check his contacts size
         //    then check if his contacts are in the potential list
         // Keep filtering one or more contacts out recursively
         // Recursion stops:
         //    when the final list it generates is the same as the input list it got
         //    Or if the list size is less than the min required
         boolean didWeDropAnyContact = false;
         for (Integer contact: potentialPartyList) {
            if(consolidatedContactsDump.get(contact).size() >= minKnownContacts &&
               Sets.intersection(consolidatedContactsDump.get(contact), potentialPartyList).size() >= minKnownContacts) {
               partyList.add(contact);
            } else {
               didWeDropAnyContact = true;
            }
         }

         // If we did drop one or more contacts, redo the party list by filtering one or more contacts out recursively
         // Recursion stops:
         //    when the final list it generates is the same as the input list it got
         //    Or if the list size is less than the min required
         //    this is the ultimate stopping condition if we indeed manage to find a working party list
         // NOTE: we could have also done the 'expensive' partyList.equals(potentialPartyList) instead of the bool flag
         //System.out.println(potentialPartyList); System.out.println(partyList);
         if(didWeDropAnyContact) {
            return generatePartyContactList(partyList);
         } else {
            return partyList;
         }
      } catch (Exception e) {
         System.out.println("Exception caught while generating party contact list: " + e.getMessage());
         return null;
      }
   }

   /*
   private static ContactsDump processContactsList(Multimap<Integer, Integer> contactsDump) {
      // load up a temp map and add missing connections by looking into each of the contacts connections
      Multimap<Integer, Integer> mapOflistOfContactsTemp = ArrayListMultimap.create();

      try {
         // look into peoples contacts and build a potential list of invites
         contactsDump.forEach((Integer contactA, HashSet<Integer> contactsList) -> {

            //System.out.println("processing: " + contactA + " -> " + contactsList);
            // for each of contactA's contactB,
            //    make a hashmap entry hash["A-B"]=1, to be used later
            //    also reverse map B to A: ie, in the map< B, add A to the list of contacts of B>
            for (Integer contactB : contactsList) {
               if (!mapOflistOfContactsTemp.containsKey(contactB)) {
                  //contactsOfB = new HashSet<Integer>();
                  mapOflistOfContactsTemp.put(contactB, new HashSet<>());
               }
               mapOflistOfContactsTemp.get(contactB).add(contactA);
            }
         });
      } catch (NullPointerException e) {
         //System.out.printf("Exception thrown while processing: " + p + " and its contact " + contact);
         System.out.printf("Exception thrown while processing: " + e.getMessage());

      } catch (Exception e) {
         System.out.println("Unknown exception thrown: " + e.getMessage());
      }

      return mapOflistOfContactsTemp;
   }
   */

   private static ContactsDump processContactsList(ContactsDump contactsDump) {

      // load up a temp map with the given map and add missing connections by looking into each of the contacts connections
      // NOTE: we could setup the temp map with contactsDump.clone() but deep copy is not guaranteed which may leave contactsDump
      //       to be affected here. To avoid this do not clone and construct the temp map inside the foreach
      ContactsDump consolidatedContactsDumpTemp = new ContactsDump();

      try {
         // look into peoples contacts and build a potential list of invites
         contactsDump.forEach((Integer contactA, HashSet<Integer> contactsList) -> {
            if(!consolidatedContactsDumpTemp.containsKey(contactA)) {
               consolidatedContactsDumpTemp.put(contactA, new HashSet<>());
            }
            consolidatedContactsDumpTemp.get(contactA).addAll(contactsList);

            //System.out.println("processing: " + contactA + " -> " + contactsList);
            // for each of contactA's contactB,
            //    make a hashmap entry hash["A-B"]=1, to be used later
            //    also reverse map B to A: ie, in the map< B, add A to the list of contacts of B>
            for (Integer contactB : contactsList) {
               if (!consolidatedContactsDumpTemp.containsKey(contactB)) {
                  //contactsOfB = new HashSet<Integer>();
                  consolidatedContactsDumpTemp.put(contactB, new HashSet<>());
               }
               consolidatedContactsDumpTemp.get(contactB).add(contactA);
            }
         });
      } catch (NullPointerException e) {
         //System.out.printf("Exception thrown while processing: " + p + " and its contact " + contact);
         System.out.printf("Exception thrown while processing: " + e.getMessage());
         java.lang.System.exit(0);

      } catch (Exception e) {
         System.out.println("Unknown exception thrown: " + e.getMessage());
         java.lang.System.exit(0);
      }

      return consolidatedContactsDumpTemp;
   }

   public Set<Integer> getPartyContactList () {
      partyContactList = generatePartyContactList(individualContactList);
      //printDump();
      return partyContactList;
   }

   public ContactsDump getContactsDump() {
      return consolidatedContactsDump;
   }

   public boolean doesContactAknowContactB(Integer A, Integer B) {
      try {
         return consolidatedContactsDump.get(A).contains(B);
      } catch (Exception e){
         return false;
      }
   }

   public static void main(String[] args) {

      Integer minKnownContacts;
      ContactsDump contactsDump;
      EjentaParty party = new EjentaParty();
      Set<Integer> invitesList;

      // read static values and validate
      minKnownContacts = 2;
      contactsDump = EjentaPartyHelper.loadContactsListFromStatic();
      party.setMinKnownContacts(minKnownContacts);
      party.setContactsDump(contactsDump);
      invitesList = party.getPartyContactList();

      System.out.println("Input/Given Contacts List: " + contactsDump);
      //System.out.println("Consolidated Contacts List: " + party.getContactsDump());
      System.out.println("Party Invites List (an empty list means we could not come up with a list of contacts that knew atleast "
         + minKnownContacts + " other contacts from that list): " + invitesList);
      junit.framework.TestCase.assertEquals(new HashSet<Integer>(Arrays.asList(1,3,5)), party.getPartyContactList());

      // read from stdin
      minKnownContacts = EjentaPartyHelper.loadNumMinContactsFromStdin();
      contactsDump = EjentaPartyHelper.loadContactsListFromStdin();
      party.setMinKnownContacts(minKnownContacts);
      party.setContactsDump(contactsDump);
      invitesList = party.getPartyContactList();

      System.out.println("Input/Given Contacts List: " + contactsDump);
      //System.out.println("Consolidated Contacts List: " + party.getContactsDump());
      System.out.println("Party Invites List (an empty list means we could not come up with a list of contacts that knew atleast "
         + minKnownContacts + " other contacts from that list): " + invitesList);
   }
}
