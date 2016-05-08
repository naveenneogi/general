package com.ejenta;

import org.jetbrains.annotations.Nullable;
import com.google.common.collect.Sets;

import java.util.*;

public class EjentaParty {

   // min number of contacts a person should know in the party
   private static Integer minKnownContacts;
   private static Integer minNumOFInvites;

   // map of person -> list of his contacts
   private static ContactsDump contactsDump;
   private static Set<Integer> individualContactList;
   private static Set<Integer> partyContactList;

   // supplementing data:
   public EjentaParty(Integer minContacts, ContactsDump contacts) {
      minKnownContacts = minContacts;
      minNumOFInvites = minContacts+1;
      contactsDump = processContactsList(contacts);
      individualContactList = contacts.keySet();
      partyContactList = generatePartyContactList(individualContactList);
      //printDump();
   }

   private static void printDump() {
      System.out.println("minKnownContacts: " + minKnownContacts);
      System.out.println("minNumOFInvites: " + minNumOFInvites);
      System.out.println("contactsDump: " + contactsDump);
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
         // until we end up with a list thats the same as the starting list
         boolean didWeDropAnyContact = false;
         for (Integer contact: potentialPartyList) {
            if(contactsDump.get(contact).size() >= minKnownContacts &&
               Sets.intersection(contactsDump.get(contact), potentialPartyList).size() >= minKnownContacts) {
               partyList.add(contact);
            } else {
               didWeDropAnyContact = true;
            }
         }

         // If we did drop one or more contacts, redo the party list by filtering one or more contacts out recursively
         // until we end up with a list thats the same as the starting list
         // this is the ultimate stopping condition if we indeed manage to find a working party list
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


   private static ContactsDump processContactsList(ContactsDump contactsDump) {
      // load up a temp map and add missing connections by looking into each of the contacts connections
      ContactsDump mapOflistOfContactsTemp = (ContactsDump) contactsDump.clone();

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

   public Set<Integer> getPartyContactList () {
      return partyContactList;
   }

   public boolean doesContactAknowContactB(Integer A, Integer B) {
      try {
         return contactsDump.get(A).contains(B);
      } catch (Exception e){
         return false;
      }
   }

   public static void main(String[] args) {

      Integer minKnownContacts = 2;
      ContactsDump contactsDump = EjentaPartyHelper.loadContactsListFromStatic();
      EjentaParty party = new EjentaParty(minKnownContacts, contactsDump);
      System.out.println("Invites List: " + party.getPartyContactList());

      /*
      System.out.println(party.doesContactAknowContactB(1,3));
      System.out.println(party.doesContactAknowContactB(2,4));
      System.out.println(party.doesContactAknowContactB(4,2));
      System.out.println(party.doesContactAknowContactB(6,9));
      System.out.println(party.doesContactAknowContactB(10,11));
      System.out.println(party.doesContactAknowContactB(12,12));
      */
   }
}
