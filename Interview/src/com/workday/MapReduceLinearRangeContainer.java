package com.workday;

import java.util.Arrays;
import java.util.List;
import java.util.LinkedList;

/**
 * Created by naveenmurthy on 7/6/16.
 *
 * this is a linear search implentation of the MapReduceRangeContainer
 * as such this is also forced to implement all the interfaces the MapReduceRangeContainer depends on if not already
 * implemented within the base class
 *
 */
public class MapReduceLinearRangeContainer extends MapReduceRangeContainer {

    public MapReduceLinearRangeContainer(long[] data) {
        super(data);
    }

    /**
     *
     * @param data
     * @return
     */
    public List<Mapper> createMappers(long[] data) {
        int noOfMappers = data.length / MAPPER_DATA_SIZE;
        if (data.length % MAPPER_DATA_SIZE != 0) {
            noOfMappers++;
        }
        List<Mapper> mappers = new LinkedList<>();

        for (int i = 0; i < noOfMappers; i++) {
            int startRange = i * MAPPER_DATA_SIZE;
            int endRange = Math.min(startRange + MAPPER_DATA_SIZE, data.length) ;

            // instantiate the 'MapperLinear' objects here
            Mapper mapper = new MapperLinear(startRange, Arrays.copyOfRange(data, startRange, endRange));
            mappers.add(mapper);
        }

        return mappers;
    }
}
