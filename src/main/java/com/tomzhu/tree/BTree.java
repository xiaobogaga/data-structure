package com.tomzhu.tree;

import java.util.LinkedList;

/**
 * a simple implementation of btree. this btree cannot save duplicate item
 * so must promise that no duplicate item exist. if really want to save duplicate item
 * , you'd better change this implementation. a simply way may be add a property implies the
 * times of the data.
 *
 * @param <E> the type of element.
 * @author tomzhu
 * @since 1.7
 */

public class BTree<E extends Comparable<E>> implements Tree {

    /**
     * set the mid node element size
     */
    private int Mid_M = 2;

    /**
     * set the leaf node element size.
     */
    private int Leaf_L = 2;

    /**
     * a Node holder
     */
    class Node {
        boolean isLeaf;
        LinkedList<E> leafData; // for leaf
        Node parent;
        LinkedList<KeyItem> midNodeData;

        public Node() {
        }

        public Node(boolean isLeaf, LinkedList<E> leafData, Node parent, LinkedList<KeyItem> midNodeData) {
            this.isLeaf = isLeaf;
            this.leafData = leafData;
            this.parent = parent;
            this.midNodeData = midNodeData;
        }
    }

    /**
     * a key Item holder.
     */
    class KeyItem {
        E key;
        Node child;

        public KeyItem() {
        }

        public KeyItem(E key, Node child) {
            this.key = key;
            this.child = child;
        }

        @Override
        public boolean equals(Object o) {
            if (o == null)
                return false;
            if (o.getClass().isInstance(KeyItem.class)) {
                KeyItem item = (KeyItem) o;
                return this.key.equals(item.key);
            } else {
                return false;
            }
        }
    }

    private Node root;

    /**
     * construct a empty btree with default <tt>mid_m/tt> and <tt>leaf_l</tt> parameters
     */
    public BTree() {
    }

    /**
     * construct a btree with a initial element
     *
     * @param element
     */
    public BTree(E element) {
        LinkedList<E> leafData = new LinkedList<E>();
        leafData.add(element);
        this.root = new Node(true, leafData, null, null);
    }

    /**
     * construct a btree with specific <tt>mid_m</tt>, <tt>leaf_l</tt> parameters and initial element.
     *
     * @param mid_M
     * @param leaf_L
     * @param element
     */
    public BTree(int mid_M, int leaf_L, E element) {
        this.Mid_M = mid_M;
        this.Leaf_L = leaf_L;
        LinkedList<E> leafData = new LinkedList<E>();
        leafData.add(element);
        this.root = new Node(true, leafData, null, null);
    }


    /**
     * check whether contain the specific element.
     *
     * @param element
     * @return <tt>true</tt> if found, <tt>false</tt> otherwise
     */
    public boolean contains(E element) {
        Node node = root;
        while (node != null) {
            if (node.isLeaf) {
                return node.leafData.contains(element);
            } else {
                // if not leaf
                int i = 0;
                for (KeyItem item : node.midNodeData) {
                    if (i < node.midNodeData.size() - 1 && item.key.compareTo(element) <= 0) {
                    } else {
                        node = item.child;
                        break;
                    }
                    i++;
                }
            }
        }
        return false;
    }


    /**
     * return the maximum element in this tree. return <tt>null</tt> if the tree has no nodes.
     *
     * @return the maximum element in this tree.
     */
    public E getMax() {
        Node node = root;
        while (node != null) {
            if (node.isLeaf) {
                return node.leafData.getLast();
            } else {
                node = node.midNodeData.getLast().child;
            }
        }
        return null;
    }

    /**
     * return the minimum element in this tree, return <tt>null</tt> if the tree is empty.
     *
     * @return the minimum element in this tree
     */
    public E getMin() {
        Node node = root;
        while (node != null) {
            if (node.isLeaf) {
                return node.leafData.getFirst();
            } else {
                node = node.midNodeData.getFirst().child;
            }
        }
        return null;
    }

    private void insertElementToLeafListInOrder(LinkedList<E> list, E element) {
        int i = 0;
        for (E e : list) {
            if (e.compareTo(element) >= 0) {
                break;
            }
            i++;
        }
        list.add(i, element);
    }

