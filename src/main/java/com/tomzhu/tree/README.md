a group of tree-liked data structure implementations, including:

* BasicTree : a multi children tree implementation
* BinaryTree : a simple binary tree implementation
* BinarySearchTree : a binary search tree implementation
* AVLTree : a AVL tree implementation
* SplayTree : a splay tree implementation
* RedBlackTree : a red black tree implementation
* BTree : a btree implementation.
* TreeMap : a tree map implementation based on binary search tree
* TreeSet : a tree set implementation based on binary search tree
* HashMap : a hash map implementation based on red black tree.

## basic tree and binary tree.

they are both tree implementation. I think they are not quite hard to implement, so
just provide a little use case here.

**Usage**

```java
//   1
//  /  \
// null null
BasicTree<Integer> tree = new BasicTree<Integer> (1);
tree.getRoot().addChild(2);
tree.getRoot().addChild(3);
tree.getRoot().addChild(4);

//   1
// / | \
// 2 3  4
```

For binary tree:

```java
//   1
//  /  \
// null null
BinaryTree<Integer> tree = new BinaryTree<Integer> (1);
tree.addLeftChild(tree.getRoot(), 2);
tree.addRightChild(tree.getRoot(), 3);

//  1
// / \
// 2  3
```

## Search Tree

Binary Search Tree, AVL tree, Splay Tree, Red-Black Tree, BTree are both search trees.
they exposed similar methods like `insert(E ele)`, `remove(E ele)`, `contains(Ele e)`.

**Usage**

```java
AVLTree<Integer> tree = new AVLTree<Integer> ();
tree.insert(1);
tree.insert(10);
tree.insert(9);

//   10
//  / \
// 1   9

tree.contains(10); // return true
tree.remove(10)
// 1
//  \
//   9
```

## TreeMap and TreeSet
UnLike java TreeMap and TreeSet, the provided TreeMap and TreeSet is based on
binary search tree, and it can be easily changed to red-black tree. TreeSet provides
unique property of elements, however, for simplify, I don't consider provide
iteration method for treeMap, although it is very simple. TreeMap provides
`insert(E k, V v)`, `contains(E ele)`, `remove(E ele)` methods and TreeSet
has `add(E ele)`, `remove(E ele)`, `contains(E ele)`.

**Usage**

```java
TreeMap<Integer, Integer> treeMap = new TreeMap<Integer, Integer> ();
treeMap.insert(1, 1);
treeMap.insert(2, 2);
treeMap.contains(1); // return true
treeMap.remove(1);

TreeSet<Integer> treeSet = new TreeSet<Integer> ();
treeSet.add(1);
treeSet.contains(1); // return true
treeSet.remove(2);
```

## HashMap
Like java HashMap, the hashMap implementation here is based on red-black tree,
but we make their implementation separate. More detail could be found at source
code. HashMap provides some basic functions like `contains(K k)`, `put(K k, V v)`,
`remove(K k)`, `get(K k)`.

**Usage**

```java
HashMap<Integer, Integer> hashMap = new HashMap<Integer, Integer> ()
hashMap.put(1, 2);
hashMap.contains(1); // return true
hashMap.get(1); // return 2
```

see [api doc]() for more information