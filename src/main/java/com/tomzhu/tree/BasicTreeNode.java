package com.tomzhu.tree;

import com.tomzhu.list.MyNosuchElementException;
import com.tomzhu.list.MyNotSupportException;

/**
 * a tree node implementation for basicTree. it holds a children chain.
 *
 * @param <E> the type of element
 *
 * @author tomzhu
 * @since 1.7
 */
public class BasicTreeNode<E> extends TreeNode<E> {

    public BasicTreeNode<E> leftSibling;

    public BasicTreeNode<E> rightSibling;

    public BasicTreeNode<E> firstChild;

    public BasicTreeNode<E> lastChild;

    public BasicTreeNode<E> parent;

    /**
     * create a basicTreeNode with given value and without children , sibling , parent.
     *
     * @param value
     */
    public BasicTreeNode(E value) {
        this.value = value;
        this.lastChild = this.parent = this.leftSibling = this.rightSibling = this.firstChild = null;
        this.childrenSize = 0;
    }

    /**
     * create a basicTreeNode with given value and parent.
     *
     * @param value
     * @param parent
     */
    public BasicTreeNode(E value , BasicTreeNode<E> parent) {
        this.value = value;
        this.parent = parent;
    }

    /**
     * create a basicTreeNode with given value, parent, first child
     *
     * @param value
     * @param parent
     * @param firstChild
     */
    public BasicTreeNode(E value , BasicTreeNode<E> parent , BasicTreeNode<E> firstChild) {
        this.value = value;
        this.parent = parent;
        this.firstChild = firstChild;
        this.childrenSize = firstChild == null ? 0 : 1;
        this.leftSibling = this.rightSibling = null;
    }

    /**
     * create a basicTreeNode with specific value , parent node , left sibling node , right sibling node , and first child
     * node
     *
     * @param value
     * @param parent
     * @param leftSibling
     * @param rightSibling
     * @param firstChild
     */
    public BasicTreeNode(E value , BasicTreeNode<E> parent , BasicTreeNode<E> leftSibling ,
                         BasicTreeNode<E> rightSibling , BasicTreeNode firstChild) {
        this.value = value;
        this.leftSibling = leftSibling;
        this.rightSibling = rightSibling;
        this.lastChild = this.firstChild = firstChild;
        this.parent = parent;
        this.childrenSize = firstChild == null ? 0 : 1;
    }

    /**
     * @return whether this node has left sibling node.
     */
    public boolean hasLeftSibling() {
        return this.leftSibling != null;
    }

    /**
     * return whether this node has right sibling node.
     * @return
     */
    public boolean hasRightSibling() {
        return this.rightSibling != null;
    }

    /**
     * @return whether this node is the root node.
     */
    public boolean isRoot() {
        return this.parent == null;
    }

    /**
     * create a new node using the specific value and add it to the current node as child then return the added node.
     *
     * @param value
     * @return the added node.
     */
    public BasicTreeNode<E> addChild(E value) {
        BasicTreeNode<E> node = new BasicTreeNode<E>(value , this);
        if (hasChildren()) {
            this.lastChild.rightSibling = node;
            node.leftSibling = this.lastChild;
            this.lastChild = node;
        } else {
            this.firstChild = this.lastChild = node;
        }
        node.parent = this;
        this.childrenSize++;
        return node;
    }

    /**
     * add the node as child to current node and returned the added node.
     * @param node
     * @return the added node
     */
    public BasicTreeNode<E> addChild(BasicTreeNode<E> node) {
        if (hasChildren()) {
            this.lastChild.rightSibling = node;
            node.leftSibling = this.lastChild;
            this.lastChild = node;
        } else {
            this.firstChild = this.lastChild = node;
        }
        node.parent = this;
        this.childrenSize++;
        return node;
    }

    /**
     * add a child node to the current node using specific value
     * in specific location <tt>i</tt> which is left from right and start at 0.
     * throw {@link MyNotSupportException} when the location <tt>i</tt> is not reliable.
     *
     * @param i
     * @param value
     * @return the added node
     * @throws MyNotSupportException
     */
    public BasicTreeNode<E> addChild(int i , E value) throws MyNotSupportException {
        BasicTreeNode<E> node = null;
        if (i > this.childrenSize || i < 0)
            throw new MyNotSupportException("the inserted location is not reliable");
        else if (this.childrenSize == i)
            return addChild(value);
        else if (i == 0) {
            node = new BasicTreeNode<E>(value , this , null , this.firstChild , null);
            this.firstChild.leftSibling = node;
            this.firstChild = node;

        } else {
            BasicTreeNode<E> temp = this.getChild(i);
            node = new BasicTreeNode<E>(value , this , temp.leftSibling , temp , null);
            temp.leftSibling.rightSibling = node;
            temp.rightSibling = node;
        }
        this.childrenSize ++;
        return node;
    }

