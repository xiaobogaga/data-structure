package com.tomzhu.tree;

import java.util.TreeMap;

/**
 * Created by tomzhu on 18-3-18.
 * a AVL tree implementation. this implementation doesn't
 * consider the duplicate element, in other words, this
 * implementation doesn't support insert duplicate element.
 * each element in this tree is unique, but for duplicate elements implementation is
 * also simple, just add a times variable in AVLTreeNode class.
 */
public class AVLTree<E extends Comparable<E>> {

    private AVLTreeNode<E> root;

    public AVLTree() {
    }

    public AVLTree(AVLTreeNode<E> root) {
        this.root = root;
    }

    public AVLTree(E element) {
        this.root = new AVLTreeNode<E>(element, 0);
    }

    /**
     * check whether this tree has the specific element.
     *
     * @return
     */
    public boolean contains(E element) {
        AVLTreeNode<E> node = this.root;
        while (node != null) {
            if (node.getElement().compareTo(element) == 0) {
                // find , just return.
                return true;
            } else if (node.getElement().compareTo(element) < 0) {
                node = node.getrChild();
            } else {
                node = node.getlChildl();
            }
        }
        return false;
    }

    /**
     * @return the max element in this tree. null implies that no such element.
     */
    public E findMax() {
        AVLTreeNode<E> node = root;
        if (node == null) {
            return null;
        }
        while (node.getrChild() != null) {
            node = node.getrChild();
        }
        return node.getElement();

    }

    /**
     * @return the min element in this tree. null implies that no such element.
     */
    public E findMin() {
        AVLTreeNode<E> node = root;
        if (node == null) {
            return null;
        }
        while (node.getlChildl() != null) {
            node = node.getlChildl();
        }
        return node.getElement();
    }

    /**
     * insert a element to tree.
     *
     * @param element
     */
    public void insert(E element) {
        AVLTreeNode<E> node = root;
        if (node == null) {
            this.root = new AVLTreeNode<E>(element, 0);
            return;
        }
        // insert this element.
        // first search for location.
        AVLTreeNode<E> prevNode = null;
        while (node != null) {
            if (node.getElement().compareTo(element) < 0) {
                prevNode = node;
                node = node.getrChild();
            } else {
                prevNode = node;
                node = node.getlChildl();
            }
        }

        node = new AVLTreeNode<E>(element, 0, null, null, prevNode);
        // we can add this element as prevNode's child.
        if (prevNode.getElement().compareTo(element) < 0) {
            // add as rchild
            prevNode.setrChild(node);
        } else {
            // add as lchild
            prevNode.setlChildl(node);
        }

        // after we insert an element, we need balance this tree.
        balance(prevNode);
    }

    /**
     * @param node
     * @return the height of this node, if this node is null, return -1;
     */
    private int getHeight(AVLTreeNode<E> node) {
        return node != null ? node.getHeight() : -1;
    }

    private boolean isNotBalanced(int height1 , int height2) {
        return height1 - height2 > 1 || height2 - height1 > 1;
    }

    private void setHeight(AVLTreeNode<E> node) {
        if (node == null) {
            return;
        }
        node.setHeight(Math.max(getHeight(node.getlChildl()), getHeight(node.getrChild())) + 1);
    }


    /**
     * for case 1, single left child rotation
     * @param parent
     */
    private void rotateLeftChild(AVLTreeNode<E> parent) {
        AVLTreeNode<E> grandPa = parent.getParent();
        AVLTreeNode<E> lchild = parent.getlChildl();
        if (lchild.getrChild() != null) {
            lchild.getrChild().setParent(parent);
        }
        parent.setParent(lchild);
        lchild.setParent(parent.getParent());
        parent.setlChildl(lchild.getrChild());
        lchild.setrChild(parent);
        setHeight(parent);
        setHeight(lchild);
        checkRoot(parent, lchild, grandPa);
    }

    private void checkRoot(AVLTreeNode<E> node , AVLTreeNode<E> newRoot , AVLTreeNode<E> grandParent) {
        if (node == this.root) {
            // we must reset root node
            this.root = newRoot;
        } else {
            if (grandParent.getlChildl() == node) {
                grandParent.setlChildl(newRoot);
            } else {
                grandParent.setrChild(newRoot);
            }
        }
    }


    /**
     * for case 4, single right child rotation.
     * @param parent
     */
    private void rotateRightChild(AVLTreeNode<E> parent) {
        AVLTreeNode<E> grandPa = parent.getParent();
        AVLTreeNode<E> rchild = parent.getrChild();
        if (rchild.getlChildl() != null) {
            rchild.getlChildl().setParent(parent);
        }
        parent.setrChild(rchild.getlChildl());
        rchild.setlChildl(parent);
        rchild.setParent(parent.getParent());
        parent.setParent(rchild);
        setHeight(parent);
        setHeight(parent.getrChild());
        checkRoot(parent, rchild, grandPa);
    }

    /**
     * for case 2, double rotation, left-right
     * @param parent
     */
    private void doubleRotateLeftChild(AVLTreeNode<E> parent) {
        AVLTreeNode<E> lchild = parent.getlChildl();
        AVLTreeNode<E> grandPa = parent.getParent();
        AVLTreeNode<E> lrChild = lchild.getrChild();
        lrChild.setParent(parent.getParent());
        if (lrChild.getlChildl() != null) {
            lrChild.getlChildl().setParent(lchild);
        }
        if (lrChild.getrChild() != null) {
            lrChild.getrChild().setParent(parent);
        }
        lchild.setParent(lrChild);
        parent.setParent(lrChild);
        lchild.setrChild(lrChild.getlChildl());
        parent.setlChildl(lrChild.getrChild());
        lrChild.setlChildl(lchild);
        lrChild.setrChild(parent);

        setHeight(lchild);
        setHeight(parent);
        setHeight(lrChild);
        checkRoot(parent , lrChild, grandPa);
    }

