Yep, another collection library like java collection framework, but it
provides other data structure implementations like Splay Tree, AVL Tree,
Red-black Tree etc. it includes:

* List: </p>
**LinkedList** </p>
**ArrayList** </p>
**Stack** </p>
**Queue**

* Tree: </p>
**Basic Tree** </p>
**Binary Search Tree** </p>
**AVL tree** </p>
**Splay Tree** </p>
**Red-black tree** </p>
**TreeMap** </p>
**HashMap based on Red-black Tree** </p>
**BTree**

* Heap: </p>
**Basic Min-Heap** </p>
**Leftist Heap** </p>
**Skew Heap** </p>
**Binomial Heap** </p>
**DoubleEndedHeap** </p>

* Hash: </p>
**Basic Hash Table by seperate chain** </p>
**Hash Table by linear probing, quadratic probing** </p>
**Perfect Hashing Table** </p>
**Cuckoo Hash Table** </p>
**Hopscotch Hash Table** </p>
**Extensible Hash Table** </p>
**HashMap based on Extensible Hashing** </p>

* Random: </p>
**SkipList** </p>
**Treap**

* String: </p>
**Tries** </p>
**BitMap**

* External memory data structure(they can hold long key and long value): </p>
**HashTable**</p>
**BPlusTree**

# Motivations

The reason I build this project is for learning. I think only when you
really build a collection library, then you might understand the details
about data structures. Most of class provides few but really necessary
functions, the benefit about this is that it can make code clean,
more readable and more extensible. I also make data structures separate,
For example, hashmap could be built on red-black tree array and this is
what java hashmap does, but it turns out java hash map implementation is
very very complicated, if you go into java hash map source code since
jdk 1.8, then you can get 2390 line codes, and my hash map implementation
only has 160 line. This is another reason I built this.

# Documentations
For a detailed api documentation, please ref here.

# Examples

* [List]()

* [Tree]()

* [Heap]()

* [Hash]()

* [Random]()

* [String]()

* [External]()