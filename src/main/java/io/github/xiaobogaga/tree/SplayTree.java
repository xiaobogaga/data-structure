package io.github.xiaobogaga.tree;

/**
 * a splay tree implementation. this tree cannot save duplicate
 * element, so you cannot insert duplicate element to this tree.
 * for duplicate splaying tree, we can keep a times element in tree
 * node to implies the existing times of element.
 *
 * @param <E> the type of element
 *
 * @author tomzhu
 * @since 1.7
 */
public class SplayTree<E extends Comparable<E>> implements Tree{

    private BinaryTreeNode<E> root;

    /**
     * construct a default splay tree
     */
    public SplayTree() {}

    /**
     * construct a splay tree with element as root.
     *
     * @param element
     */
    public SplayTree(E element) {
        this.root = new BinaryTreeNode<E>(element);
    }

    protected BinaryTreeNode<E> getRootNode() {
        return this.root;
    }

    /**
     * @return root element of this tree. return <tt>null</tt> if empty.
     */
    public E getRoot() {
        return this.root == null ? null : this.root.getValue();
    }

    /**
     * @param element
     * @return whether the tree contains the specific element
     */
    public boolean contains(E element) {
        BinaryTreeNode<E> node = root;
        while (node != null) {
            if (node.getValue().compareTo(element) == 0) {
                break;
            } else if (node.getValue().compareTo(element) < 0) {
                node = node.getRightChild();
            } else {
                node = node.getLeftChild();
            }
        }

        if (node == null){
            return false;
        } else {
            rotate(node);
            return true;
        }
    }

    /**
     * @return max element in this tree and <tt>null</tt> for empty tree.
     */
    public E getMax() {
        BinaryTreeNode<E> node = root;
        while (node != null && node.getRightChild() != null) {
            node = node.getRightChild();
        }
        if (node == null) {
            return null;
        } else {
            rotate(node);
            return this.root.getValue();
        }
    }

    /**
     * @return min element in this tree and <tt>null</tt> for empty tree.
     */
    public E getMin() {
        BinaryTreeNode<E> node = root;
        while (node != null && node.getLeftChild() != null) {
            node = node.getLeftChild();
        }
        if (node == null) {
            return null;
        } else {
            //do rotate
            rotate(node);
            return this.root.getValue();
        }
    }

    /**
     * insert an element. if have a same element in this tree, just return.
     * @param element
     * @return true if succeed , false if duplicate items.
     */
    public boolean insert(E element) {
        BinaryTreeNode<E> node = root;
        BinaryTreeNode<E> parent = null;
        while (node != null) {
            if (node.getValue().compareTo(element) == 0) {
                // find a same element. just return, doesn't insert.
                return false;
            } else if (node.getValue().compareTo(element) < 0) {
                parent = node;
                node = node.getRightChild();
            } else {
                parent = node;
                node = node.getLeftChild();
            }
        }

        if (parent == null) {
            // just insert this element as the root;
            this.root = new BinaryTreeNode<E>(element);
            return true;
        }

        // otherwise, we need insert this element as the child of parent node.
        if (parent.getValue().compareTo(element) < 0) {
            BinaryTreeNode<E> n = new BinaryTreeNode<E>(element , parent , null , null);
            parent.setRightChild(n);
        } else {
            BinaryTreeNode<E> n = new BinaryTreeNode<E>(element , parent , null , null);
            parent.setLeftChild(n);
        }
        return true;
    }

