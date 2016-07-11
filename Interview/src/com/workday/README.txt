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

5. Synchronization/Locking: I have deliberately left out synchronization/locking issues since one of the
patterns I am designing for is for this system to be read/query heavy with insertions happening infrequently.
So, I do not want penalize the reads/queries by introducing synch/lock patterns. Of note, however, is that
the skipList implementation of mapR is indeed ConcurrentSkipLists, but its usage here does not cause any
locking/starvation since each thread has its own ConcurrentSkipLists and not shared across.


The system implements the following algorithms and compares them under (mini) stress tests :
MapReduceLinear
MapReduceLogarithmic
TreeMap
BinarySearch

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


Summary of the mini stress tests:
DataRange==99,9999 QueryRange==99,9999 Runs==1000
	Threads==1
		Strategy==TreeMap , RT(ms)==6.042
		Strategy==MapReduceLinear , RT(ms)==1.076
		Strategy==MapReduceLogarithmic , RT(ms)==6.922
	Threads==4
		Strategy==TreeMap , RT(ms)==6.01
		Strategy==MapReduceLinear , RT(ms)==1.119
		Strategy==MapReduceLogarithmic , RT(ms)==2.609
	Threads==10
		Strategy==TreeMap  RT(ms)==6.181
		Strategy==MapReduceLinear RT(ms)==1.649
		Strategy==MapReduceLogarithmic RT(ms)==2.413
	Threads==20
		Strategy==TreeMap  RT(ms)==6.108
		Strategy==MapReduceLinear RT(ms)==2.252
		Strategy==MapReduceLogarithmic RT(ms)==2.737



DataRange==99,9999 QueryRange==4999,14999 Runs==1000
	Threads==1
		Strategy==TreeMap RT(ms)==2.739
		Strategy==MapReduceLinear RT(ms)==0.845
		Strategy==MapReduceLogarithmic RT(ms)==3.266
	Threads==4
		Strategy==TreeMap RT(ms)==2.654
		Strategy==MapReduceLinear RT(ms)==0.7
		Strategy==MapReduceLogarithmic RT(ms)==1.265
	Threads==10
		Strategy==TreeMap RT(ms)==2.806
		Strategy==MapReduceLinear RT(ms)==1.316
		Strategy==MapReduceLogarithmic RT(ms)==1.399
	Threads==20
		Strategy==TreeMap RT(ms)==2.652
		Strategy==MapReduceLinear RT(ms)==1.52
		Strategy==MapReduceLogarithmic RT(ms)==1.882



DataRange==99,9999 QueryRange==1,98 Runs==1000
	Threads==1
		Strategy==TreeMap , RT(ms)==0.003
		Strategy==MapReduceLinear , RT(ms)==0.212
		Strategy==MapReduceLogarithmic , RT(ms)==0.206
	Threads==4
		Strategy==TreeMap , RT(ms)==0.002
		Strategy==MapReduceLinear , RT(ms)==0.46
		Strategy==MapReduceLogarithmic , RT(ms)==0.294
	Threads==10
		Strategy==TreeMap RT(ms)==0.003
		Strategy==MapReduceLinear RT(ms)==0.684
		Strategy==MapReduceLogarithmic RT(ms)==0.656
	Threads==20
		Strategy==TreeMap RT(ms)==0.003
		Strategy==MapReduceLinear RT(ms)==1.116
		Strategy==MapReduceLogarithmic RT(ms)==1.172
