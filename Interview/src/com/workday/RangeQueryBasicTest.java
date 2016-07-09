package com.workday;


import static org.junit.Assert.*;

import com.sun.xml.internal.bind.v2.model.core.ID;
import org.junit.*;
import org.junit.rules.ExpectedException;

/**
 * Created by naveenmurthy on 7/6/16.
 */
public class RangeQueryBasicTest {

    private static RangeContainer rc;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @BeforeClass
    public static void runOnceBeforeClass() {
        long[] data = new long[]{10, 12, 17, 21, 2, 15, 16};
        RangeContainerFactory rf = new RangeContainerFactoryDynamicImpl();
        rc = rf.createContainer(data);
    }

    @Test
    public void runARangeQuery() {
        Ids ids = rc.findIdsInRange(14, 17, true, true);
        assertEquals(2, ids.nextId());
        assertEquals(5, ids.nextId());
        assertEquals(6, ids.nextId());
        assertEquals(Ids.END_OF_IDS, ids.nextId());

        ids = rc.findIdsInRange(14, 17, true, false);
        assertEquals(5, ids.nextId());
        assertEquals(6, ids.nextId());
        assertEquals(Ids.END_OF_IDS, ids.nextId());

        ids = rc.findIdsInRange(20, Long.MAX_VALUE, false, true);
        assertEquals(3, ids.nextId());
        assertEquals(Ids.END_OF_IDS, ids.nextId());
    }


    @Test
    public void runARangeQueryTestForDupsInData() {

        RangeContainerFactoryDynamic rfd = new RangeContainerFactoryDynamicImpl();
        RangeContainer rcd;
        Ids ids;

        // 17 is at index:2,8
        // 15 is at index:5,7
        long[] data = new long[]{10, 12, 17, 21, 2, 15, 16, 15, 17};

        // testing MapReduceLinear
        rcd = rfd.createContainer(data, RangeContainerStrategy.MapReduceLinear);
        ids = rcd.findIdsInRange(14, 17, true, true);
        // should return index: 2,5,6,7,8

        assertEquals(2, ids.nextId());
        assertEquals(5, ids.nextId());
        assertEquals(6, ids.nextId());
        assertEquals(7, ids.nextId());
        assertEquals(8, ids.nextId());
        assertEquals(Ids.END_OF_IDS, ids.nextId());

        // testing MapReduceLogarithmic
        ids = null;
        rcd = rfd.createContainer(data, RangeContainerStrategy.MapReduceLogarithmic);
        ids = rcd.findIdsInRange(14, 17, true, true);
        // should return index: 2,5,6,7,8

        assertEquals(2, ids.nextId());
        assertEquals(5, ids.nextId());
        assertEquals(6, ids.nextId());
        assertEquals(7, ids.nextId());
        assertEquals(8, ids.nextId());
        assertEquals(Ids.END_OF_IDS, ids.nextId());



        /*
        ids = rcd.findIdsInRange(14, 17, true, false);
        assertEquals(5, ids.nextId());
        assertEquals(6, ids.nextId());
        assertEquals(Ids.END_OF_IDS, ids.nextId());

        ids = rcd.findIdsInRange(20, Long.MAX_VALUE, false, true);
        assertEquals(3, ids.nextId());
        assertEquals(Ids.END_OF_IDS, ids.nextId());
        */

    }

}