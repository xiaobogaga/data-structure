package com.tomzhu.tree;

import com.tomzhu.list.MyNosuchElementException;
import com.tomzhu.list.MyNotSupportException;

/**
 * a basic tree implementation.
 * for a basic tree , each tree node may has different number of children,
 *
 * @param <E> the type of element.
 *
 * @author tomzhu
 * @since 1.7
 */
public class BasicTree<E> implements Tree {

    private BasicTreeNode<E> root;

    /**
     * construct a basic tree using element <tt>root</tt>
     * @param root
     */
    public BasicTree(E root) {
        BasicTreeNode<E> node = new BasicTreeNode<E>(root , null);
        this.root = node;
    }

    /**
     * create an empty tree.
     */
    public BasicTree() {}

    /**
     * @return the root node of this tree.
     */
    public BasicTreeNode<E> getRoot() {
        return this.root;
    }

    /**
     * @return whether the tree is empty.
     */
    public boolean isEmpty() {
        return this.root == null;
    }

    /**
     * @param node
     * @return whether the node has children nodes.
     */
    public boolean hasChildren(BasicTreeNode<E> node) {
        return node.hasChildren();
    }

    /**
     * @param node
     * @return whether the node has left sibling node.
     */
    public boolean hasLeftSibling(BasicTreeNode<E> node) {
        return node.hasLeftSibling();
    }

    /**
     * @param node
     * @return whether the node has right sibling node.
     */
    public boolean hasRightSibling(BasicTreeNode<E> node) {
        return node.hasRightSibling();
    }

    /**
     * @param node
     * @return whether the node is the root node.
     */
    public boolean isRoot(BasicTreeNode<E> node){
        return node.isRoot();
    }

    /**
     * add a child node to the specific node and return the added node.
     *
     * @param value
     * @param node
     * @return added node
     */
    public BasicTreeNode<E> addChild(BasicTreeNode<E> node , E value) {
        return node.addChild(value);
    }

    /**
     * add a child node to parameter <tt>node</tt> at specific location, throw {@link MyNotSupportException}
     * when the location is not reliable.
     *
     * @param value
     * @param location
     * @param node
     * @return added node
     * @throws MyNotSupportException
     */
    public BasicTreeNode<E> addChild(BasicTreeNode<E> node , E value , int location) throws MyNotSupportException {
        return node.addChild(location , value);
    }


    /**
     * add the neededNode to the node as it's children. then return the added node namely neededNode.
     *
     * @param neededNode
     * @param node
     * @return added node
     */
    public BasicTreeNode<E> addChild(BasicTreeNode<E> node , BasicTreeNode<E> neededNode) {
        return node.addChild(neededNode);
    }

    /**
     * add the neededNode to the node as it's children at specific location, then
     * return the added node namely neededNode. throw {@link MyNotSupportException}
     *
     * @param neededNode
     * @param location
     * @param node
     * @return added node
     */
    public BasicTreeNode<E> addChild(BasicTreeNode<E> node , BasicTreeNode<E> neededNode , int location)
            throws MyNotSupportException {
        return node.addChild(location , neededNode);
    }

    /**
     * add the neededNodes to the node from left to right. return the node.
     *
     * @param neededNodes
     * @param node
     * @return node
     */
    public BasicTreeNode<E> addChildren(BasicTreeNode<E> node , BasicTreeNode<E>[] neededNodes) {
        return node.addChildren(neededNodes);
    }

    /**
     * create neededNodes using values and add them to the node from left to right.
     * return the node.
     *
     * @param values
     * @param node
     * @return node
     */
    public BasicTreeNode<E> addChildren(BasicTreeNode<E> node , E[] values) {
        return node.addChildren(values);
    }

    /**
     * @param node
     * @return whether the node is the first child of its parent.
     */
    public boolean isFirstChild(BasicTreeNode<E> node) {
        return node.isFirstChild();
    }

    /**
     * @param node
     * @return whether the node is the last child of its parent.
     */
    public boolean isLastChild(BasicTreeNode<E> node) {
        return node.isLastChild();
    }


    /**
     * add left sibling node using value to the specific node and return the added node. throw
     * {@link MyNotSupportException} when the node has a left sibling already.
     *
     * @param value
     * @param node
     * @return the added node
     */
    public BasicTreeNode<E> addLeftSibling(BasicTreeNode<E> node , E value) throws MyNotSupportException {
        return node.addLeftSibling(value);
    }

    /**
     * add left sibling node(neededNode) to the specific node and return the added node. throw
     * {@link MyNotSupportException} when the node has a left sibling already.
     *
     * @param neededNode
     * @param node
     * @return the added node
     * @throws MyNotSupportException
     */
    public BasicTreeNode<E> addLeftSibling(BasicTreeNode<E> node , BasicTreeNode<E> neededNode) throws MyNotSupportException {
        return node.addLeftSibling(neededNode);
    }

