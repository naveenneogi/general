package com.workday;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.util.logging.Level.SEVERE;

/**
 * Created by naveenmurthy on 7/12/16.
 */
public class MapReduceRangeContainerUtil {

    private final static Logger logger = Logger.getLogger(MapReduceRangeContainerUtil.class.getName());

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
    public static List<Short> reduce(List<Mapper> mapperList, long fromValue, long toValue,
                               boolean fromInclusive, boolean toInclusive) {

        logger.setLevel(SEVERE);

        // TODO: what if no threds or fewer threads, degrade smartly, worst case calling thread does all the reduce
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
        // TODO: what if threads just make you wait forever
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

}
