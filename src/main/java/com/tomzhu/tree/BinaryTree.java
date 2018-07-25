package com.tomzhu.tree;

/**
 * Created by tomzhu on 17-7-15.
 */
public class BinaryTree<E> implements Tree {

    private BinaryTreeNode<E> root;

    /**
     * create a binaryTree using specific value as the root node.
     * @param value
     */
    public BinaryTree(E value) {
        this.root = new BinaryTreeNode<E>(value , null);
    }

    /**
     * create a binaryTree using specific root.
     * @param root
     */
    public BinaryTree(BinaryTreeNode<E> root) {
        this.root = root;
    }

    /**
     * check whether this tree is empty(only when root is null).
     * @return
     */
    public boolean isEmpty() {
        return this.root == null;
    }

    public BinaryTreeNode<E> getRoot() {
        return  this.root;
    }

    /**
     * return whether this node has left sibling.
     * @param node
     * @return
     */
    public boolean hasLeftChild(BinaryTreeNode<E> node) {
        return node.hasLeftChild();
    }

    /**
     * return whether this node has right child.
     * @param node
     * @return
     */
    public boolean hasRightChild(BinaryTreeNode<E> node) {
        return node.hasRightChild();
    }

    /**
     * return whether this node is the root node.
     * @param node
     * @return
     */
    public boolean isRoot(BinaryTreeNode<E> node) {
        return node.isRoot();
    }

//    public boolean isFirstChild() {
//        return
//    }
//
//    public boolean isLastChild() {
//        return false;
//    }

    /**
     * add the left child using specific "value" to the current node.
     * and return the added node.
     * @param node
     * @param value
     * @return
     */
    public BinaryTreeNode<E> addLeftChild(BinaryTreeNode<E> node , E value) {
        return node.addLeftChild(value);
    }

    /**
     * add the right child using specific "value" to the current node.
     * and return the added node
     * @param node
     * @param value
     * @return
     */
    public BinaryTreeNode<E> addRightChild(BinaryTreeNode<E> node , E value) {
        return node.addRightChild(value);
    }

    /**
     * add the leftChild node to the current node as its left child.
     * and return the added node
     * @param node
     * @param leftChild
     * @return
     */
    public BinaryTreeNode<E> addLeftChild(BinaryTreeNode<E> node , BinaryTreeNode<E> leftChild) {
        return node.addLeftChild(leftChild);
    }

    /**
     * add the rightChild node to the current node as its right child
     * and return the added node.
     * @param node
     * @param rightChild
     * @return
     */
    public BinaryTreeNode<E> addRightChild(BinaryTreeNode<E> node ,
                                           BinaryTreeNode<E> rightChild) {
        return node.addRightChild(rightChild);
    }

    /**
     * return the left child of the current node , return null if it doesn't have left child.
     * @param node
     * @return
     */
    public BinaryTreeNode<E> getLeftChild(BinaryTreeNode<E> node) {
        return node.getLeftChild();
    }

    /**
     * return the right child of the current node , return null if it doesn't have right child.
     * @param node
     * @return
     */
    public BinaryTreeNode<E> getRightChild(BinaryTreeNode<E> node) {
        return node.getRightChild();
    }

    /**
     * remove the children node of the current node
     * and then return the current node.
     * @param node
     * @return
     */
    public BinaryTreeNode<E> clearChildren(BinaryTreeNode<E> node) {
        return node.clearChildren();
    }

    /**
     * remove and return the left child of current node.
     * return null if current node doesn't have left child.
     * @param node
     * @return
     */
    public BinaryTreeNode<E> removeLeftChild(BinaryTreeNode<E> node) {
        return node.removeLeftChild();
    }

    /**
     * remove  and return the right child of current node
     * return null if current node doesn't have right child.
     * @param node
     * @return
     */
    public BinaryTreeNode<E> removeRightChild(BinaryTreeNode<E> node) {
        return node.removeRightChild();
    }


    /**
     * return whether the node has children nodes.
     * @param node
     * @return
     */
    public boolean hasChildren(BinaryTreeNode<E> node) {
        return node.hasChildren();
    }

}
