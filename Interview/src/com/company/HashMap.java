package com.company;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by naveenmurthy on 7/4/16.
 */
public class HashMap<K extends Comparable<K>, V> {

    private class MapEntry<K,V> {
        private K key;
        private V value;
        private MapEntry<K,V> next;

        public MapEntry(K key, V value) {
            this.key = key;
            this.value = value;
            next = null;
        }
    }

    private final double loadFactor = 0.75;
    private final int tableSize = 997;
    private ArrayList<MapEntry<K,V>> table;
    private MapEntry<K,V>[] hashtable;

    public HashMap() {
        table = new ArrayList<MapEntry<K,V>>(tableSize);
        hashtable = new MapEntry[tableSize];
    }

    public void put(K key, V value) {
        int index = hash(key);
        // if index is not occupied yet, then this key is just starting its own chain, but check how full the hashtable is and resize if needed
        if (hashtable[index] == null) {
            // check if resize needed, if so resize and reallocate and then call put again so that this gets into a proper bucket after resize
            if (resizeNeeded()) {
                resizeHashTable();
                put(key, value);
            } else {
                hashtable[index] = new MapEntry<>(key, value);
            }
        }
        // else if index is taken, there is already a chain of keys for this index, check if this key has to be updated or inserted into the chain
        else {
            MapEntry<K,V> nodeAtHead = hashtable[index];
            MapEntry<K,V> node = nodeAtHead;
            boolean existingKeyUpdated = false;
            while (node != null) {
                if (node.key.equals(key)) {
                    node.value = value;
                    existingKeyUpdated = true;
                    break;
                }
                node = node.next;
            }

            if (!existingKeyUpdated) {
                MapEntry<K,V> entry = new MapEntry<>(key, value);
                entry.next = nodeAtHead;
                hashtable[index] = entry;
            }
        }
    }

    public V get(K key) {
        int index = hash(key);
        MapEntry<K,V> node = hashtable[index];

        while(node != null) {
            if (node.key.equals(key)) {
                return node.value;
            }
            node = node.next;
        }
        return null;
    }

    private void resizeHashTable() {
    }

    private boolean resizeNeeded() {
        return false;
    }

    private int hash(K key) {
        return (Math.abs(key.hashCode()))%tableSize;
    }


}
