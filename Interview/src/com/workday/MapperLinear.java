package com.workday;

import java.util.Arrays;
import java.util.List;
import java.util.LinkedList;

import java.util.logging.Level;
import java.util.logging.Logger;

import static java.util.Arrays.copyOfRange;
import static java.util.logging.Level.SEVERE;

/**
 * Created by naveenmurthy on 7/6/16.
 *
 * use an array internally to store the reverse index of data->id
 * its implementation of mappers would be specific to array manipulations and support queries as such
 * since this being an array operations, search queries would be linear,
 * but many mappers acting on a 'disjoint smaller dataset' imply low latency query times
 *
 */
public class MapperLinear implements Mapper {

    private final static Logger logger = Logger.getLogger(MapperLinear.class.getName());

    // offset of data[] that this mapper is dealing with
    int beginMapperOffset;
    int endMapperOffset;

    // data that this mapper is dealing with: stored as an array and supports linear search
    long[] dataSubset;
    // keep track of the min & max while initiating the dataSubset
    // failfast: if the range being asked for is below the min OR above the max of our data
    long min, max;

    public MapperLinear(long[] data, int beginMapperOffset, int endMapperOffset) {
        logger.setLevel(SEVERE);

        this.beginMapperOffset = beginMapperOffset;
        this.endMapperOffset = endMapperOffset;
        try {
            //this.dataSubset = Arrays.copyOfRange(data, beginMapperOffset, endMapperOffset);
            dataSubset = new long[data.length];
            int j = -1;
            for (int i=beginMapperOffset; i<endMapperOffset; i++) {
                long item = data[i];
                this.dataSubset[++j] = item;
                // keep track of the min & max while copying the subarray
                if (item < this.min) this.min = item;
                else if (item > this.max) this.max = item;
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
        } finally {
            // any specific final cleanups?
        }
    }

    /**
     *
     *
     * @param fromValue
     * @param toValue
     * @param fromInclusive
     * @param toInclusive
     * @return
     */
    public List<Short> findIdsInRange(long fromValue, long toValue, boolean fromInclusive, boolean toInclusive) {

        List<Short> idList = new LinkedList<>();

        // validate input condition
        if (fromValue > toValue || (fromValue == toValue && fromInclusive != toInclusive)
                || (fromValue == toValue && fromInclusive == false && toInclusive == false)) {
            return idList;
        }

        //failfast: if the range being asked for is below the min OR above the max of our data, return
        if (fromValue < this.min && toValue < this.min) return idList;
        if (fromValue > this.max && toValue > this.max) return idList;


        for (short i = 0; i < dataSubset.length; i++) {
            if (inRange(dataSubset[i], fromValue, toValue, fromInclusive, toInclusive)) {
                short id = (short) (beginMapperOffset + i);
                idList.add(id);
            }
        }

        logger.log(Level.INFO, ""
                + " Thread." + Thread.currentThread().getName() + "." + Thread.currentThread().getId()
                + " idList.size() " + idList.size()
        );

        return idList;
    }

    // determines if the given value is in range
    private boolean inRange(long value, long fromValue, long toValue, boolean fromInclusive, boolean toInclusive) {
        boolean isAboveLowerRange = fromInclusive ? value >= fromValue : value > fromValue;
        boolean isBelowUpperRange = toInclusive ? value <= toValue : value < toValue;
        return isAboveLowerRange && isBelowUpperRange;
    }
}
