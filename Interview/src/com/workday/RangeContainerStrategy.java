package com.workday;

/**
 * Created by naveenmurthy on 7/6/16.
 *
 * there can be many 'efficient' implementations of RangeContainer as enumarated below
 * the applications calling can instantiate one or more flavors based on their specific needs and constraints
 *
 */
public enum RangeContainerStrategy {
    MapReduceLinear, MapReduceLogarithmic, TreeBased;
}