    /**
     * add right sibling node to the specific node and return the added node. throw
     * {@link MyNotSupportException} when the node has a right sibling already.
     *
     * @param value
     * @param node
     * @return the added node
     * @throws MyNotSupportException
     */
    public BasicTreeNode<E> addRightSibling(BasicTreeNode<E> node , E value) throws MyNotSupportException {
        return node.addRightSibling(value);
    }

    /**
     * @param node
     * @return the children size of the node.
     */
    public int getChildrenSize(BasicTreeNode<E> node) {
        return node.childrenSize;
    }

    /**
     * @param node
     * @return the left sibling of the node , if don't have left sibling, return <tt>null</tt>
     */
    public BasicTreeNode<E> getLeftSibling(BasicTreeNode<E> node) {
        return node.getLeftSibling();
    }

    /**
     * @param node
     * @return the right sibling of the node , if don't have right sibling, return <tt>null</tt>
     */
    public BasicTreeNode<E> getRightSibling(BasicTreeNode<E> node) {
        return node.getRightSibling();
    }

    /**
     * @param node
     * @return the parent of the node , if don't have parent , namely root, then return <tt>null</tt>
     */
    public BasicTreeNode<E> getParent(BasicTreeNode<E> node) {
        return node.getParent();
    }

    /**
     * @param node
     * @return the first child of the node , if don't have children , return <tt>null</tt>.
     */
    public BasicTreeNode<E> getFirstChild(BasicTreeNode<E> node) {
        return node.getFirstChild();
    }

    /**
     * @param node
     * @return the last child of the node (order is left to right) , if don't have children, return <tt>null</tt>.
     */
    public BasicTreeNode<E> getLastChild(BasicTreeNode<E> node) {
        return node.getLastChild();
    }

    /**
     * return children as an array, leftToRight imply the order.
     *
     * @param node
     * @param leftToRight
     * @return children of node
     */
    public BasicTreeNode<E>[] getChilds(BasicTreeNode<E> node , boolean leftToRight) {
        return node.getChilds(leftToRight);
    }

    /**
     * return the child node at specific location <tt>i</tt> for the node.
     *
     * @param node
     * @param i
     * @return the child node at specific location <tt>i</tt> for the node.
     */
    public BasicTreeNode<E> getChild(BasicTreeNode<E> node , int i) {
        return node.getChild(i);
    }

    /**
     * @param child
     * @param node
     * @return whether the node has the <tt>child</tt> as its child node.
     */
    public boolean containChild(BasicTreeNode<E> node , BasicTreeNode<E> child) {
        return node.containChild(child);
    }

    /**
     * @param child
     * @param node
     * @return the child location in node's children from left to right starting at 0.
     */
    public int searchChild(BasicTreeNode<E> node , BasicTreeNode<E> child) {
        return node.searchChild(child);
    }

    /**
     * remove the node's last child node and return it, throw {@link MyNosuchElementException} when
     * the node has no children.
     *
     * @param node
     * @return the last child
     * @throws MyNosuchElementException
     */
    public BasicTreeNode<E> removeLastChild(BasicTreeNode<E> node) throws MyNosuchElementException {
        return node.removeLastChild();
    }

    /**
     * remove the node's first child node and return it, throw {@link MyNosuchElementException} when
     * the node has no children.
     *
     * @param node
     * @return the first child node
     * @throws MyNosuchElementException
     */
    public BasicTreeNode<E> removeFirstChild(BasicTreeNode<E> node) throws MyNosuchElementException {
        return node.removeFirstChild();
    }

    /**
     * remove the i-th child of the node and return the removed child. throw
     * {@link MyNosuchElementException} when the location <tt>i</tt> is not reliable.
     *
     * @param node
     * @param i
     * @return i-th child of the node.
     * @throws MyNosuchElementException
     */
    public BasicTreeNode<E> removeChild(BasicTreeNode<E> node , int i) throws MyNosuchElementException {
        return node.removeChild(i);
    }

    /**
     * remove the removedNode as node's children and return the removedNode. return <tt>null</tt> if it is not
     * the child of the node. {@link MyNosuchElementException} is not possible to throw.
     * see {@link BasicTreeNode} for more understanding.
     *
     * @param removedNode
     * @param node
     * @return
     * @throws MyNosuchElementException
     */
    public BasicTreeNode<E> removeChild(BasicTreeNode<E> node , BasicTreeNode<E> removedNode)
            throws MyNosuchElementException {
        return node.removeChild(removedNode);
    }

    /**
     * clear the node's children and return the node.
     *
     * @param node
     * @return node
     */
    public BasicTreeNode<E> clearChildren(BasicTreeNode<E> node) {
        return node.clearChildren();
    }

}
