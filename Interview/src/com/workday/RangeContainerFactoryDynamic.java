package com.workday;

/**
 * Created by naveenmurthy on 7/8/16.
 */
public interface RangeContainerFactoryDynamic extends RangeContainerFactory{

    /**
     * builds an immutable container optimized for range queries.
     * Data is expected to be 32k items or less.
     *
     * takes an additional strategy parameter to invoke the corresponding type of RangeContainer creation
     */
    RangeContainer createContainer(long[] data, RangeContainerStrategy strategy);

}
