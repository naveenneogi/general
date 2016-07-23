package com.workday;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.util.logging.Level.SEVERE;

/**
 * Created by naveenmurthy on 7/8/16.
 *
 * TODO: MapReduceRangeContainerFactory could own the creation of Linear, Logarithmic, etc variations
 *
 */
public class MapReduceLogarithmicRangeContainer implements RangeContainer {

    private final static Logger logger = Logger.getLogger(MapReduceLogarithmicRangeContainer.class.getName());

    // data size for each mapper to deal with, for logarithmic mapreduce, we will have 3200
    // other types of mapR may choose different size to facilitate their specific insert/search ops
    protected static final short MAPPER_DATA_SIZE = 8000;
    //protected static final short MAPPER_DATA_SIZE = Short.MAX_VALUE;


    List<Mapper> mapperList;

    public MapReduceLogarithmicRangeContainer(long[] data) {
        logger.setLevel(SEVERE);

        if (data == null || data.length > 32000 || data.length == 0) {
            throw new IllegalArgumentException("RangeContainer invalid data passed: data size to be within 1-32k");
        }
        // partition the data across 'few' mappers
        // also expect the different implementations of MapReduceRangeContainer to implement their specific createMappers
        mapperList = createMappers(data);
    }


    /**
     *
     * @param data
     * @return
     */
    // TODO: look into refactoring the crux of this method into a separate utility method that all MapRs could use
    public List<Mapper> createMappers(long[] data) {
        try {
            int noOfMappers = data.length / MAPPER_DATA_SIZE;
            if (data.length % MAPPER_DATA_SIZE != 0) {
                noOfMappers++;
            }

            List<Mapper> mappers = new LinkedList<>();

            for (int i = 0; i < noOfMappers; i++) {
                int startRange = i * MAPPER_DATA_SIZE;
                int endRange = Math.min(startRange + MAPPER_DATA_SIZE, data.length) ;

                // instantiate the 'MapperLogarithmic' object that deals with the data subset
                // within the start and end ranges
                Mapper mapper = new MapperLogarithmic(data, startRange, endRange);
                mappers.add(mapper);
            }

            /*
            logger.log(Level.INFO, ""
                    + " Thread." + Thread.currentThread().getName() + "." + Thread.currentThread().getId()
                    + " mappers.size() " + mappers.size()
            );
            */

            return mappers;
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
        } finally {
            // any specific final cleanups?
        }
        return null;
    }


    /**
     * Calls the reduce method which creates as many threads as mappers and invokes the range
     * query on each thread before merging the results.
     */
    public Ids findIdsInRange(long fromValue, long toValue, boolean fromInclusive, boolean toInclusive) {
        List<Short> idList = MapReduceRangeContainerUtil.reduce(mapperList, fromValue, toValue, fromInclusive, toInclusive);
        return new IdsImpl(idList);
    }

}