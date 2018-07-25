package com.tomzhu.tree;

/**
 * Created by tomzhu on 2017/7/12.
 * a simple treenode interface, since there are many kinds of trees with different tree node attributes.
 * for example , binary tree will have left children treenode , right children treenode and other attributes ,
 * but other tree has different children treenodes.
 */
public abstract class TreeNode<E> {
    public E value; // value can be a user-defined attribute.
    public int childrenSize;

    /**
     * return whether this node has children, if yes, return true, otherwise return false
     * @return
     */
    public boolean hasChildren() {
        return this.childrenSize != 0;
    }

    public abstract boolean hasLeftSibling();
    public abstract boolean hasRightSibling();
    public abstract boolean isRoot(); //whether the node is the root node.
//    public abstract boolean isFirstChild();
//    public abstract boolean isLastChild();

    /**
     * return the children size.
     * @return
     */
    public int getChildrenSize() {
        return this.childrenSize;
    }

    /**
     * return the value of the current node.
     * @return
     */
    public E getValue() {return this.value;}

    public abstract TreeNode<E>[] getChilds(boolean b);
}
