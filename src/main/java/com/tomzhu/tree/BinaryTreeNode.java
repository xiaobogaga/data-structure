package com.tomzhu.tree;

/**
 * simple binary tree node implementation.
 *
 * @param <E> the type of element
 *
 * @author tomzhu
 * @since 1.7
 */
public class BinaryTreeNode<E> extends TreeNode<E> {

    private BinaryTreeNode<E> parent;
    private BinaryTreeNode<E> leftChild;
    private BinaryTreeNode<E> rightChild;

    /**
     * construct a binary tree node using value.
     * @param value
     */
    public BinaryTreeNode(E value) {
        this.value = value;
        this.childrenSize = 0;
        this.parent = this.leftChild = this.rightChild = null;
    }

    /**
     * construct a binary tree node using specific value, parent, left child and right child.
     *
     * @param value
     * @param parent
     * @param leftChild
     * @param rightChild
     */
    public BinaryTreeNode(E value , BinaryTreeNode<E> parent ,
                          BinaryTreeNode<E> leftChild , BinaryTreeNode<E> rightChild) {
        this.value = value;
        this.parent = parent;
        this.leftChild = leftChild;
        this.rightChild = rightChild;
        this.childrenSize += this.leftChild == null ? 0 : 1;
        this.childrenSize +=  this.rightChild == null ? 0 : 1;
    }

    /**
     * construct a binary tree node based on value and parent node.
     *
     * @param value
     * @param parent
     */
    public BinaryTreeNode(E value , BinaryTreeNode<E> parent) {
        this.value = value;
        this.parent = parent;
        this.childrenSize = 0;
        this.leftChild = this.rightChild = null;
    }

    /**
     * @return whether this node has left sibling.
     */
    public boolean hasLeftSibling() {
        return this.parent != null && this.parent.leftChild != null;
    }

    /**
     * @return whether this node has right sibling.
     */
    public boolean hasRightSibling() {
        return this.parent != null && this.parent.rightChild != null;
    }

    /**
     * @return whether this node has left child.
     */
    public boolean hasLeftChild() {
        return this.leftChild != null;
    }

    /**
     * @return whether this node has right child.
     */
    public boolean hasRightChild() {
        return this.rightChild != null;
    }

    /**
     * @return whether this node is the root node.
     */
    public boolean isRoot() {
        return this.parent == null;
    }

    /**
     * @param leftToRight
     * @return an array holds all the children of the current node with left to right order.
     */
    public BinaryTreeNode<E>[] getChilds(boolean leftToRight) {
        if (!hasChildren())
            return new BinaryTreeNode[0];
        BinaryTreeNode<E>[] ar = new BinaryTreeNode[this.childrenSize];
        if (leftToRight) {
            if (this.childrenSize == 2) {
                ar[0] = this.getLeftChild();
                ar[1] = this.getRightChild();
            } else
                ar[0] = this.getLeftChild() == null ? this.getRightChild() : this.getLeftChild();
        } else {
            if (this.childrenSize == 2) {
                ar[0] = this.getRightChild();
                ar[1] = this.getLeftChild();
            } else
                ar[0] = this.getLeftChild() == null ? this.getRightChild() : this.getLeftChild();
        }
        return ar;
    }

    /**
     * add the left child using specific <tt>value</tt> to the current node and return the added node.
     *
     * @param value
     * @return the added node
     */
    public BinaryTreeNode<E> addLeftChild(E value) {
        this.childrenSize++;
        return this.leftChild = new BinaryTreeNode<E>(value , this);
    }

    /**
     * add the right child using specific <tt>value</tt> to the current node and return the added node
     *
     * @param value
     * @return the added node
     */
    public BinaryTreeNode<E> addRightChild(E value) {
        this.childrenSize++;
        return this.rightChild = new BinaryTreeNode<E>(value , this);

    }