    /**
     * for case 3 , double rotation, right-left rotation.
     * @param parent
     */
    private void doubleRotateRightChild(AVLTreeNode<E> parent) {
        AVLTreeNode<E> grandPa = parent.getParent();
        AVLTreeNode<E> rchild = parent.getrChild();
        AVLTreeNode<E> rlchild = rchild.getlChildl();
        rlchild.setParent(parent.getParent());
        parent.setParent(rlchild);
        rchild.setParent(rlchild);
        if (rlchild.getlChildl() != null) {
            rlchild.getlChildl().setParent(parent);
        }
        if (rlchild.getrChild() != null) {
            rlchild.getrChild().setParent(rchild);
        }

        parent.setrChild(rlchild.getlChildl());
        rchild.setlChildl(rlchild.getrChild());
        rlchild.setlChildl(parent);
        rlchild.setrChild(rchild);

        setHeight(rchild);
        setHeight(parent);
        setHeight(rlchild);
        checkRoot(parent, rlchild, grandPa);
    }

    private void balance(AVLTreeNode<E> node) {
        // balance tree from this node to root.
        AVLTreeNode<E> parent = node;
        int lchildHeight = 0 , rChildHeight = 0;
        while (parent != null) {
            lchildHeight = this.getHeight(parent.getlChildl());
            rChildHeight = this.getHeight(parent.getrChild());
            if (isNotBalanced(lchildHeight, rChildHeight)) {
                // not balanced, need do something.
                if (lchildHeight > rChildHeight) {
                    if (getHeight(parent.getlChildl().getlChildl()) > getHeight(parent.getlChildl().
                            getrChild())) {
                        // single rotation. case 1
                        rotateLeftChild(parent);
                    } else {
                        // double rotation. case 2
                        doubleRotateLeftChild(parent);
                    }
                } else {
                    if (getHeight(parent.getrChild().getlChildl()) > getHeight(parent.getrChild().getrChild())) {
                        // double rotation, case 3
                        doubleRotateRightChild(parent);
                    } else {
                        // single rotation, case 4
                        rotateRightChild(parent);
                    }
                }
                // balance finish
                break;
            } else {
                // balanced.
                setHeight(parent);
            }
            parent = parent.getParent();
        }
    }

    /**
     * remove a node
     * @param node
     * @return the first node needs to do balance.
     */
    private AVLTreeNode doRemoveNode(AVLTreeNode<E> node) {
        AVLTreeNode<E> parent = node.getParent();
        if (node.getlChildl() == null) {
            if (parent.getlChildl() == node) {
                parent.setlChildl(node.getrChild());
                if (node.getrChild() != null) {
                    node.getrChild().setParent(parent);
                }
            } else {
                parent.setrChild(node.getrChild());
                if (node.getrChild() != null) {
                    node.getrChild().setParent(parent);
                }
            }
            return parent;
        } else {
            AVLTreeNode<E> min = node.getlChildl();
            while (min.getlChildl() != null) {
                min = min.getlChildl();
            }
            if (parent.getlChildl() == node) {
                parent.setlChildl(min);
            } else {
                parent.setrChild(min);
            }
            if (node.getlChildl() != min) {
                min.setlChildl(node.getlChildl());
            }
            min.setrChild(node.getrChild());

            if (node.getlChildl() != min) {
                node.getlChildl().setParent(min);
            }

            if (node.getrChild() != null) {
                node.getrChild().setParent(min);
            }

            AVLTreeNode<E> prevParent = min;
            min.setParent(parent);

            // we need to reset the height from [min to node] location.
            return prevParent;
        }

        // help gc
    }


    public boolean remove(E element) {
        AVLTreeNode<E> node = root;
        while (node != null) {
            if (node.getElement().compareTo(element) == 0) {
                // find this element
                break;
            } else if (node.getElement().compareTo(element) < 0) {
                node = node.getrChild();
            } else {
                node = node.getlChildl();
            }
        }

        if (node != null) {
            // find the element, let's do remove, now node.element == element.
            if (node == this.root) {
                // if is the root node, then we must do removeRoot
                this.removeRoot();
            } else {
                AVLTreeNode<E> parent = doRemoveNode(node);
                balance(parent);
            }
            return true;
        } else {
            // doesn't find the element.
            return false;
        }
    }

    private void removeRoot() {
        if (this.root.getlChildl() == null) {
            this.root = this.root.getrChild(); // doesn't need to do promote.
            if (this.root != null) {
                this.root.setParent(null);
            }
        } else {
            AVLTreeNode<E> node = this.root.getlChildl();
            while (node.getrChild() != null) {
                node = node.getrChild();
            }
            // we need to promote the node to root
            AVLTreeNode<E> prevParent = node.getParent();
            if (prevParent != this.root) {
                if (node.getlChildl() != null) {
                    node.getlChildl().setParent(prevParent);
                }
                prevParent.setrChild(node.getlChildl());
                node.setlChildl(this.root.getlChildl());
                node.setrChild(this.root.getrChild());
                this.root.getlChildl().setParent(node);
                if (this.root.getrChild() != null) {
                    this.root.getrChild().setParent(node);
                }
                node.setParent(null);
                this.root = node;
                balance(prevParent);
            } else {
                // root have only one lchild.
                if (this.root.getrChild() != null) {
                    this.root.getrChild().setParent(node);
                }
                node.setrChild(this.root.getrChild());
                this.root = node;
                node.setParent(null);
                balance(this.root);
            }
        }
    }


    public E getRoot() {
        return this.root.getElement();
    }

    public AVLTreeNode<E> getRootNode() {
        return this.root;
    }



}