    /**
     * do rotate from this node.
     * @param node
     */
    private void rotate(BinaryTreeNode<E> node) {
        while (node != null && node != this.root) {
            if (node.getParent() == this.root) {
                // rule 1
                BinaryTreeNode<E> lchild = node.getLeftChild();
                BinaryTreeNode<E> rchild = node.getRightChild();
                if (node.getParent().getLeftChild() == node) {
                    node.setRightChild(node.getParent());
                    node.getParent().setLeftChild(rchild);
                    if (rchild != null)
                        rchild.setParent(node.getParent());
                    node.getParent().setParent(node);
                    node.setParent(null);
                    this.root = node;
                } else {
                    node.setLeftChild(node.getParent());
                    node.getParent().setRightChild(lchild);
                    if (lchild != null)
                        lchild.setParent(node.getParent());
                    node.getParent().setParent(node);
                    node.setParent(null);
                    this.root = node;
                }
            } else {
                BinaryTreeNode<E> parent = node.getParent();
                BinaryTreeNode<E> grandParent = parent.getParent();
                if (grandParent.getLeftChild() == parent && parent.getLeftChild() == node) {
                    // do zig-zig rotate
                    node.setParent(grandParent.getParent());
                    if (parent.getRightChild() != null)
                        parent.getRightChild().setParent(grandParent);
                    if (node.getRightChild() != null)
                        node.getRightChild().setParent(parent);
                    parent.setParent(node);
                    if (grandParent.getParent() != null) {
                        // set child for grandParent;
                        if (grandParent.getParent().getLeftChild() == grandParent) {
                            grandParent.getParent().setLeftChild(node);
                        } else {
                            grandParent.getParent().setRightChild(node);
                        }
                    } else {
                        this.root = node;
                    }
                    grandParent.setParent(parent);
                    grandParent.setLeftChild(parent.getRightChild());
                    parent.setLeftChild(node.getRightChild());
                    parent.setRightChild(grandParent);
                    node.setRightChild(parent);
                } else if (grandParent.getRightChild() == parent && parent.getRightChild() == node) {
                    // do zig-zig rotate
                    node.setParent(grandParent.getParent());
                    if (parent.getLeftChild() != null)
                        parent.getLeftChild().setParent(grandParent);
                    if (node.getLeftChild() != null)
                        node.getLeftChild().setParent(parent);
                    parent.setParent(node);
                    if (grandParent.getParent() != null) {
                        // set child for grandParent;
                        if (grandParent.getParent().getLeftChild() == grandParent) {
                            grandParent.getParent().setLeftChild(node);
                        } else {
                            grandParent.getParent().setRightChild(node);
                        }
                    } else {
                        this.root = node;
                    }
                    grandParent.setParent(parent);
                    grandParent.setRightChild(parent.getLeftChild());
                    parent.setLeftChild(grandParent);
                    parent.setRightChild(node.getLeftChild());
                    node.setLeftChild(parent);
                } else if (grandParent.getLeftChild() == parent && parent.getRightChild() == node) {
                    // do zig-zag rotate
                    node.setParent(grandParent.getParent());
                    if (node.getLeftChild() != null)
                        node.getLeftChild().setParent(parent);
                    if (node.getRightChild() != null)
                        node.getRightChild().setParent(grandParent);
                    parent.setParent(node);
                    if (grandParent.getParent() != null) {
                        // set child for grandParent;
                        if (grandParent.getParent().getLeftChild() == grandParent) {
                            grandParent.getParent().setLeftChild(node);
                        } else {
                            grandParent.getParent().setRightChild(node);
                        }
                    } else {
                        this.root = node;
                    }
                    grandParent.setParent(node);
                    grandParent.setLeftChild(node.getRightChild());
                    parent.setRightChild(node.getLeftChild());
                    node.setLeftChild(parent);
                    node.setRightChild(grandParent);
                } else {
                    // do zig-zag rotate
                    node.setParent(grandParent.getParent());
                    if (node.getLeftChild() != null) {
                        node.getLeftChild().setParent(grandParent);
                    }
                    if (node.getRightChild() != null) {
                        node.getRightChild().setParent(parent);
                    }
                    parent.setParent(node);
                    if (grandParent.getParent() != null) {
                        // set child for grandParent;
                        if (grandParent.getParent().getLeftChild() == grandParent) {
                            grandParent.getParent().setLeftChild(node);
                        } else {
                            grandParent.getParent().setRightChild(node);
                        }
                    } else {
                        this.root = node;
                    }
                    grandParent.setParent(node);
                    grandParent.setRightChild(node.getLeftChild());
                    parent.setLeftChild(node.getRightChild());
                    node.setLeftChild(grandParent);
                    node.setRightChild(parent);
                }

            }
        }
    }

    /**
     * attempt to remove an element from the tree, true implies success,
     * false implies that the tree doesn't have the element.
     *
     * @param element
     * @return true if succeed and false if no such element found.
     */
    public boolean remove(E element) {
        if (this.contains(element)) {
            // contain the element, have promote the element to root
            if (this.root.hasLeftChild()) {
                // if have left child. we do following
                BinaryTreeNode<E> rTree = this.root.getRightChild();
                this.root.setRightChild(null);
                // do promote the leftTree;
                this.root.getLeftChild().setParent(null);
                this.root = this.root.getLeftChild();
                this.getMax(); // do find Max,// and the maxElement would promote to root.
                this.root.setRightChild(rTree);
                if (rTree != null)
                    rTree.setParent(this.root);
            } else {
                // just remove the element.
                if (this.root.getRightChild() != null)
                    this.root.getRightChild().setParent(null);
                this.root = this.root.getRightChild();
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * @return whether this tree is empty
     */
    public boolean isEmpty() {
        return this.root == null;
    }


}
