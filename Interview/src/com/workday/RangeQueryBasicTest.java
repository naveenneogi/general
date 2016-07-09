package com.workday;


import static org.junit.Assert.*;

import org.junit.*;
import org.junit.rules.ExpectedException;

/**
 * Created by naveenmurthy on 7/6/16.
 */
public class RangeQueryBasicTest {

    private static RangeContainer rc;
    private static RangeContainer rcd;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @BeforeClass
    public static void runOnceBeforeClass() {
        RangeContainerFactory rf = new RangeContainerFactoryDynamicImpl();
        RangeContainerFactoryDynamic rfd = new RangeContainerFactoryDynamicImpl();
        long[] data = new long[]{10, 12, 17, 21, 2, 15, 16};

        rc = rf.createContainer(data);
        rcd = rfd.createContainer(data, RangeContainerStrategy.MapReduceLogarithmic);
    }

    //@Before
    public void setUp() {
        RangeContainerFactory rf = new RangeContainerFactoryDynamicImpl();
        long[] data = new long[]{10, 12, 17, 21, 2, 15, 16};
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
    public void runARangeQuery2() {
        Ids ids = rcd.findIdsInRange(14, 17, true, true);
        assertEquals(2, ids.nextId());
        assertEquals(5, ids.nextId());
        assertEquals(6, ids.nextId());
        assertEquals(Ids.END_OF_IDS, ids.nextId());

        ids = rcd.findIdsInRange(14, 17, true, false);
        assertEquals(5, ids.nextId());
        assertEquals(6, ids.nextId());
        assertEquals(Ids.END_OF_IDS, ids.nextId());

        ids = rcd.findIdsInRange(20, Long.MAX_VALUE, false, true);
        assertEquals(3, ids.nextId());
        assertEquals(Ids.END_OF_IDS, ids.nextId());

    }

}