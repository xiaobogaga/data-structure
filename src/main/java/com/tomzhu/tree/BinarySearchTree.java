package com.tomzhu.tree;

/**
 * a simple implementation for binary search tree.
 *
 * @param <E> the type of element
 *
 * @author tomzhu
 * @since 1.7
 */
public class BinarySearchTree<E extends Comparable<E>> implements Tree {

    private BinaryTreeNode<E> root;

    /**
     * create a binarySearchTree using the specific root.
     * @param root
     */
    public BinarySearchTree(E root) {
        this.root = new BinaryTreeNode<E>(root);
    }

    /**
     * create a empty binarySearchTree ( it is simply no root).
     */
    public BinarySearchTree() { this.root = null; }

    /**
     * @param value
     * @return whether this tree has the value.
     */
    public boolean contains(E value) {
        BinaryTreeNode<E> node = this.root;
        while (node != null) {
            if (node.getValue().compareTo(value) == 0) {
                return true;
            } else if (node.getValue().compareTo(value) > 0) {
                node = node.getLeftChild();
            } else {
                node = node.getRightChild();
            }
        }
        return false;
    }

    private BinaryTreeNode<E> searchNode(E value) {
        BinaryTreeNode<E> node = this.root;
        BinaryTreeNode<E> temp = node;
        while (node != null) {
            temp = node;
            if (node.getValue().compareTo(value) == 0) {
                return node;
            } else if (node.getValue().compareTo(value) > 0) {
                node = node.getLeftChild();
            } else {
                node = node.getRightChild();
            }
        }
        return temp;
    }

    /**
     * find the adding node value's parent node recursively.
     * @param node
     * @param value
     * @return
     */
    private BinaryTreeNode<E> searchLocationRecursively(BinaryTreeNode<E> node , E value) {
        if ( node.getValue().compareTo(value) >= 0) {
            return searchLocation(node.getLeftChild() , value);
        } else {
            return searchLocation(node.getRightChild() , value);
        }
    }

    /**
     * find the adding node value's parent node using loop.
     * @param node
     * @param value
     * @return
     */
    private BinaryTreeNode<E> searchLocation(BinaryTreeNode<E> node , E value) {
        if (node == null)
            return null;
        BinaryTreeNode<E> temp = node;
        while (node != null) {
            temp = node;
            if (node.getValue().compareTo(value) >= 0) {
                node = node.getLeftChild();
            } else
                node = node.getRightChild();
        }
        return temp;
    }

    /**
     * @return the max E insides this tree and return null if the tree is empty.
     */
    public E getMax() {
        BinaryTreeNode<E> node = this.root;
        E value = null;
        while (node != null) {
            value = node.getValue();
            node = node.getRightChild();
        }
        return value;
    }

    /**
     * @return the min E insides this tree and return null if the tree is empty
     */
    public E getMin() {
        BinaryTreeNode<E> node = this.root;
        E value = null;
        while (node != null) {
            value = node.getValue();
            node = node.getLeftChild();
        }
        return value;
    }

    private BinaryTreeNode<E> searchMax(BinaryTreeNode<E> node) {
        BinaryTreeNode<E> value = null;
        while (node != null) {
            value = node;
            node = node.getRightChild();
        }
        return value;
    }

    private BinaryTreeNode<E> searchMin(BinaryTreeNode<E> node) {
        BinaryTreeNode<E> value = null;
        while (node != null) {
            value = node;
            node = node.getLeftChild();
        }
        return value;
    }


    /**
     * insert an element value to this tree.
     * @param value
     * @return true if succeed, false if duplicated items.
     */
    public boolean insert(E value) {
        BinaryTreeNode<E> parent = searchLocation(root , value);
        if (parent == null) {
            this.root = new BinaryTreeNode<E>(value);
        } else {
            if (parent.getValue().compareTo(value) > 0) {
                parent.addLeftChild(new BinaryTreeNode<E>(value , parent));
            } else if (parent.getValue().compareTo(value) == 0) {
                return false;
            } else {
                parent.addRightChild(new BinaryTreeNode<E>(value , parent));
            }
        }
        return true;
    }

    /**
     * try to remove the element with value. return true if succeed and false if not found.
     * @param value
     * @return
     */
    public boolean remove(E value) {
        BinaryTreeNode<E> node = searchNode(value);
        if (node != null && node.getValue().compareTo(value) == 0) {
            // finding the removing node.
            if (node.isRoot()) {
                // node is the root node.
                if (node.hasChildren()) {
                    // node has one child.
                    if (node.childrenSize == 1) {
                        this.root = node.getLeftChild() == null ? node.getRightChild() :
                                node.getLeftChild();
                        this.root.setParent(null);
                    } else { // has two children, we replace the removing node with
                             // the smallest child in the node's right subtree.
                        BinaryTreeNode<E> min = searchMin(node.getRightChild());
                        if (min.hasChildren()) { // must have a right child.
                            min.getParent().setLeftChild(min.getRightChild());
                        }
                        this.root = min;
                        min.setParent(null);
                        min.childrenSize = 0;
                        min.setLeftChild(node.getLeftChild());
                        min.setRightChild(node.getRightChild() == min ? null : node.getRightChild());
                    }

                } else {
                    // since has no children , just remove the root.
                    this.root = null;
                }
                return true;
            } else { // the removing node is not the root node.
                if (node.hasChildren()) {
                    // node has one child
                    if (node.childrenSize == 1) {
                        if (node.getParent().getLeftChild() == node) {
                            node.getParent().setLeftChildWithParent(node.getLeftChild() == null ?
                            node.getRightChild() : node.getLeftChild());
                        } else {
                            node.getParent().setRightChildWithParent(node.getRightChild() == null ?
                            node.getLeftChild() : node.getRightChild());
                        }
                    } else { // has two children, we replace the removing node with
                             // the smallest child in the node's right subtree.
                        BinaryTreeNode<E> min = searchMin(node.getRightChild());
                        if (min.hasChildren()) { // must have a right child.
                            min.getParent().setLeftChildWithParent(min.getRightChild());
                        } else min.getParent().setLeftChild(null);
                        if (node.getParent().getLeftChild() == node) {
                            node.getParent().setLeftChildWithParent(min);
                        } else {
                            node.getParent().setRightChildWithParent(min);
                        }
                        min.childrenSize = 0;
                        min.setLeftChildWithParent(node.getLeftChild());
                        min.setRightChildWithParent(node.getRightChild() == min ? null : node.getRightChild());
                    }

                } else {
                    node.getParent().removeChild(node);
                }
            }
            return true;
        } else {
            // there is no node with the specific value
            return false; // represent that there is no node with specific value
        }
    }

    /**
     * @return whether this tree is empty.
     */
    public boolean isEmpty() { return  this.root == null;}

    /**
     * @return the root of this tree.
     */
    public BinaryTreeNode<E> getRoot() { return this.root; }

}
