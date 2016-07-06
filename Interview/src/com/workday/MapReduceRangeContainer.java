package com.workday;


import java.util.List;
import java.util.LinkedList;

/**
 * Created by naveenmurthy on 7/6/16.
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
