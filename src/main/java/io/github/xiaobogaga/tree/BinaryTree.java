package io.github.xiaobogaga.tree;

/**
 * simple binary tree implementation
 *
 * @param <E> the type of element
 *
 * @author tomzhu
 * @since 1.7
 */
public class BinaryTree<E> implements Tree {

    private BinaryTreeNode<E> root;

    /**
     * create a binaryTree using specific value as the root node.
     *
     * @param value
     */
    public BinaryTree(E value) {
        this.root = new BinaryTreeNode<E>(value , null);
    }

    /**
     * create a binaryTree using specific root.
     *
     * @param root
     */
    public BinaryTree(BinaryTreeNode<E> root) {
        this.root = root;
    }

    /**
     * @return whether this tree is empty
     */
    public boolean isEmpty() {
        return this.root == null;
    }

    /**
     * @return the root node of this tree
     */
    public BinaryTreeNode<E> getRoot() {
        return  this.root;
    }

    /**
     * @param node
     * @return whether this node has left sibling.
     */
    public boolean hasLeftChild(BinaryTreeNode<E> node) {
        return node.hasLeftChild();
    }

    /**
     * @param node
     * @return whether this node has right child.
     */
    public boolean hasRightChild(BinaryTreeNode<E> node) {
        return node.hasRightChild();
    }

    /**
     * @param node
     * @return whether this node is the root node.
     */
    public boolean isRoot(BinaryTreeNode<E> node) {
        return node.isRoot();
    }

    /**
     * add the left child using specific <tt>value</tt> to the current node.
     * and return the added node.
     *
     * @param node
     * @param value
     * @return the added node
     */
    public BinaryTreeNode<E> addLeftChild(BinaryTreeNode<E> node , E value) {
        return node.addLeftChild(value);
    }

    /**
     * add the right child using specific <tt>value</tt> to the current node.
     * and return the added node
     *
     * @param node
     * @param value
     * @return the added node
     */
    public BinaryTreeNode<E> addRightChild(BinaryTreeNode<E> node , E value) {
        return node.addRightChild(value);
    }

    /**
     * add the leftChild node to the current node as its left child.
     * and return the added node
     *
     * @param node
     * @param leftChild
     * @return the added node
     */
    public BinaryTreeNode<E> addLeftChild(BinaryTreeNode<E> node , BinaryTreeNode<E> leftChild) {
        return node.addLeftChild(leftChild);
    }

    /**
     * add the rightChild node to the current node as its right child
     * and return the added node.
     *
     * @param node
     * @param rightChild
     * @return the added node
     */
    public BinaryTreeNode<E> addRightChild(BinaryTreeNode<E> node ,
                                           BinaryTreeNode<E> rightChild) {
        return node.addRightChild(rightChild);
    }

    /**
     * @param node
     * @return the left child of the current node , return <tt>null</tt> if it doesn't have left child.
     */
    public BinaryTreeNode<E> getLeftChild(BinaryTreeNode<E> node) {
        return node.getLeftChild();
    }

    /**
     * @param node
     * @return the right child of the current node , return <tt>null</tt> if it doesn't have right child.
     */
    public BinaryTreeNode<E> getRightChild(BinaryTreeNode<E> node) {
        return node.getRightChild();
    }

    /**
     * remove the children node of the current node
     * and then return the current node.
     *
     * @param node
     * @return the current node
     */
    public BinaryTreeNode<E> clearChildren(BinaryTreeNode<E> node) {
        return node.clearChildren();
    }

    /**
     * remove and return the left child of current node.
     * return <tt>null</tt> if current node doesn't have left child.
     *
     * @param node
     * @return return the left child of current node and return <tt>null</tt> if current node doesn't have left child.
     */
    public BinaryTreeNode<E> removeLeftChild(BinaryTreeNode<E> node) {
        return node.removeLeftChild();
    }

    /**
     * remove and return the right child of current node
     * return <tt>null</tt> if current node doesn't have right child.
     *
     * @param node
     * @return the right child of current node return <tt>null</tt> if current node doesn't have right child.
     */
    public BinaryTreeNode<E> removeRightChild(BinaryTreeNode<E> node) {
        return node.removeRightChild();
    }


    /**
     * @param node
     * @return whether the node has children nodes.
     */
    public boolean hasChildren(BinaryTreeNode<E> node) {
        return node.hasChildren();
    }

}
