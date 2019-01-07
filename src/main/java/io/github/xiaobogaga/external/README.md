some external memory data structure implementations including:

* HashTable
* BPlusTree

those data structure would save key value pairs on disk, but for simplify,
they only can save long key and long type value. they both has some basic
methods like `addOrUpdate(long key, long value)`, `contains(long key)`,
`get(long key)` , `remove(long key)` and `close()` methods.

these structures are efficient, since we save data by mapped file which uses
page cache to do real saving, and this makes these structures has some kind
of fault tolerant, say if application stopped suddenly, those data would
still be saved safely by operating system.


**Usage**

```java
BPlusTree bplusTree = new BPlusTree("/home/data"); // specific a path to save data
bplusTree.addOrUpdate(1, 10);
bplusTree.contains(1); // return true
bplusTree.close();
```

go to test file for more usages.