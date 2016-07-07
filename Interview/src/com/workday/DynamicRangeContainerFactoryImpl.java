package com.workday;

/**
 * Created by naveenmurthy on 7/6/16.
 *
 * there can be many 'efficient' implementations of RangeContainer as enumarated below
 * the applications calling can instantiate one or more flavors based on their specific needs and constraints
 *
 */
public class DynamicRangeContainerFactoryImpl implements DynamicRangeContainerFactory {

    /**
     *
     * @param data  the data array to be 'contained'
     * @return the default RangeContainer object that contains 'data' and supports range Qs
     */
    public RangeContainer createContainer(long[] data) {
        return new MapReduceLinearRangeContainer(data);
    }

    /**
     *
     * @param data  the data array to be 'contained'
     * @param strategy the container/search 'strategy' to be used
     * @return the RangeContainer object corresponding to the 'strategy' being asked by the application
     */
    public RangeContainer createContainer(long[] data, RangeContainerStrategy strategy) {
        try {
            if (strategy.equals(RangeContainerStrategy.MapReduceLinear)) {
                return new MapReduceLinearRangeContainer(data);
            }
            if (strategy.equals(RangeContainerStrategy.MapReduceLogarithmic)) {
                return new MapReduceLinearRangeContainer(data);
            }


        } catch (Exception e) {
            // log.error(e) ??
            System.out.println("Exception thrown: " + e.getMessage());
        }
        return null;
    }
}
