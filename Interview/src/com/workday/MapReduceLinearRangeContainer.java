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
    public List<Mapper> createMappers(long[] data, short mapperDataSize) {
        try {
            if (mapperDataSize == 0) throw (new Exception("mapper data size passed is invalid"));
            int noOfMappers = data.length / mapperDataSize;
            if (data.length % mapperDataSize != 0) {
                noOfMappers++;
            }
            List<Mapper> mappers = new LinkedList<>();

            for (int i = 0; i < noOfMappers; i++) {
                int startRange = i * mapperDataSize;
                int endRange = Math.min(startRange + mapperDataSize, data.length) ;

                // instantiate the 'MapperLinear' object that deals with the data subset within the start and end ranges
                Mapper mapper = new MapperLinear(data, startRange, endRange);
                mappers.add(mapper);
            }
            return mappers;
        } catch (Exception e) {
            //move to a log.error
            System.out.println(e.getMessage());
        }
        return null;
    }
}