    private void insertElementToNodeListInOrder(LinkedList<KeyItem> list, KeyItem item) {
        if (item.child.isLeaf) {
            item.key = item.child.leafData.getFirst();
        } else {
            item.key = item.child.midNodeData.getFirst().key;
        }
        int i = 0;
        for (KeyItem keyItem : list) {
            if (keyItem.key == null || keyItem.key.compareTo(item.key) > 0) {
                break;
            }
            i++;
        }
        E key = item.key;
        item.key = list.get(i).key;
        list.get(i).key = key;
        list.add(i + 1, item);
    }

    private E getMinEle(Node node) {
        if (node.isLeaf) {
            return node.leafData.getFirst();
        } else {
            return getMinEle(node.midNodeData.getFirst().child);
        }
    }


    private void insertElementAndSplit(Node node, Object element) {
        if (node == null) {
            // split root node
            if (this.root.isLeaf) {
                LinkedList<KeyItem> list = new LinkedList<KeyItem>();
                Node e = (Node) element;
                Node prevRoot = this.root;
                list.add(new KeyItem(e.leafData.getFirst(), prevRoot));
                list.add(new KeyItem(null, e));
                this.root = new Node(false, null, null, list);
                prevRoot.parent = this.root;
                e.parent = this.root;
            } else {
                LinkedList<KeyItem> list = new LinkedList<KeyItem>();
                Node e = (Node) element;
                Node prevRoot = this.root;
                list.add(new KeyItem(getMinEle(e), prevRoot));
                list.add(new KeyItem(null, e));
                this.root = new Node(false, null, null, list);
                prevRoot.parent = this.root;
                e.parent = this.root;
            }
        } else if (node.isLeaf) {
            // when we add an element to node, it may exceed the maximum size of
            // a leaf node can contain. when this happened, need split.
            // just add, but we must keep the data is in order.
            this.insertElementToLeafListInOrder(node.leafData, (E) element);
            if (node.leafData.size() <= Leaf_L) {
            } else {
                LinkedList<E> leafNode = new LinkedList<E>();
                int start = Leaf_L / 2 + 1, i = 0, length = node.leafData.size() - start;
                while (i < length) {
                    leafNode.add(node.leafData.remove(start));
                    i++;
                }
                Node newNode = new Node(true, leafNode, node.parent, null);
                this.insertElementAndSplit(node.parent, newNode);
            }
        } else {
            // first add.
            KeyItem item = new KeyItem(null, (Node) element);
            this.insertElementToNodeListInOrder(node.midNodeData, item);
            // must check whether to split a midNode
            if (node.midNodeData.size() > Mid_M) {
                // must split
                LinkedList<KeyItem> midNodeData = new LinkedList<KeyItem>();
                Node newNode = new Node(false, null, node.parent, midNodeData);
                int start = Mid_M / 2 + 1, i = 0, length = node.midNodeData.size() - start;
                KeyItem temp;
                while (i < length) {
                    temp = node.midNodeData.remove(start);
                    temp.child.parent = newNode;
                    midNodeData.add(temp);
                    i++;
                }
                node.midNodeData.getLast().key = null;
                this.insertElementAndSplit(node.parent, newNode);
            } else {
                // doesn't need split
            }
        }

    }


    /**
     * insert an element to this tree. return <tt>true</tt> if success and return <tt>false</tt> if duplicate items.
     *
     * @param element
     * @return <tt>true</tt> if success and <tt>false</tt> if duplicate items.
     */
    public boolean insert(E element) {
        if (!isEmpty()) if (this.contains(element)) return false;
        if (root == null) {
            LinkedList<E> data = new LinkedList<E>();
            data.add(element);
            this.root = new Node(true, data, null, null);
            return true;
        }
        Node node = root;
        while (node != null) {
            if (node.isLeaf) {
                this.insertElementAndSplit(node, element);
                return true;
            } else {
                int i = 0;
                for (KeyItem item : node.midNodeData) {
                    if (i < node.midNodeData.size() - 1 && item.key.compareTo(element) <= 0) {
                    } else {
                        node = item.child;
                        break;
                    }
                    i++;
                }
            }
        }
        return true;
    }

