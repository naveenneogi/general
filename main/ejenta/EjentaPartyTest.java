package com.ejenta;

import com.sun.tools.javac.code.Attribute;
import junit.framework.TestCase;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by naveenmurthy on 5/7/16.
 */
public class EjentaPartyTest extends TestCase {

   protected Integer minContacts;
   protected ContactsDump contactsDump;
   protected EjentaParty party;

   @Override
   public void setUp() throws Exception {
      super.setUp();
      contactsDump = EjentaPartyHelper.loadContactsListFromStatic();
   }

   @Test
   public void testContactsConnections() throws Exception {

      minContacts = 2;
      party = new EjentaParty(minContacts, contactsDump);

      assertEquals(true,party.doesContactAknowContactB(1,3));
      assertEquals(true,party.doesContactAknowContactB(2,4));
      assertEquals(true,party.doesContactAknowContactB(4,2));
      assertEquals(false,party.doesContactAknowContactB(6,9));
      assertEquals(false,party.doesContactAknowContactB(10,11));
      assertEquals(false,party.doesContactAknowContactB(12,12));
   }

   @Test
   public void testPartyInvitesListWithMinAsOne() throws Exception {

      minContacts = 1;
      party = new EjentaParty(minContacts, contactsDump);

      assertEquals(new HashSet<Integer>(Arrays.asList(1,2,3,4,5,6,7,8,10)), party.getPartyContactList());
   }

   @Test
   public void testPartyInvitesListWithMinAsTwo() throws Exception {

      minContacts = 2;
      party = new EjentaParty(minContacts, contactsDump);

      assertEquals(new HashSet<Integer>(Arrays.asList(1,3,5)), party.getPartyContactList());
   }


   @Test
   public void testPartyInvitesListWithMinAsThree() throws Exception {

      minContacts = 3;
      party = new EjentaParty(minContacts, contactsDump);

      assertEquals(new HashSet<Integer>(), party.getPartyContactList());
   }


   @Override
   public void tearDown() throws Exception {
      super.tearDown();
   }
}
