java collection implementations for the data structures talked about in
the book **DATA STRUCTURES AND ALGORITHM ANALYSIS IN JAVA, THIRD EDITION**

[![Build Status](https://travis-ci.org/xiaobogaga/data-structure.svg?branch=master)](https://travis-ci.org/xiaobogaga/data-structure)
[![Javadocs](https://www.javadoc.io/badge/io.github.xiaobogaga/datastructure.svg)](https://www.javadoc.io/doc/io.github.xiaobogaga/datastructure)
[![Maven Central](https://img.shields.io/maven-central/v/io.github.xiaobogaga/datastructure.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22io.github.xiaobogaga%22%20AND%20a:%22datastructure%22)

Yep, this can be seen as another collection library like java collection
framework, but it provides other data structure implementations like
Splay Tree, AVL Tree, Red-black Tree etc. it includes:

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
**TreeSet**</p>
**HashMap based on Red-black Tree** </p>
**BTree** </p>
**SkipList** </p>
**Treap** </p>
**Tries**

* Heap: </p>
**Basic Min-Heap** </p>
**Leftist Heap** </p>
**Skew Heap** </p>
**Binomial Heap** </p>
**DoubleEndedHeap**

* Hash: </p>
**Basic Hash Table by seperate chain** </p>
**Hash Table by linear probing, quadratic probing** </p>
**Perfect Hash Table** </p>
**Cuckoo Hash Table** </p>
**Hopscotch Hash Table** </p>
**Extensible Hash Table**

* BitMap: </p>
**BitMap**

* External memory data structure(they can hold long key and long value): </p>
**HashTable**</p>
**BPlusTree**

# Features

Most of class provides few but really necessary functions, the benefit about this is that it
can make code clean, more readable and more extensible. I also make
data structures separate, For example, hashmap could be built on
red-black tree array and this is what java hashmap does, but it turns
out java hash map implementation is very very complicated, if you go
into java hash map source code since jdk 1.8, then you can get 2390 line
codes, and my hash map implementation only has 160 line. This is
another reason I built this.

# Documentations
For a detailed api documentation, please go to api docs.

# Examples

* [List](https://github.com/xiaobogaga/data-structure/tree/master/src/main/java/io/github/xiaobogaga/list)

* [Tree](https://github.com/xiaobogaga/data-structure/tree/master/src/main/java/io/github/xiaobogaga/tree)

* [Heap](https://github.com/xiaobogaga/data-structure/tree/master/src/main/java/io/github/xiaobogaga/heap)

* [Hash](https://github.com/xiaobogaga/data-structure/tree/master/src/main/java/io/github/xiaobogaga/hash)

* [BitMap](https://github.com/xiaobogaga/data-structure/tree/master/src/main/java/io/github/xiaobogaga/bitmap)

* [External](https://github.com/xiaobogaga/data-structure/tree/master/src/main/java/io/github/xiaobogaga/external)