    /**
     * try to remove an element from this tree, if success , return <tt>true</tt>
     * otherwise return <tt>false<tt> and this implies that the tree doesn't have the element.
     *
     * @param element
     * @return true if success, false if not found
     */
    public boolean remove(E element) {
        Node node = this.root;
        // KeyItem keyItem = null;
        while (node != null) {
            if (node.isLeaf) {
                boolean success = node.leafData.remove(element);
                if (success) {
                    this.checkAdaption(node);
                    return true;
                } else {
                    return false;
                }
            } else {
                int i = 0;
                for (KeyItem item : node.midNodeData) {
                    if (i < node.midNodeData.size() - 1 && item.key.compareTo(element) <= 0) {
                    } else {
                        node = item.child;
                        // keyItem = item;
                        break;
                    }
                    i++;
                }
            }
        }
        return false;
    }

    private void checkAdaption(Node node) {
        // after remove a element, the leaf may exceed the minimum element size,
        // then we need to do adaption.
        if (node == this.root) {
            // if is root node.
            if (node.isLeaf) {
                // for leaf node
                if (node.leafData.size() == 0) {
                    node = null;
                    this.root = null;
                }
            } else {
                if (node.midNodeData.size() < 2) {
                    // need promote to leaf node.
                    KeyItem item = node.midNodeData.getFirst();
                    // node.isLeaf = item.child.isLeaf;
                    this.root = item.child;
                    this.root.parent = null;
                }
            }
            return;
        }
        if (node.isLeaf) {
            if (node.leafData.size() < Math.ceil(Leaf_L / 2.0)) {
                // do adaption
                doAdaption(node);
            } else {
                // doesn't need.
            }
        } else {
            if (node.midNodeData.size() < Math.ceil(Mid_M / 2.0)) {
                // do adaption
                doAdaption(node);
            } else {
                // doesn't need
            }
        }
    }

    private void doAdaption(Node node) {
        // this node need adapt a node from it's neighborhood.
        if (node.isLeaf) {
            // find two neighbor
            int i = 0;
            for (KeyItem item : node.parent.midNodeData) {
                if (item.child == node) {
                    // it is the node.
                    break;
                }
                i++;
            }

            // we can attempt to adapt child from left or right node.
            // but for simplify, we can adapt only from left neighbor (only except that have no left neighbor)
            // if left neighbor is also in minimum, we can mix this two nodes.
            KeyItem nodeItem = node.parent.midNodeData.get(i);
            KeyItem leftNeighbor = null;
            if (i - 1 >= 0) {
                leftNeighbor = node.parent.midNodeData.get(i - 1);
            }
            if (leftNeighbor == null) {
                KeyItem rightNeighbor = node.parent.midNodeData.get(i + 1);
                if (rightNeighbor.child.leafData.size() <= Math.ceil(Leaf_L / 2.0)) {
                    // must do mix
                    node.parent.midNodeData.remove(i + 1);
                    doMix(nodeItem, rightNeighbor);
                    checkAdaption(node.parent);
                } else {
                    E ele = rightNeighbor.child.leafData.getFirst();
                    rightNeighbor.child.leafData.removeFirst();
                    nodeItem.key = rightNeighbor.child.leafData.getFirst();
                    node.leafData.addLast(ele);
                }
            } else {
                // get one max element from leftNeighbor.
                if (leftNeighbor.child.leafData.size() <= Math.ceil(Leaf_L / 2.0)) {
                    // must do mix
                    node.parent.midNodeData.remove(i);
                    doMix(leftNeighbor, nodeItem);
                    checkAdaption(node.parent);
                } else {
                    // adapt the max from left neighbor
                    E ele = leftNeighbor.child.leafData.getLast();
                    leftNeighbor.child.leafData.removeLast();
                    leftNeighbor.key = ele;
                    node.leafData.addFirst(ele);
                }
            }

        } else {
            // when it's not leaf
            // find two neighbor
            int i = 0;
            for (KeyItem item : node.parent.midNodeData) {
                if (item.child == node) {
                    // it is the node.
                    break;
                }
                i++;
            }

            // we can attempt to adapt child from left or right node.
            // but for simplify, we can adapt only from left neighbor (only except that have no left neighbor)
            // if left neighbor is also in minimum, we can mix this two nodes.
            KeyItem nodeItem = node.parent.midNodeData.get(i);
            KeyItem leftNeighbor = null;
            if (i - 1 >= 0) {
                leftNeighbor = node.parent.midNodeData.get(i - 1);
            }
            if (leftNeighbor == null) {
                KeyItem rightNeighbor = node.parent.midNodeData.get(i + 1);
                if (rightNeighbor.child.midNodeData.size() <= Math.ceil(Mid_M / 2.0)) {
                    // need do mix
                    node.parent.midNodeData.remove(i + 1);
                    doMix(nodeItem, rightNeighbor);
                    checkAdaption(node.parent);
                } else {
                    // get the minimum from rightNeighbor
                    KeyItem temp = rightNeighbor.child.midNodeData.getFirst();
                    rightNeighbor.child.midNodeData.removeFirst();
                    nodeItem.key = this.getMinEle(rightNeighbor.child);
                    nodeItem.child.midNodeData.getLast().key = this.getMinEle(temp.child);
                    temp.key = null;
                    temp.child.parent = nodeItem.child.midNodeData.getFirst().child.parent;
                    nodeItem.child.midNodeData.addLast(temp);
                }
            } else {
                if (leftNeighbor.child.midNodeData.size() <= Math.ceil(Mid_M / 2.0)) {
                    // need do mix
                    node.parent.midNodeData.remove(i);
                    doMix(leftNeighbor, nodeItem);
                    checkAdaption(node.parent);
                } else {
                    KeyItem keyItem = leftNeighbor.child.midNodeData.getLast();
                    leftNeighbor.child.midNodeData.removeLast();
                    leftNeighbor.key = this.getMinEle(keyItem.child);
                    leftNeighbor.child.midNodeData.getLast().key = null;
                    keyItem.key = this.getMinEle(nodeItem.child.midNodeData.getFirst().child);
                    keyItem.child.parent = nodeItem.child.midNodeData.getFirst().child.parent;
                    nodeItem.child.midNodeData.addFirst(keyItem);
                }
            }
        }
    }

