package com.workday;


import java.util.List;
import java.util.LinkedList;

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
public class MapReduceRangeContainer implements RangeContainer {

    // data size for each mapper to deal with
    private static short MAPPER_DATA_SIZE = 3200;
    List<Mapper> mapperList;

    private MapReduceRangeContainer(long[] data) {
        if (data == null || data.length > 32000 || data.length == 0) {
            throw new IllegalArgumentException("RangeContainer data size to be <= 32000");
        }
        // partition the data and dist it across 'few' mappers
        mapperList = createMappers(data);
    }

    private List<Mapper> createMappers(long[] data) {
        List<Mapper> mappers = new LinkedList<>();
        return mappers;
    }

    public static RangeContainer create(long[] data) {
        return new MapReduceRangeContainer(data);
    }

    @Override
    public Ids findIdsInRange(long fromValue, long toValue, boolean fromInclusive, boolean toInclusive) {
        return null;
    }
}
