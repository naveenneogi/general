package com.workday;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by naveenmurthy on 7/8/16.
 */
public class MapReduceLogarithmicRangeContainer extends MapReduceRangeContainer {

    // data size for each mapper to deal with, for logarithmic mapreduce, we will have 3200
    // other types of mapR may choose different size to facilitate their specific insert/search ops
    protected static short MAPPER_DATA_SIZE = 3200;

    public MapReduceLogarithmicRangeContainer(long[] data) {
        super(data);
    }

    /**
     *
     * @param data
     * @return
     */
    // TODO: look into refactoring the crux of this method into a separate utility method that all MapRs could use
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
            Mapper mapper = new MapperLogarithmic(data, startRange, endRange);
            mappers.add(mapper);
        }

        System.out.println(getClass().getSimpleName() + "." + getClass().getEnclosingMethod()
                + " Thread# " + Thread.currentThread().getId()
                + " mappers.size() " + mappers.size());

        return mappers;
    }

}