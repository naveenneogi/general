package com.workday;


import java.util.List;

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
public interface Mapper {

    /**
     *
     * @param fromValue
     * @param toValue
     * @param fromInclusive
     * @param toInclusive
     * @return
     */
    public List<Short> findIdsInRange(long fromValue, long toValue, boolean fromInclusive, boolean toInclusive);

}
