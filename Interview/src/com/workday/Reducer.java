package com.workday;

import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by naveenmurthy on 7/6/16.
 *
 * The MapReduceRangeContainer implements a Map-Reduce algorithm to partition data across few mappers.
 * Each mapper supports the range query within its local smaller dataset
 * As many 'reducer' threads as there are mappers will be spawned
 * these reducers then invoke the mappers range query
 * then the resultsets are combined and returned as an iterator for the application
 *
 * The idea behind this Map-Reduce algo is to allow for threads within the container to operate on distinct datasets
 * and hence speed up the search queries by a facor of N, N being the number of mappers/reducers/threads
 *
 */
public class Reducer implements Callable<List<Short>> {
    private Mapper mapper;
    private long fromValue;
    private long toValue;
    private boolean fromInclusive;
    private boolean toInclusive;

    Reducer(Mapper mapper, long fromValue, long toValue, boolean fromInclusive, boolean toInclusive) {
        this.mapper = mapper;
        this.fromValue = fromValue;
        this.toValue = toValue;
        this.fromInclusive = fromInclusive;
        this.toInclusive = toInclusive;
    }

    @Override
    public List<Short> call() {
        List<Short> ids = mapper.findIdsInRange(fromValue, toValue, fromInclusive, toInclusive);
        return ids;
    }
}
