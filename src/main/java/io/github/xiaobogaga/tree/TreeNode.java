package io.github.xiaobogaga.tree;

/**
 * a simple tree node interface
 *
 * @param <E> the type of element
 *
 * @author tomzhu
 * @since 1.7
 */
public abstract class TreeNode<E> {
    public E value; // value can be a user-defined attribute.
    public int childrenSize;

    /**
     * @return whether this node has children
     */
    public boolean hasChildren() {
        return this.childrenSize != 0;
    }

    /**
     * @return whether this node has left sibling
     */
    public abstract boolean hasLeftSibling();

    /**
     * @return whether this node has right sibling
     */
    public abstract boolean hasRightSibling();

    /**
     * @return whether this node is tree root node
     */
    public abstract boolean isRoot();

    /**
     * @return the children size.
     */
    public int getChildrenSize() {
        return this.childrenSize;
    }

    /**
     * @return the value of the current node.
     */
    public E getValue() {return this.value;}

    /**
     *
     * @param leftToRight whether left to right.
     * @return the children array
     */
    public abstract TreeNode<E>[] getChilds(boolean leftToRight);

}
