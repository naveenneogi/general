package com.workday;

import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.ConcurrentNavigableMap;

/**
 * Created by naveenmurthy on 7/8/16.
 *
 * use the skipList datastruct ConcurrentSkipListMap to store the reverse index of data->id
 * skiplist manipulations: insert delete search are all O(log(n))
 * additionally skiplists do not carry the overhead of balancing trees, as is the case for treemaps
 *
 * another consideration is if we really need concurrency, java does not natively support nonconcurrent skiplists,
 * so essentially ConcurrentSkipListMap are the concurrent variations of treemaps
 * concurrency does add a slight perf hit because of locking/waiting
 *
 * */
public class MapperLogarithmic implements Mapper {

    // offset of data[] that this mapper is dealing with
    int beginMapperOffset;
    int endMapperOffset;

    // data that this mapper is dealing with: stored as an array and supports linear search
    // TODO: <Long, List<Short>> ?
    private ConcurrentSkipListMap<Long, Short> indexIds = new ConcurrentSkipListMap<Long, Short>();

    public MapperLogarithmic(long[] data, int beginMapperOffset, int endMapperOffset) {
        for (int i = beginMapperOffset; i < endMapperOffset; i++) {
            indexIds.put(data[i], (short) i);
        }
    }

    /**
     *
     *
     * @param fromValue
     * @param toValue
     * @param fromInclusive
     * @param toInclusive
     * @return
     */
    public List<Short> findIdsInRange(long fromValue, long toValue,	boolean fromInclusive, boolean toInclusive) {

        ConcurrentNavigableMap<Long, Short>idsRange = indexIds.subMap(fromValue, fromInclusive, toValue, toInclusive);
        List<Short> idList = new ArrayList<>(idsRange.values());
        Collections.sort(idList);

        System.out.println(getClass().getSimpleName() + "." + getClass().getEnclosingMethod()
                + " Thread# " + Thread.currentThread().getId()
                + " idList.size() " + idList.size());

        return idList;

    }

}