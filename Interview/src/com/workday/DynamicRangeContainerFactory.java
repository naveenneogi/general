package com.workday;

/**
 * Created by naveenmurthy on 7/6/16.
 *
 * there can be many 'efficient' implementations of RangeContainer
 * the applications calling can instantiate one or more flavors based on their specific needs and constraints
 * by passing in the appropriate 'strategy' to the factory
 *
 */
public interface DynamicRangeContainerFactory extends RangeContainerFactory {

    /**
     * builds an immutable container optimized for range queries.
     * Data is expected to be 32k items or less.
     *
     * takes an additional strategy parameter to invoke the corresponding type of RangeContainer creation
     */
    RangeContainer createContainer(long[] data, RangeContainerStrategy strategy);

}
