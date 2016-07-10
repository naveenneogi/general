package com.workday;

import java.util.*;

import java.util.concurrent.ConcurrentNavigableMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static java.util.logging.Level.SEVERE;

/**
 * Created by naveenmurthy on 7/10/16.
 */
public class TreeMapRangeContainer implements RangeContainer {
    private final static Logger logger = Logger.getLogger(MapReduceRangeContainer.class.getName());

    private final NavigableMap<Long, List<Short>> reverseIndexDataToId = new TreeMap<>();

    public TreeMapRangeContainer(long[] data) {
        logger.setLevel(SEVERE);

        if (data == null || data.length > 32000 || data.length == 0) {
            throw new IllegalArgumentException("RangeContainer invalid data passed: data size to be within 1-32k");
        }

        for (short i = 0; i < data.length; i++) {
            long item = data[i];
            if (!reverseIndexDataToId.containsKey(item)) {
                List<Short> ids = new ArrayList<>();
                ids.add(i);
                reverseIndexDataToId.put(item, ids);
            } else {
                reverseIndexDataToId.get(item).add(i);
            }
        }
    }


    @Override
    public Ids findIdsInRange(long fromValue, long toValue, boolean fromInclusive, boolean toInclusive) {
        List<Short> idList = new LinkedList<>();

        // validate input condition
        if (fromValue > toValue || (fromValue == toValue && fromInclusive != toInclusive)
                || (fromValue == toValue && fromInclusive == false && toInclusive == false)) {
            return new IdsImpl(idList);
        }

        if (reverseIndexDataToId == null ||  reverseIndexDataToId.isEmpty() || reverseIndexDataToId.size() == 0) {
            return new IdsImpl(idList);
        }

        NavigableMap<Long, List<Short>> idsRange = reverseIndexDataToId.subMap(fromValue, fromInclusive, toValue, toInclusive);
        List<List<Short>> idMultiList = new ArrayList<>(idsRange.values());
        idList = idMultiList.stream().flatMap(l -> l.stream()).collect(Collectors.toList());
        Collections.sort(idList);

        logger.log(Level.INFO, ""
                + " Thread." + Thread.currentThread().getName() + "." + Thread.currentThread().getId()
                + " idList.size() " + idList.size()
        );

        return new IdsImpl(idList);
    }
}
