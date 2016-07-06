package com.ejenta;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;

/**
 * Created by naveenmurthy on 5/6/16.
 */
public class EjentaPartyHelper {

   // get min contacts from stdin
   public static Integer loadNumMinContactsFromStdin() {
      Scanner stdin = new Scanner(System.in);
      Integer minContacts = 0;
      try {
         System.out.println("Enter the minimum num of contacts a person should know in the party, for eg: 5");
         minContacts = stdin.nextInt();
      } catch (Exception e) {
         System.out.println("Invalid input passed, please check and try again: " + e.getMessage());
         stdin.close();
         java.lang.System.exit(0);
      }
      return minContacts;
   }

   // get contacts list from stdin
   public static ContactsDump loadContactsListFromStdin() {

      Scanner stdin = new Scanner(System.in);

      System.out.println("Enter the contact pairs one per line. Enter their numeric IDs only, for eg \n\t1,5\n"
                           + "\t1,6\n"
                           + "\t2,5 \n\tso on, with only one pair per line"
                           + "\nIf you enter more than one pair per line, only the first pair is considered, rest will be ignored"
                           + "\nEnter exit/EXIT when done");

      try {
         String line;
         ContactsDump contactsList = new ContactsDump();
         while(stdin.hasNextLine() && !( line = stdin.nextLine() ).equalsIgnoreCase("exit"))
         {
            if(!line.isEmpty()) {
               //System.out.println(line);
               String[] tokens = line.split(",");
               Integer contactA = Integer.parseInt(tokens[0]);
               Integer contactB = Integer.parseInt(tokens[1]);

               if(contactA.equals(contactB)) continue;

               //System.out.println(contactA + " -> " + contactB );
               if(!contactsList.containsKey(contactA)) {
                  contactsList.put(contactA, new HashSet<>());
               }
               contactsList.get(contactA).add(contactB);
            }
         }
         return contactsList;
      } catch (Exception e) {
         System.out.println("Contact pairs entered per line seems to have an issue, have you entered their numeric IDs,3 please check and try again: " + e.getMessage());
         java.lang.System.exit(0);
      } finally {
         stdin.close();
      }
      return new ContactsDump();
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