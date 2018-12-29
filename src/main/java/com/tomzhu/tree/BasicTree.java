package com.tomzhu.tree;

import com.tomzhu.list.MyNosuchElementException;
import com.tomzhu.list.MyNotSupportException;

/**
 * Created by tomzhu on 2017/7/12.
 * a basic tree implementation.
 * for a basic tree , each treenode may has different number of children,
 * each treenode will hold a list for a children chain.
 */
public class BasicTree<E> implements Tree {

    private BasicTreeNode<E> root;

    public BasicTree(E root) {
        BasicTreeNode<E> node = new BasicTreeNode<E>(root , null);
        this.root = node;
    }

    /**
     * create a empty tree.
     */
    public BasicTree() {}

    /**
     * return the root node of this tree.
     * @return
     */
    public BasicTreeNode<E> getRoot() {
        return this.root;
    }

    /**
     * return whether the tree is empty.
     * @return
     */
    public boolean isEmpty() {
        return this.root == null;
    }

    /**
     * return whether the node has children nodes.
     * @param node
     * @return
     */
    public boolean hasChildren(BasicTreeNode<E> node) {
        return node.hasChildren();
    }

    /**
     * return whether the node has left sibling node.
     * @param node
     * @return
     */
    public boolean hasLeftSibling(BasicTreeNode<E> node) {
        return node.hasLeftSibling();
    }

    /**
     * return whether the node has right sibling node.
     * @param node
     * @return
     */
    public boolean hasRightSibling(BasicTreeNode<E> node) {
        return node.hasRightSibling();
    }

    /**
     * return whether the node is the root node.
     * @param node
     * @return
     */
    public boolean isRoot(BasicTreeNode<E> node){
        return node.isRoot();
    }

    /**
     * add a child node to the specific node and return the added node.
     * @param value
     * @param node
     * @return
     */
    public BasicTreeNode<E> addChild(BasicTreeNode<E> node , E value) {
        return node.addChild(value);
    }

    /**
     * add a child node to parameter "node" at speficic location, throw @see{com.tomzhu.list.MyNotSupportException}
     * when the location i is not reliable.
     * @param value
     * @param location
     * @param node
     * @return
     * @throws MyNotSupportException
     */
    public BasicTreeNode<E> addChild(BasicTreeNode<E> node , E value , int location) throws MyNotSupportException {
        return node.addChild(location , value);
    }


    /**
     * add the neededNode to the node as it's children. then return the added node namely neededNode.
     * @param neededNode
     * @param node
     * @return
     */
    public BasicTreeNode<E> addChild(BasicTreeNode<E> node , BasicTreeNode<E> neededNode) {
        return node.addChild(neededNode);
    }

    /**
     * add the neededNode to the node as it's children at specific location, then
     * return the added node namely neededNode. throw @see{com.tomzhu.list.MyNotSupportException}
     * @param neededNode
     * @param location
     * @param node
     * @return
     */
    public BasicTreeNode<E> addChild(BasicTreeNode<E> node , BasicTreeNode<E> neededNode , int location)
            throws MyNotSupportException {
        return node.addChild(location , neededNode);
    }

    /**
     * add the neededNodes to the node from left to right.
     * return the node.
     * @param neededNodes
     * @param node
     * @return
     * @throws Exception
     */
    public BasicTreeNode<E> addChildren(BasicTreeNode<E> node , BasicTreeNode<E>[] neededNodes) {
        return node.addChildren(neededNodes);
    }

    /**
     * create needednodes using values and add them to the node from left to right.
     * return the node.
     * @param values
     * @param node
     * @return
     * @throws Exception
     */
    public BasicTreeNode<E> addChildren(BasicTreeNode<E> node , E[] values) {
        return node.addChildren(values);
    }

    /**
     * return whether the node is the first child of its parent.
     * @param node
     * @return
     * @throws Exception
     */
    public boolean isFirstChild(BasicTreeNode<E> node) throws Exception {
        return node.isFirstChild();
    }

    /**
     * return whether the node is the last child of its parent.
     * @param node
     * @return
     * @throws Exception
     */
    public boolean isLastChild(BasicTreeNode<E> node) throws Exception {
        return node.isLastChild();
    }


    /**
     * add left sibling node(create a node using value) to the specific node and return the added node.
     * @param value
     * @param node
     */
    public BasicTreeNode<E> addLeftSibling(BasicTreeNode<E> node , E value) throws MyNotSupportException {
        return node.addLeftSibling(value);
    }

    /**
     * add left sibling node(neededNode) to the speficic node and return the added node.
     * @param neededNode
     * @param node
     * @return
     * @throws Exception
     */
    public BasicTreeNode<E> addLeftSibling(BasicTreeNode<E> node , BasicTreeNode<E> neededNode) throws MyNotSupportException {
        return node.addLeftSibling(neededNode);
    }

