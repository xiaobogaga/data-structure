package com.tomzhu.tree;

/**
 * Created by tomzhu on 18-3-18.
 * AVLTreeNode represent a node of AVL tree. we redefine a tree node class here but doesn't use
 * the binary tree node, the reason is that a avl tree node has an element called height.
 */
public class AVLTreeNode<E extends Comparable<E>> implements Tree{

    private E element;

    private int height;

    private AVLTreeNode<E> lChildl;
    private AVLTreeNode<E> rChild;
    private AVLTreeNode<E> parent;

    public AVLTreeNode() {
    }

    public AVLTreeNode(E element, int height) {
        this.element = element;
        this.height = height;
    }

    public AVLTreeNode(E element, int height, AVLTreeNode<E> lChild,
                       AVLTreeNode<E> rChild, AVLTreeNode<E> parent) {
        this.height = height;
        this.element = element;
        this.lChildl = lChild;
        this.rChild = rChild;
        this.parent = parent;
    }


    public E getElement() {
        return element;
    }

    public void setElement(E element) {
        this.element = element;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public AVLTreeNode<E> getlChildl() {
        return lChildl;
    }

    public void setlChildl(AVLTreeNode<E> lChildl) {
        this.lChildl = lChildl;
    }

    public AVLTreeNode<E> getrChild() {
        return rChild;
    }

    public void setrChild(AVLTreeNode<E> rChild) {
        this.rChild = rChild;
    }

    public AVLTreeNode<E> getParent() {
        return this.parent;
    }

    public void setParent(AVLTreeNode<E> parent) {
        this.parent = parent;
    }

    public String toString() {
        return "[Node : " + element  + " height : " + height + " parent :" + ((parent == null) ? "null" : parent.getElement())
                + " lchild : " + ((lChildl == null) ? "null" : lChildl.getElement()) + " rchild : " +
                ((rChild == null) ?  "null" : rChild.getElement());
    }




}
