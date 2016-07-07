package com.workday;

import java.util.List;
import java.util.LinkedList;

/**
 * Created by naveenmurthy on 7/6/16.
 *
 * would use an array internally and search on arrays,
 * its implementation of mappers would be specific to array manipulations and support queries as such
 *
 */
public class MapperLinear implements Mapper {

    // offset of data[] that this mapper is dealing with
    int indexOffset;

    // data that this mapper is dealing with: stored as an array and supports linear search
    long[] data;

    public MapperLinear(int index, long[] data) {
        this.indexOffset = index;
        this.data = data;
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
        for (short i = 0; i < data.length; i++) {
            if (inRange(data[i], fromValue, toValue, fromInclusive, toInclusive)) {
                short id = (short) (indexOffset + i);
                idList.add(id);
            }
        }

        return idList;
    }

    // determines if the given value is in range
    private boolean inRange(long value, long fromValue, long toValue, boolean fromInclusive, boolean toInclusive) {
        boolean isAboveLowerRange = fromInclusive ? value >= fromValue : value > fromValue;
        boolean isBelowUpperRange = toInclusive ? value <= toValue : value < toValue;
        return isAboveLowerRange && isBelowUpperRange;
    }
}
