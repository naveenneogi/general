package com.workday;

/**
 * Created by naveenmurthy on 7/6/16.
 */
public class RangeContainerFactoryImpl implements RangeContainerFactory {

    @Override
    public RangeContainer createContainer(long[] data) {
        return MapReduceRangeContainer.create(data);
    }
}
