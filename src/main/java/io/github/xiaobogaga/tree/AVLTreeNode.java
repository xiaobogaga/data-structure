package io.github.xiaobogaga.tree;

/**
 * AVLTreeNode represent a node of AVL tree. we redefine a tree node class here but doesn't use
 * the binary tree node, the reason is that a avl tree node has an element called height.
 *
 * @param <E> the type of element.
 *
 * @author tomzhu
 * @since 1.7
 */

public class AVLTreeNode<E extends Comparable<E>> implements Tree{

    private E element;

    private int height;

    private AVLTreeNode<E> lChildl;
    private AVLTreeNode<E> rChild;
    private AVLTreeNode<E> parent;

    /**
     * construct an AVLTreeNode.
     */
    public AVLTreeNode() { }

    /**
     * construct an AVLTree Node with element and height.
     * @param element
     * @param height
     */
    public AVLTreeNode(E element, int height) {
        this.element = element;
        this.height = height;
    }

    /**
     * construct an AVLTreeNode with element, height, left child, right child, parent.
     * @param element
     * @param height
     * @param lChild
     * @param rChild
     * @param parent
     */
    public AVLTreeNode(E element, int height, AVLTreeNode<E> lChild,
                       AVLTreeNode<E> rChild, AVLTreeNode<E> parent) {
        this.height = height;
        this.element = element;
        this.lChildl = lChild;
        this.rChild = rChild;
        this.parent = parent;
    }

    /**
     * @return the element of this node.
     */
    public E getElement() {
        return element;
    }

    /**
     * set the element of this node
     * @param element
     */
    public void setElement(E element) {
        this.element = element;
    }

    /**
     * @return the height of this tree.
     */
    public int getHeight() {
        return height;
    }

    /**
     * set the height of this tree.
     * @param height
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * @return the left child of this tree.
     */
    public AVLTreeNode<E> getlChildl() {
        return lChildl;
    }

    /**
     * set the left child of this tree.
     * @param lChildl
     */
    public void setlChildl(AVLTreeNode<E> lChildl) {
        this.lChildl = lChildl;
    }

    /**
     * @return get the right child of this tree.
     */
    public AVLTreeNode<E> getrChild() {
        return rChild;
    }

    /**
     * set the right child of this tree.
     * @param rChild
     */
    public void setrChild(AVLTreeNode<E> rChild) {
        this.rChild = rChild;
    }

    /**
     * @return the parent node of this tree.
     */
    public AVLTreeNode<E> getParent() {
        return this.parent;
    }

    /**
     * set the parent node of this tree.
     * @param parent
     */
    public void setParent(AVLTreeNode<E> parent) {
        this.parent = parent;
    }

    public String toString() {
        return "[Node : " + element  + " height : " + height + " parent :" + ((parent == null) ? "null" : parent.getElement())
                + " lchild : " + ((lChildl == null) ? "null" : lChildl.getElement()) + " rchild : " +
                ((rChild == null) ?  "null" : rChild.getElement());
    }

}