    /**
     * add right sibling node to the specific node and return the added node.
     * @param value
     * @param node
     * @return
     */
    public BasicTreeNode<E> addRightSibling(BasicTreeNode<E> node , E value) throws MyNotSupportException {
        return node.addRightSibling(value);
    }

    public BasicTreeNode<E> addRightSibling1(BasicTreeNode<E> node , BasicTreeNode<E> neededNode) throws MyNotSupportException {
        return node.addRightSibling(neededNode);
    }

    /**
     * return the children size of the node.
     * @param node
     * @return
     * @throws Exception
     */
    public int getChildrenSize(BasicTreeNode<E> node) {
        return node.childrenSize;
    }

    /**
     * return the left sibling of the node , if don't have left sibling ,
     * return null.
     * @param node
     * @return
     */
    public BasicTreeNode<E> getLeftSibling(BasicTreeNode<E> node) {
        return node.getLeftSibling();
    }

    /**
     * return the right sibling of the node , if don't have right sibling,
     * return null.
     * @param node
     * @return
     */
    public BasicTreeNode<E> getRightSibling(BasicTreeNode<E> node) {
        return node.getRightSibling();
    }

    /**
     * return the parent of the node , if don't have parent , namely root,
     * then return null.
     * @param node
     * @return
     */
    public BasicTreeNode<E> getParent(BasicTreeNode<E> node) {
        return node.getParent();
    }

    /**
     * return the first child of the node , if don't have children ,
     * return null.
     * @param node
     * @return
     */
    public BasicTreeNode<E> getFirstChild(BasicTreeNode<E> node) {
        return node.getFirstChild();
    }

    /**
     *  return the last child of the node (order is left to right) , if don't have children
     *  return null.
     * @param node
     * @return
     */
    public BasicTreeNode<E> getLastChild(BasicTreeNode<E> node) {
        return node.getLastChild();
    }

    /**
     * return children as an array, leftToRight imply the order.
     * @param node
     * @param leftToRight
     * @return
     */
    public BasicTreeNode<E>[] getChilds(BasicTreeNode<E> node , boolean leftToRight) {
        return node.getChilds(leftToRight);
    }

    /**
     * return the child node at specific location i for the node.
     * @param node
     * @param i
     * @return
     */
    public BasicTreeNode<E> getChild(BasicTreeNode<E> node , int i) {
        return node.getChild(i);
    }

    /**
     * check whether the node has the "child" as its child node.
     * @param child
     * @param node
     * @return
     */
    public boolean containChild(BasicTreeNode<E> node , BasicTreeNode<E> child) {
        return node.containChild(child);
    }

    /**
     * return the child location in node's children from left to right starting at 0.
     * @param child
     * @param node
     * @return
     */
    public int searchChild(BasicTreeNode<E> node , BasicTreeNode<E> child) {
        return node.searchChild(child);
    }

    /**
     * remove the node's first child node and return it , throw @see{com.tomzhu.list.MyNosuchElementException} when
     * the node has no children.
     * @param node
     * @return
     * @throws MyNosuchElementException
     */
    public BasicTreeNode<E> removeLastChild(BasicTreeNode<E> node) throws MyNosuchElementException {
        return node.removeLastChild();
    }

    /**
     * remove the node's last child node and return it, throw @see{com.tomzhu.list.MyNosuchElementException} when
     * the node has no children.
     * @param node
     * @return
     * @throws MyNosuchElementException
     */
    public BasicTreeNode<E> removeFirstChild(BasicTreeNode<E> node) throws MyNosuchElementException {
        return node.removeFirstChild();
    }

    /**
     * remove the i-th child of the node and return the removed child. throw
     * @see{com.tomzhu.list.MyNosuchElementException} when the location i is not reliable.
     * @param node
     * @param i
     * @return
     * @throws MyNosuchElementException
     */
    public BasicTreeNode<E> removeChild(BasicTreeNode<E> node , int i) throws MyNosuchElementException {
        return node.removeChild(i);
    }

    /**
     * remove the removedNode as node's children and return the removedNode. return null if it is not
     * the child of the node. @see{com.tomzhu.list.MyNosuchElementException} is not possible to throw.
     * @see {com.tomzhu.tree.BasicTreeNode} for more understanding.
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
     * @param node
     */
    public BasicTreeNode<E> clearChildren(BasicTreeNode<E> node) {
        return node.clearChildren();
    }


    /**
     * return a iterator using layer travel.
     * @return
     */
//    public TreeIterator<E> getLayerIterator() {
//
//    }

}