    /**
     * add a child node to the current node in specific location <tt>i</tt> which is left from right and start at 0.
     * throw {@link MyNotSupportException} when the location <tt>i</tt> is not reliable.
     *
     * @param i
     * @param node
     * @return the added node
     * @throws MyNotSupportException
     */
    public BasicTreeNode<E> addChild(int i , BasicTreeNode<E> node) throws MyNotSupportException {
        if (i > this.childrenSize || i < 0)
            throw new MyNotSupportException("the inserted location is not reliable");
        else if (this.childrenSize == i)
            return addChild(node);
        else if (i == 0) {
            this.firstChild.leftSibling = node;
            node.rightSibling = this.firstChild;
            this.firstChild = node;
        } else {
            BasicTreeNode<E> temp = this.getChild(i);
            node.leftSibling = temp.leftSibling;
            node.rightSibling = temp;
            temp.leftSibling.rightSibling = node;
            temp.rightSibling = node;
        }
        node.parent = this;
        this.childrenSize ++;
        return node;
    }


    /**
     * add the nodes to the current node as children and return the current node.
     *
     * @param nodes
     * @return the current node
     */
    public BasicTreeNode<E> addChildren(BasicTreeNode<E>[] nodes) {
        for(BasicTreeNode<E> n : nodes)
            addChild(n);
        return this;
    }

    /**
     * create nodes using values and add the nodes to the current node as children , then return the current node.
     *
     * @param values
     * @return the current node
     */
    public BasicTreeNode<E> addChildren(E[] values) {
        for(E v : values)
            addChild(v);
        return this;
    }

    /**
     * @return whether this node is it's parent's first child.
     */
    public boolean isFirstChild() {
        if (isRoot())
            return false;
        else
            return this.parent.firstChild == this;
    }

    /**
     * @return whether this node is it's parent's last child.
     */
    public boolean isLastChild() {
        if (isRoot())
            return false;
        else
            return this.parent.lastChild == this;
    }

    /**
     * create a new BasicTreeNode using the specific value and add it to the current node as it's left sibling.
     * then return the added node.
     *
     * @param value
     * @return the added node
     */
    public BasicTreeNode<E> addLeftSibling(E value) throws MyNotSupportException {
        if (this.hasLeftSibling())
            throw new MyNotSupportException("the current node have own a left sibling node");
        BasicTreeNode<E> node = new BasicTreeNode<E>(value , this.parent, null , this , null);
        this.leftSibling = node;
        this.parent.firstChild = node;
        this.parent.childrenSize ++;
        return node;
    }

    /**
     * add the parameter node to the current node as it's left sibling. then return the added node.
     *
     * @param node
     * @return the added node
     */
    public BasicTreeNode<E> addLeftSibling(BasicTreeNode<E> node) throws MyNotSupportException {
        if (this.hasLeftSibling() || node.hasRightSibling()) {
            throw new MyNotSupportException("the current node have own a left sibling node" +
                    " or the adding node already have a right sibling node");
        } else {
            this.leftSibling = node;
            node.rightSibling = this;
            this.parent.firstChild = node;
        }
        node.parent = this.parent;
        this.parent.childrenSize ++;
        return node;
    }

    /**
     * create a new basicTreeNode using the current value and add it to the current node as it's right sibling.
     * then return the node.
     *
     * @param value
     * @return added node
     */
    public BasicTreeNode<E> addRightSibling(E value) throws MyNotSupportException {
        if (this.hasRightSibling())
            throw new MyNotSupportException("the node already have a right sibling node");
        BasicTreeNode<E> node = new BasicTreeNode<E>(value , this.parent , this , null , null);
        this.rightSibling = node;
        this.parent.lastChild = node;
        this.parent.childrenSize ++;
        return node;
    }

    /**
     * add the parameter node to the current node as it's right sibling. then return the node.
     *
     * @param node
     * @return added node
     */
    public BasicTreeNode<E> addRightSibling(BasicTreeNode<E> node) throws MyNotSupportException {
        if (this.hasRightSibling() || node.hasLeftSibling()) {
            throw new MyNotSupportException("the node already have a right sibling node" +
                    " or the adding node already has a left node");
        } else {
            node.leftSibling = this;
            this.rightSibling = node;
            this.parent.lastChild = node;
        }
        node.parent = this.parent;
        this.parent.childrenSize ++;
        return node;
    }

    /**
     * @return the left sibling of the current node
     */
    public BasicTreeNode<E> getLeftSibling() {
        return this.leftSibling;
    }

    /**
     * @return the right sibling of the current node
     */
    public BasicTreeNode<E> getRightSibling() {
        return this.rightSibling;
    }

    /**
     * @return parent of the current node
     */
    public BasicTreeNode<E> getParent() {
        return this.parent;
    }

    /**
     * @return the first child of the current node
     */
    public BasicTreeNode<E> getFirstChild() {
        return this.firstChild;
    }

    /**
     * @return the right child of the current node
     */
    public BasicTreeNode<E> getLastChild() {
        return this.lastChild;
    }

