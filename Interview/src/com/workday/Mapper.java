package com.workday;

import java.util.concurrent.ConcurrentSkipListMap;

/**
 * Created by naveenmurthy on 7/6/16.
 */
public class Mapper {
    // offset of data[] that this mapper is dealing with
    int indexOffset;
    // data that this mapper is dealing with:
    // we can either work with an array and do a linear search,
    // or have a SkipList/TreeMap kinda datastruct to
    long[] data;
    ConcurrentSkipListMap<Long, Short> reverseIndexDataToId;

    Mapper(int index, long[] data) {
        this.indexOffset = index;
        this.data = data;
    }
}
