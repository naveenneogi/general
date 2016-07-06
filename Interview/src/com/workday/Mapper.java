package com.workday;

import java.util.concurrent.ConcurrentSkipListMap;

/**
 * Created by naveenmurthy on 7/6/16.
 *
 * The MapReduceRangeContainer implements a Map-Reduce algorithm to partition data across few mappers.
 * Each mapper supports the range query within its local smaller dataset
 * As many 'reducer' threads as there are mappers will be spawned
 * these reducers then invoke the mappers range query
 * then the resultsets are combined and returned as an iterator for the application to iterate over
 *
 * The idea behind this Map-Reduce algo is to allow for threads within the container to operate on distinct datasets
 * and hence speed up the search queries by a facor of N where N being the number of mappers/reducers/threads
 *
 * The search operation within each Mapper itself can be implemented in multiple ways - linear, logarithmic, etc
 * depending on the internal data structure we end up using - arrays, skiplists, treemaps, treaps, etc
 *
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
