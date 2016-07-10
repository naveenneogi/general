System design considerations:

1. Each container deals with only 32k data items. So, emphasis can be on creating a main data structure and
supplimenting it with additional data structures to facilitate Search/Query operations runtime.

2. In addition, the system can also consider divide and conquering the search operation. This goes back to
the first point of having supporting data structures to facilitate such muilt-search or federated-search

3. As duly noted by the problem statement itself, one could potentially use structures like Treemaps,
Navigablemaps, BinarySearch, etc to facilitate these operations. The system designed here with considers
few such data structures but in addition also breaks the problem down into a 'Divide n Conquer' or
'Map Reduce' pattern to achieve search effeciencies.

4. Sufficient tests have been incuded to support positive, negative cases. The system also supports
a mini stress test of all the algorithms considered.



The system implements the following algorithms and compares them under (mini) stress tests :
MapReduceLinear
MapReduceLogarithmic
TreeMap
BinarySearch

Summary of the mini stress tests:
Threads==4	Strategy==MapReduceLinear DataRange==99,9999 QueryRange==99,9999 Runs==1000, RT(ms)==1.404
Threads==4	Strategy==MapReduceLogarithmic DataRange==99,9999 QueryRange==99,9999 Runs==1000, RT(ms)==2.512
Threads==NA	Strategy==TreeMap DataRange==99,9999 QueryRange==99,9999 Runs==1000, RT(ms)==6.033

Threads==4	Strategy==MapReduceLinear DataRange==99,9999 QueryRange==1,98 Runs==1000, RT(ms)==0.358
Threads==4	Strategy==MapReduceLogarithmic DataRange==99,9999 QueryRange==1,98 Runs==1000, RT(ms)==0.267
Threads==NA	Strategy==TreeMap DataRange==99,9999 QueryRange==1,98 Runs==1000, RT(ms)==0.003


A bit more about the MapReduce algorithm:

The MapReduceRangeContainer implements a Map-Reduce algorithm to partition data across few mappers.
Each mapper supports the range query within its local smaller dataset
As many 'reducer' threads as there are mappers will be spawned
these reducers then invoke the mappers range query
then the resultsets are combined and returned as an iterator for the application

The idea behind this Map-Reduce algo is to allow for threads within the container to operate on distinct datasets
and hence speed up the search queries by a facor of N, N being the number of mappers/reducers/threads

The search operation within each Mapper itself can be implemented in multiple ways - linear, logarithmic, etc
depending on the internal data structure we end up using - arrays, skiplists, treemaps, treaps, etc
Likewise, we can have different extensions to MapReduceRangeContainer depending on the internal data structures
these extensions use, for eg: MapReduceLinear, MapReduceLogarithmic

And a bit more stress test analysis of the two MapReduce algorithms:
Threads==1 	Strategy==MapReduceLinear DataRange==99,9999 QueryRange==99,9999 Runs==1000, RT(ms)==1.399
Threads==4	Strategy==MapReduceLinear DataRange==99,9999 QueryRange==99,9999 Runs==1000, RT(ms)==1.404
Threads==10 Strategy==MapReduceLinear DataRange==99,9999 QueryRange==99,9999 Runs==1000, RT(ms)==1.591
Threads==20 Strategy==MapReduceLinear DataRange==99,9999 QueryRange==99,9999 Runs==1000, RT(ms)==2.405

Threads==1 	Strategy==MapReduceLinear DataRange==99,9999 QueryRange==1,98 Runs==1000, RT(ms)==0.194
Threads==4	Strategy==MapReduceLinear DataRange==99,9999 QueryRange==1,98 Runs==1000, RT(ms)==0.358
Threads==10 Strategy==MapReduceLinear DataRange==99,9999 QueryRange==1,98 Runs==1000, RT(ms)==0.614
Threads==20 Strategy==MapReduceLinear DataRange==99,9999 QueryRange==1,98 Runs==1000, RT(ms)==1.016

Threads==1 	Strategy==MapReduceLogarithmic DataRange==99,9999 QueryRange==99,9999 Runs==1000, RT(ms)==6.57
Threads==4	Strategy==MapReduceLogarithmic DataRange==99,9999 QueryRange==99,9999 Runs==1000, RT(ms)==2.512
Threads==10 Strategy==MapReduceLogarithmic DataRange==99,9999 QueryRange==99,9999 Runs==1000, RT(ms)==2.86
Threads==20 Strategy==MapReduceLogarithmic DataRange==99,9999 QueryRange==99,9999 Runs==1000, RT(ms)==2.801

Threads==1 	Strategy==MapReduceLogarithmic DataRange==99,9999 QueryRange==1,98 Runs==1000, RT(ms)==0.091
Threads==4	Strategy==MapReduceLogarithmic DataRange==99,9999 QueryRange==1,98 Runs==1000, RT(ms)==0.267
Threads==10 Strategy==MapReduceLogarithmic DataRange==99,9999 QueryRange==1,98 Runs==1000, RT(ms)==0.57
Threads==20 Strategy==MapReduceLogarithmic DataRange==99,9999 QueryRange==1,98 Runs==1000, RT(ms)==1.077