    /**
     * add the leftChild node to the current node as its left child and return the added node
     *
     * @param leftChild
     * @return the added node
     */
    public BinaryTreeNode<E> addLeftChild(BinaryTreeNode<E> leftChild) {
        this.childrenSize ++;
        this.leftChild = leftChild;
        this.leftChild.parent = this;
        return this.leftChild;
    }

    /**
     * add the rightChild node to the current node as its right child and return the added node.
     *
     * @param rightChild
     * @return the added node
     */
    public BinaryTreeNode<E> addRightChild(BinaryTreeNode<E> rightChild) {
        this.childrenSize ++;
        this.rightChild = rightChild;
        this.rightChild.parent = this;
        return this.rightChild;
    }

    /**
     * @return the left child of the current node , return <tt>null</tt> if it doesn't have left child.
     */
    public BinaryTreeNode<E> getLeftChild() {
        return this.leftChild;
    }

    /**
     * @return the right child of the current node , return <tt>null</tt> if it doesn't have right child.
     */
    public BinaryTreeNode<E> getRightChild() {
        return this.rightChild;
    }

    /**
     * remove the children node of the current node and then return the current node.
     *
     * @return the current node
     */
    public BinaryTreeNode<E> clearChildren() {
        this.childrenSize = 0;
        this.leftChild = this.rightChild = null;
        return this;
    }

    /**
     * remove and return the left child of current node, return <tt>null</tt> if current node doesn't have left child.
     *
     * @return the left child of current node or <tt>null</tt>
     */
    public BinaryTreeNode<E> removeLeftChild() {
        BinaryTreeNode<E> temp = this.leftChild;
        this.childrenSize -= temp == null ? 0 : 1;
        this.leftChild = null;
        return temp;
    }

    /**
     * remove and return the right child of current node
     * return <tt>null</tt> if current node doesn't have right child.
     *
     * @return the right child of current node or <tt>null</tt>
     */
    public BinaryTreeNode<E> removeRightChild() {
        BinaryTreeNode<E> temp = this.rightChild;
        this.childrenSize -= temp == null ? 0 : 1;
        this.rightChild = null;
        return temp;
    }

    /**
     * @return the parent node of the current node.
     */
    public BinaryTreeNode<E> getParent() { return this.parent; }

    /**
     * set the parent node of the current node and return
     * the parent node.
     *
     * @param newParent
     * @return parent node
     */
    public BinaryTreeNode<E> setParent(BinaryTreeNode<E> newParent) {
        this.parent = newParent;
        return this;
    }

    /**
     * remove the binary tree's child node
     * @param node
     */
    public void removeChild(BinaryTreeNode<E> node) {
        if (node == null)
            return;
        if (this.leftChild == node)
            removeLeftChild();
        else if (this.rightChild == node)
            removeRightChild();
    }

    /**
     * set the current node's left child.
     * @param leftChild
     */
    public void setLeftChild(BinaryTreeNode<E> leftChild) {
        // if (leftChild != null) leftChild.parent = this;
        this.childrenSize += this.hasLeftChild() ? 0 : 1;
        this.leftChild = leftChild;
    }

    /**
     * set the current node's right child.
     * @param rightChild
     */
    public void setRightChild(BinaryTreeNode<E> rightChild) {
        // if (rightChild != null) rightChild.parent = this;
        this.childrenSize += this.hasRightChild() ? 0 : 1;
        this.rightChild = rightChild;
    }

    void setLeftChildWithParent(BinaryTreeNode<E> leftChild) {
        if (leftChild != null) leftChild.parent = this;
        this.childrenSize += this.hasLeftChild() ? 0 : 1;
        this.leftChild = leftChild;
    }

    /**
     * set the current node's right child.
     * @param rightChild
     */
    void setRightChildWithParent(BinaryTreeNode<E> rightChild) {
        if (rightChild != null) rightChild.parent = this;
        this.childrenSize += this.hasRightChild() ? 0 : 1;
        this.rightChild = rightChild;
    }
}
