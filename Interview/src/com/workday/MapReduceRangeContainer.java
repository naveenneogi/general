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
 * Likewise, we will have different extensions to MapReduceRangeContainer depending on the internal data structures
 * these extensions use, for eg: MapReduceLinear, MapReduceLogarithmic
 *
 */
public abstract class MapReduceRangeContainer implements RangeContainer, MapperCreator {

    // data size for each mapper to deal with
    protected static short MAPPER_DATA_SIZE = 3200;
    List<Mapper> mapperList;

    /*
    public static RangeContainer createMappers(long[] data) {
        if (data == null || data.length > 32000 || data.length == 0) {
            throw new IllegalArgumentException("RangeContainer data size to be <= 32000");
        }
        // partition the data across 'few' mappers
        mapperList = createMappers(data);
    }
    */

    public MapReduceRangeContainer(long[] data) {
        if (data == null || data.length > 32000 || data.length == 0) {
            throw new IllegalArgumentException("RangeContainer data size to be <= 32000");
        }
        // partition the data across 'few' mappers
        // also expect the different implementations of MapReduceRangeContainer to implement their specific mappers
        mapperList = createMappers(data);
    }

    @Override
    public Ids findIdsInRange(long fromValue, long toValue, boolean fromInclusive, boolean toInclusive) {
        return null;
    }
}
