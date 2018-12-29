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


