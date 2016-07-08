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

    // data size for each mapper to deal with, for linear mapreduce, we will have 1600
    // other types of mapR may choose different size to facilitate their specific insert/search ops
    protected static short MAPPER_DATA_SIZE = 1600;

    public MapReduceLinearRangeContainer(long[] data) {
        super(data);
    }

    /**
     *
     * @param data
     * @return
     */
    // TODO: look into refactoring the crux of this method into a separate utility method that all MapRs could use
    public List<Mapper> createMappers(long[] data) {
        try {
            if (MAPPER_DATA_SIZE == 0) throw (new Exception("mapper data size passed is invalid"));
            int noOfMappers = data.length / MAPPER_DATA_SIZE;
            if (data.length % MAPPER_DATA_SIZE != 0) {
                noOfMappers++;
            }
            List<Mapper> mappers = new LinkedList<>();

            for (int i = 0; i < noOfMappers; i++) {
                int startRange = i * MAPPER_DATA_SIZE;
                int endRange = Math.min(startRange + MAPPER_DATA_SIZE, data.length) ;

                // instantiate the 'MapperLinear' object that deals with the data subset within the start and end ranges
                Mapper mapper = new MapperLinear(data, startRange, endRange);
                mappers.add(mapper);
            }

            System.out.println(getClass().getSimpleName() + "." + getClass().getEnclosingMethod()
                    + " Thread# " + Thread.currentThread().getId()
                    + " mappers.size() " + mappers.size());

            return mappers;
        } catch (Exception e) {
            // log.error(e) ??
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
}
