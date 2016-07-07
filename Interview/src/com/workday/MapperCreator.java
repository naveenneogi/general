package com.workday;

import java.util.List;

/**
 * Created by naveenmurthy on 7/6/16.
 *
 * an interface to force classes to support their own specific mappers based on their internal implementations
 * for eg:
 * MapReduceLinearRangeContainer:
 *      would use an array internally and search on arrays,
 *      its implementation of mappers would be specific to array manipulations and support queries as such
 * MapReduceLogarithmicRangeContainer:
 *      would use skipListMaps internally and search on these
 *      its implementation of mappers would be specific to skiplist manipulations and support queries as such
 *
 */
public interface MapperCreator {
    /**
     * different implementations of mappers will need to support this interface
     * @param data
     * @return
     */
    public List<Mapper> createMappers(long[] data);
}
