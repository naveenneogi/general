package com.workday;

/**
 * Created by naveenmurthy on 7/8/16.
 */
public class RangeContainerFactoryDynamicImpl implements RangeContainerFactoryDynamic {

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
                return new MapReduceLogarithmicRangeContainer(data);
            }
            if (strategy.equals(RangeContainerStrategy.TreeMap)) {
                return new TreeMapRangeContainer(data);
            }


        } catch (Exception e) {
            // log.error(e) ??
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

}
