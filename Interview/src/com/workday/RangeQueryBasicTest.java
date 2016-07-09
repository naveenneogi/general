package com.workday;


import static java.util.logging.Level.INFO;
import static java.util.logging.Level.SEVERE;
import static org.junit.Assert.*;

import com.sun.xml.internal.bind.v2.model.core.ID;
import org.junit.*;
import org.junit.rules.ExpectedException;

/**
 * Created by naveenmurthy on 7/6/16.
 */
public class RangeQueryBasicTest {
    //private final static Logger logger = Logger.getLogger(MapReduceRangeContainer.class.getName());

    private static RangeContainer rc;

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

        ids = rcd.findIdsInRange(14, 17, true, false);
        assertEquals(5, ids.nextId());
        assertEquals(6, ids.nextId());
        assertEquals(7, ids.nextId());
        assertEquals(Ids.END_OF_IDS, ids.nextId());


        // testing MapReduceLogarithmic
        rcd = rfd.createContainer(data, RangeContainerStrategy.MapReduceLogarithmic);
        ids = rcd.findIdsInRange(14, 17, true, true);
        // should return index: 2,5,6,7,8

        assertEquals(2, ids.nextId());
        assertEquals(5, ids.nextId());
        assertEquals(6, ids.nextId());
        assertEquals(7, ids.nextId());
        assertEquals(8, ids.nextId());
        assertEquals(Ids.END_OF_IDS, ids.nextId());

