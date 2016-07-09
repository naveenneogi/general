package com.workday;

import java.util.List;
import java.util.Iterator;

/**
 * Created by naveenmurthy on 7/6/16.
 */
public class IdsImpl implements Ids {

    Iterator<Short> itr;
    int size;

    public IdsImpl(List<Short> idList) {
        itr = idList.iterator();
        size = idList.size();
    }

    @Override
    public short nextId() {
        return itr.hasNext() ? itr.next() : Ids.END_OF_IDS;
    }

}
