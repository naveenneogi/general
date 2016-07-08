package com.workday;

import java.util.Arrays;
import java.util.List;
import java.util.LinkedList;

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

    // offset of data[] that this mapper is dealing with
    int beginMapperOffset;
    int endMapperOffset;

    // data that this mapper is dealing with: stored as an array and supports linear search
    long[] dataSubset;

    public MapperLinear(long[] data, int beginMapperOffset, int endMapperOffset) {
        this.beginMapperOffset = beginMapperOffset;
        this.endMapperOffset = endMapperOffset;
        this.dataSubset = Arrays.copyOfRange(data, beginMapperOffset, endMapperOffset);
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
        for (short i = 0; i < dataSubset.length; i++) {
            if (inRange(dataSubset[i], fromValue, toValue, fromInclusive, toInclusive)) {
                short id = (short) (beginMapperOffset + i);
                idList.add(id);
            }
        }

        System.out.println(getClass().getSimpleName() + "." + getClass().getEnclosingMethod()
                + " Thread# " + Thread.currentThread().getId()
                + " idList.size() " + idList.size());

        return idList;
    }

    // determines if the given value is in range
    private boolean inRange(long value, long fromValue, long toValue, boolean fromInclusive, boolean toInclusive) {
        boolean isAboveLowerRange = fromInclusive ? value >= fromValue : value > fromValue;
        boolean isBelowUpperRange = toInclusive ? value <= toValue : value < toValue;
        return isAboveLowerRange && isBelowUpperRange;
    }
}