    /**
     * @return the children as array of the current node, which the order is based on the parameter
     * leftToRight , if it is true , then the order is left to right, otherwise inverted.
     */
    public BasicTreeNode<E>[] getChilds(boolean leftToRight) {
        if (!hasChildren())
            return new BasicTreeNode[0];
        if (leftToRight) {
            BasicTreeNode<E>[] nodes = new BasicTreeNode[this.childrenSize];
            BasicTreeNode<E> temp = this.firstChild;
            int i = 0;
            while (i < this.childrenSize) {
                nodes[i] = temp;
                i++;
                temp = temp.rightSibling;
            }
            return nodes;
        } else {
            BasicTreeNode<E>[] nodes = new BasicTreeNode[this.childrenSize];
            BasicTreeNode<E> temp = this.lastChild;
            int i = 0;
            while (i < this.childrenSize) {
                nodes[i] = temp;
                i++;
                temp = temp.leftSibling;
            }
            return nodes;
        }
    }

    /**
     * get the i-th child of the current node.
     * return null if the index i is beyond the size. the support
     * index is like array from 0 to (children size - 1).
     *
     * @param i
     * @return the i-th child of current node
     */
    public BasicTreeNode<E> getChild(int i) {
        if (i >= this.childrenSize || i < 0) {
            return null;
        } else {
            BasicTreeNode<E> temp = this.firstChild;
            int index = 0;
            while (index < i) {
                temp = temp.rightSibling;
                index++;
            }
            return temp;
        }
    }


    /**
     * check whether the node has the specific child node <tt>child</tt>, if yes, return true, otherwise return
     * false. a simple method is just checking {@code child.parent == this} , although user can use this strategy
     * for simplify and performance , the child will not be added to the parent
     * because user can call {@code child.parent = this}.
     *
     * @param child
     * @return
     */
    public boolean containChild(BasicTreeNode<E> child) {
        return searchChild(child) != -1;
    }

    /**
     * search the child node , when find a node then return the found child's location, otherwise return -1;
     * @param child
     * @return
     */
    public int searchChild(BasicTreeNode<E> child) {
        int i = 0;
        BasicTreeNode<E> temp = this.firstChild;
        while (temp != null) {
            if (temp == child)
                return i;
            temp = temp.rightSibling;
            i++;
        }
        return -1;
    }

    /**
     * remove the last child of the current node from left to right.
     * then return the removed child. if the current node doesn't have children,
     * throw {@link MyNosuchElementException}
     *
     * @return the removed child
     * @exception MyNosuchElementException
     */
    public BasicTreeNode<E> removeLastChild() throws MyNosuchElementException {
        if (hasChildren()) {
            BasicTreeNode<E> temp = this.lastChild;
            if (this.firstChild == this.lastChild) {
                this.firstChild = this.lastChild = null;
            } else {
                this.lastChild.leftSibling.rightSibling = null;
                this.lastChild = this.lastChild.leftSibling;
            }
            this.childrenSize --;
            return temp;
        } else {
            throw new MyNosuchElementException("the node has no children");
        }
    }

    /**
     * remove the first child of the current node from left to right.
     * then return the remove child and if the current node doesn't have children,
     * throw {@link MyNosuchElementException}
     *
     * @return the removed first child
     */
    public BasicTreeNode<E> removeFirstChild() throws MyNosuchElementException {
        if (hasChildren()) {
            BasicTreeNode<E> temp = this.firstChild;
            if (this.firstChild == this.lastChild) {
                this.firstChild = this.lastChild = null;
            } else {
                this.firstChild.rightSibling.leftSibling = null;
                this.firstChild = this.firstChild.rightSibling;
            }
            this.childrenSize --;
            return temp;
        } else {
            throw new MyNosuchElementException("the node has no children");
        }
    }

    /**
     * remove the specific child at location <tt>i</tt> and return the removed node. if the location <tt>i</tt> is
     * not reliable, then throw {@link MyNosuchElementException}
     *
     * @param i
     * @return the removed i-th child.
     */
    public BasicTreeNode<E> removeChild(int i) throws MyNosuchElementException {
        if (i < 0 || i >= this.childrenSize)
            throw new MyNosuchElementException("no such element exist in specific location " + i);
        if (i == this.childrenSize - 1)
            return removeLastChild();
        else if (i == 0) {
            return removeFirstChild();
        } else {
            BasicTreeNode<E> temp = getChild(i);
            temp.leftSibling.rightSibling = temp.rightSibling;
            temp.rightSibling.leftSibling = temp.leftSibling;
            this.childrenSize --;
            return temp;
        }
    }

    /**
     * remove the child node <tt>child</tt>. but when the child is not the child of the current node,
     * return <tt>null</tt>, otherwise return the remove child. {@link MyNosuchElementException} is not possible
     * to throw.
     *
     * @param child
     * @return the removed child
     * @throws MyNosuchElementException
     */
    public BasicTreeNode<E> removeChild(BasicTreeNode<E> child) throws MyNosuchElementException {
        int i = searchChild(child);
        if (i == -1)
            return null;
        else
            return removeChild(i);
    }

    /**
     * remove all children nodes and return the current node.
     *
     * @return the current node.
     */
    public BasicTreeNode<E> clearChildren() {
        this.firstChild = this.lastChild = null;
        this.childrenSize = 0;
        return this;
    }

}