        ids = rcd.findIdsInRange(14, 17, true, false);
        assertEquals(5, ids.nextId());
        assertEquals(6, ids.nextId());
        assertEquals(7, ids.nextId());
        assertEquals(Ids.END_OF_IDS, ids.nextId());
    }


    @Test
    public void runARangeQueryTestForNotInRangeData() {

        RangeContainerFactoryDynamic rfd = new RangeContainerFactoryDynamicImpl();
        RangeContainer rcd;
        Ids ids;

        long[] data = new long[]{10, 12, 17, 21, 2, 15, 16, 15, 17};

        // testing MapReduceLinear
        rcd = rfd.createContainer(data, RangeContainerStrategy.MapReduceLinear);
        ids = rcd.findIdsInRange(100, 200, true, true);
        // should return index: null

        assertEquals(Ids.END_OF_IDS, ids.nextId());
    }

    //@Rule
    public ExpectedException exception = ExpectedException.none();
    //@Test
    public void testIndexOutOfBoundsException1() {
        RangeContainerFactoryDynamic rfd = new RangeContainerFactoryDynamicImpl();
        RangeContainer rcd;

        exception.expect(IllegalArgumentException.class);
        rcd = rfd.createContainer(new long[0], RangeContainerStrategy.MapReduceLinear);
    }

    //@Test
    public void testIndexOutOfBoundsException2() {
        try {
            RangeContainerFactoryDynamic rfd = new RangeContainerFactoryDynamicImpl();
            RangeContainer rcd;

            rcd = rfd.createContainer(new long[0], RangeContainerStrategy.MapReduceLinear);
        } catch (IllegalArgumentException e) {
            assertTrue(true);
        }
    }


    @Test
    public void miniStressTestMapReduceLinearSimpleData() {
        System.out.println("miniStressTestMapReduceLinearSimpleData: ");

        int runs = 10;
        long[] data;

        data = new long[32000];
        for (int i = 0; i < data.length; i++) {
            data[i] = 1;
            // set 0,8000,16000,24000 to 100
            if (i%8000 == 0) {
                data[i] = 100;
            }
        }

        RangeContainerFactoryDynamic rfd = new RangeContainerFactoryDynamicImpl();
        RangeContainer rcd = rfd.createContainer(data, RangeContainerStrategy.MapReduceLinear);

        // run N times, find items between 99 and 101
        Ids ids = miniStressTest(rcd, runs, 99, 101);
        assertEquals(0, ids.nextId());
        assertEquals(8000, ids.nextId());
        assertEquals(16000, ids.nextId());
        assertEquals(24000, ids.nextId());
        assertEquals(Ids.END_OF_IDS, ids.nextId());

        // run N times, find items between 0 and 2
        ids = miniStressTest(rcd, runs, 0, 2);
        for (int i = 0; i < 32000; i++) {
            if (i%8000 == 0) {
                continue;
            } else {
                assertEquals(i, ids.nextId());
            }
        }
        assertEquals(Ids.END_OF_IDS, ids.nextId());
    }

    @Test
    public void miniStressTestMapReduceLogarithmicSimpleData() {
        System.out.println("miniStressTestMapReduceLogarithmicSimpleData: ");

        int runs = 10;
        long[] data;

        data = new long[32000];
        for (int i = 0; i < data.length; i++) {
            data[i] = 1;
            // set 0,8000,16000,24000 to 100
            if (i%8000 == 0) {
                data[i] = 100;
            }
        }

        RangeContainerFactoryDynamic rfd = new RangeContainerFactoryDynamicImpl();
        RangeContainer rcd = rfd.createContainer(data, RangeContainerStrategy.MapReduceLogarithmic);

        // run N times, find items between 99 and 101
        Ids ids = miniStressTest(rcd, runs, 99, 101);
        assertEquals(0, ids.nextId());
        assertEquals(8000, ids.nextId());
        assertEquals(16000, ids.nextId());
        assertEquals(24000, ids.nextId());
        assertEquals(Ids.END_OF_IDS, ids.nextId());

        // run N times, find items between 0 and 2
        ids = miniStressTest(rcd, runs, 0, 2);
        for (int i = 0; i < 32000; i++) {
            if (i%8000 == 0) {
                continue;
            } else {
                assertEquals(i, ids.nextId());
            }
        }
        assertEquals(Ids.END_OF_IDS, ids.nextId());
    }

    @Test
    public void miniStressTestMapReduceLinearRandomData() {
        System.out.println("miniStressTestMapReduceLinearRandomData: ");

        int runs = 1000;
        long[] data;

        data = new long[32000];
        for (int i = 0; i < data.length; i++) {
            // get a random num between 99 and 9999
            data[i] = (long) (int)(Math.random() * ((9999 - 99) + 1)) + 99;
        }

        RangeContainerFactoryDynamic rfd = new RangeContainerFactoryDynamicImpl();
        RangeContainer rcd = rfd.createContainer(data, RangeContainerStrategy.MapReduceLinear);

        // run N times, find items between 99 and 101
        Ids ids = miniStressTest(rcd, runs, 99, 9999);
        for (int i = 0; i < 32000; i++) {
            assertEquals(i, ids.nextId());
        }
        assertEquals(Ids.END_OF_IDS, ids.nextId());
    }

    @Test
    public void miniStressTestMapReduceLogarithmicRandomData() {
        System.out.println("miniStressTestMapReduceLogarithmicRandomData: ");

        int runs = 1000;
        long[] data;

        data = new long[32000];
        for (int i = 0; i < data.length; i++) {
            // get a random num between 99 and 9999
            data[i] = (long) (int)(Math.random() * ((9999 - 99) + 1)) + 99;
        }

        RangeContainerFactoryDynamic rfd = new RangeContainerFactoryDynamicImpl();
        RangeContainer rcd = rfd.createContainer(data, RangeContainerStrategy.MapReduceLogarithmic);

        // run N times, find items between 99 and 101
        Ids ids = miniStressTest(rcd, runs, 99, 9999);
        for (int i = 0; i < 32000; i++) {
            assertEquals(i, ids.nextId());
        }
        assertEquals(Ids.END_OF_IDS, ids.nextId());
    }


    private Ids miniStressTest(RangeContainer rc, int runs, long fromValue, long toValue) {
        Ids ids = null;
        double time = 0;
        for (int i = 1; i <= runs; i++) {
            long startTime = System.currentTimeMillis();
            ids = rc.findIdsInRange(fromValue, toValue, true, true);
            long estimatedTime = System.currentTimeMillis() - startTime;
            time += estimatedTime;
        }

        System.out.println("No. of Runs: " + runs + ", Avg Time Per Run (millisecs): " + time/runs);
        return ids;
    }

}