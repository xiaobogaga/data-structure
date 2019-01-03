a group of heap-liked data structure implementations:

* BasicArrayHeap
* BasicDoubleEndedHeap
* BinomialQueue
* LeftistHeap
* SkewHeap

those heap provides basic methods like `insert(E ele)`, `getMax()`,
`getMin()`, `remove()` methods. Most heap has a heap type to define what
heap it is, such as a min heap or a max heap. Specially, BasicDoubleEndedHeap
support min heap and max heap at the same time, it supports `getMax` and `getMin`
method at same time and both are under constant time overhead.

**Usage**

```java
BasicArrayHeap heap = new BasicArrayHeap(BasicArrayHeap.MAXHEAP);
heap.insert(10);
heap.insert(1);
heap.insert(2);
heap.getMax(); // return 10.
heap.getMin(); // return null, doesn't support
```

for more details, see [api doc]().