package com.workday;


import java.util.List;
import java.util.Map;
import java.util.LinkedList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import java.util.logging.Level;
import java.util.logging.Logger;

import static java.util.logging.Level.SEVERE;

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
 * The search operation within each Mapper itself can be implemented in multiple ways - linear, logarithmic, etc
 * depending on the internal data structure we end up using - arrays, skiplists, treemaps, treaps, etc
 * Likewise, we will have different extensions to MapReduceRangeContainer depending on the internal data structures
 * these extensions use, for eg: MapReduceLinear, MapReduceLogarithmic
 *
 */
public abstract class MapReduceRangeContainer implements RangeContainer, MapperCreator {

    private final static Logger logger = Logger.getLogger(MapReduceRangeContainer.class.getName());
    List<Mapper> mapperList;

    public MapReduceRangeContainer(long[] data) {
        logger.setLevel(SEVERE);

        if (data == null || data.length > 32000 || data.length == 0) {
            throw new IllegalArgumentException("RangeContainer invalid data passed: data size to be within 1-32k");
        }
        // partition the data across 'few' mappers
        // also expect the different implementations of MapReduceRangeContainer to implement their specific createMappers
        mapperList = createMappers(data);
    }

    /**
     * This method does the reduce part of the algorithm.
     * It creates as many threads as mappers and then executes the range query
     * and merges the results from each thread to return the final result.
     * @param fromValue
     * @param toValue
     * @param fromInclusive
     * @param toInclusive
     * @return
     */
    private List<Short> reduce(long fromValue, long toValue,
                               boolean fromInclusive, boolean toInclusive) {
        ExecutorService executor = Executors.newFixedThreadPool(mapperList.size());
        List<Callable<List<Short>>> reducers = new LinkedList<>();
        for (Mapper mapper : mapperList) {
            Callable<List<Short>> reducer = new Reducer(mapper, fromValue, toValue, fromInclusive, toInclusive);
            reducers.add(reducer);
        }

        logger.log(Level.INFO, ""
                + " Thread." + Thread.currentThread().getName() + "." + Thread.currentThread().getId()
                + " reducers.size() " + reducers.size()
        );

        List<Future<List<Short>>> reducerResults = null;

        try {
            reducerResults = executor.invokeAll(reducers);
        } catch (InterruptedException e) {
            logger.log(Level.SEVERE, e.getMessage());
        } finally {
            // any specific final cleanups?
            executor.shutdown();
        }
        // Wait until all threads are done
        while (!executor.isTerminated()) {}

        // the results are in the same order in which they were added to the executor service.
        List<Short> resultIds = new LinkedList<>();
        for (Future<List<Short>> result : reducerResults) {
            try {
                resultIds.addAll(result.get());
            } catch (InterruptedException | ExecutionException e) {
                logger.log(Level.SEVERE, e.getMessage());
            } finally {
                // any specific final cleanups?
            }
        }

        // the results are in the order expected and hence no need to sort.
        return resultIds;
    }

    /**
     * Calls the reduce method which creates as many threads as mappers and invokes the range
     * query on each thread before merging the results.
     */
    public Ids findIdsInRange(long fromValue, long toValue, boolean fromInclusive, boolean toInclusive) {
        List<Short> idsList = reduce(fromValue, toValue, fromInclusive, toInclusive);
        return new IdsImpl(idsList);
    }
}
