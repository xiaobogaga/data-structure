package io.github.xiaobogaga.tree;

/**
 * an AVL tree implementation. this implementation doesn't support insert duplicate element.
 * each element in this tree is unique, but for duplicate elements implementation is
 * also simple, just add a times variable in AVLTreeNode class.
 *
 * @param <E> the type of element
 *
 * @author tomzhu
 * @since 1.7
 */
public class AVLTree<E extends Comparable<E>> {

    private AVLTreeNode<E> root;

    /**
     * construct an empty default AVLTree
     */
    public AVLTree() {
    }

    /**
     * construct an AVLTree with element.
     * @param element
     */
    public AVLTree(E element) {
        this.root = new AVLTreeNode<E>(element, 0);
    }

    /**
     * @param element
     * @return whether this tree has the specific element.
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
     * @return the max element in this tree. <tt>null</tt> implies empty tree
     */
    public E getMax() {
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
     * @return the min element in this tree. <tt>null</tt> implies empty tree
     */
    public E getMin() {
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
     * @return true if succeed, false if duplicate items.
     */
    public boolean insert(E element) {
        AVLTreeNode<E> node = root;
        if (node == null) {
            this.root = new AVLTreeNode<E>(element, 0);
            return true;
        }
        // insert this element.
        // first search for location.
        AVLTreeNode<E> prevNode = null;
        while (node != null) {
            int com;
            if ( (com = node.getElement().compareTo(element)) < 0) {
                prevNode = node;
                node = node.getrChild();
            } else if (com == 0) {
                return false;
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
        return true;
    }

    /**
     * @param node
     * @return the height of this node, if this node is <tt>null</tt>, return -1;
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
        lchild.setParent(parent.getParent());
        parent.setParent(lchild);
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
        setHeight(rchild);
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
                // break;
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
        // first, if only has one child, just do promote with child node.
        if (node.getlChildl() == null) {
            if (parent.getlChildl() == node) parent.setlChildl(node.getrChild());
            else parent.setrChild(node.getrChild());
            if (node.getrChild() != null) node.getrChild().setParent(parent);
            return parent;
        } else if (node.getrChild() == null) {
            if (parent.getlChildl() == node) parent.setlChildl(node.getlChildl());
            else parent.setrChild(node.getlChildl());
            if (node.getlChildl() != null) node.getlChildl().setParent(parent);
            return parent;
        } else {
            // replace with the smallest node on the right subtree of node.
            AVLTreeNode<E> min = node.getrChild();
            while (min.getlChildl() != null) {
                min = min.getlChildl();
            }
            if (parent.getlChildl() == node) parent.setlChildl(min);
            else parent.setrChild(min);

            if (min.getParent() != node) {

                min.getParent().setlChildl(min.getrChild());
                if (min.getrChild() != null) min.getrChild().setParent(min.getParent());
                min.setlChildl(node.getlChildl());
                if (node.getlChildl() != null) node.getlChildl().setParent(min);
                min.setrChild(node.getrChild());
                if (node.getrChild() != null) node.getrChild().setParent(min);
                AVLTreeNode<E> n = min.getParent();
                min.setParent(parent);
                return n;
            } else {
                if (node.getlChildl() != null) node.getlChildl().setParent(min);
                min.setlChildl(node.getlChildl());
                min.setParent(parent);
                // we need to reset the height from [min to node] location.
                return min;
            }
        }

        // help gc
    }

    /**
     * try to remove an element from this tree, return true if succeed and return false otherwise.
     * @param element
     * @return true if succeed and return false otherwise.
     */
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

    /**
     * @return the root element of this tree.
     */
    public E getRoot() {
        return this.root.getElement();
    }

    /**
     * @return the root node.
     */
    public AVLTreeNode<E> getRootNode() {
        return this.root;
    }

    /**
     * @return whether this tree is empty
     */
    public boolean isEmpty() {
        return this.root == null;
    }


}