    private void doMix(KeyItem leftNode, KeyItem rightNode) {
        // mix two nodeItem
        if (leftNode.child.isLeaf) {
            // for leaf node
            for (E ele : rightNode.child.leafData) {
                leftNode.child.leafData.addLast(ele);
            }
            // when mixed, we should set the parent node
            leftNode.key = rightNode.key;
        } else {
            leftNode.child.midNodeData.getLast().key = getMinEle(rightNode.child);
            for (KeyItem item : rightNode.child.midNodeData) {
                // must set parent of item
                item.child.parent = leftNode.child.midNodeData.getFirst().child.parent;
                leftNode.child.midNodeData.addLast(item);
            }
            leftNode.key = rightNode.key;
        }
    }

    /**
     * used for print btree. only protected by package.
     */
    protected void PrintTree() {
        System.out.println("--> PrintBtree");
        Node node = this.root;
        LinkedList<Node> queue = new LinkedList<Node>();
        queue.addFirst(node);
        while (!queue.isEmpty()) {
            Node temp = queue.removeFirst();
            if (temp == null) {
                continue;
            } else if (temp.isLeaf) {
                StringBuilder build = new StringBuilder();
                for (E ele : temp.leafData) {
                    build.append(" , ").append(ele);
                }
                if (temp.parent != null) {
                    build.append(" , parent : ").append(temp.parent.midNodeData.getFirst().key);
                } else {
                    build.append(" , isRoot");
                }
                System.out.println(build);
            } else {
                // if temp is not leaf
                StringBuilder build = new StringBuilder();
                for (KeyItem item : temp.midNodeData) {
                    build.append(" , ").append("[key : ").append(item.key).append("]");
                    queue.addLast(item.child);
                }
                if (temp.parent != null) {
                    build.append(" , parent : ").append(temp.parent.midNodeData.getFirst().key);
                } else {
                    build.append(" , isRoot");
                }
                System.out.println(build);
            }
        }
        System.out.println("PrintBtree end <--");
    }


    /**
     * @return whether this tree is empty.
     */
    public boolean isEmpty() {
        return this.root == null;
    }
}
