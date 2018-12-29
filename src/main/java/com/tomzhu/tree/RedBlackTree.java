package com.tomzhu.tree;

import java.util.Iterator;
import java.util.Stack;

/**
 * a simple red-black tree implementation.
 *
 * @author tomzhu.
 */
public class RedBlackTree<E extends Comparable<E>>{

    /**
     * a simple red black tree iterator.
     */
    class RedBlackTreeIterator implements Iterator<E> {

        public RedBlackTreeIterator() {
            this.stack = new Stack<Node>();
            if (root != null) {
                stack.push(root);
            }
        }

        private Stack<Node> stack;

        public boolean hasNext() {
            if (stack.isEmpty()) {
                return false;
            }
            return true;
        }

        public E next() {
            Node e = stack.pop();
            if (e != null) {
                if (e.left != null) stack.push(e.left);
                if (e.right != null) stack.push(e.right);
            }
            return e.ele;
        }

        public void remove() {
            // ignore.
        }
    }

    public Iterator<E> iterator () {
        return new RedBlackTreeIterator();
    }

    /**
     * color type.
     */
    enum Color {
        RED, BLACK
    }

    /**
     * node holder.
     */
    class Node {
        E ele;
        Color color;
        Node left;
        Node right;
        Node parent;
        Node next = null; // used for rehash.
        Node prev = null;

        public Node(E ele) {
            this.ele = ele;
            this.color = Color.RED;
        }

        public Node(E ele, Color color) {
            this.ele = ele;
            this.color = color;
        }

        public Node(E ele, Node parent) {
            this.ele = ele;
            this.parent = parent;
            this.color = Color.RED;
        }
        public void reset(Node parent) {
            this.parent = parent;
            this.color = Color.RED;
            this.left = this.right = null;
        }
    }

    protected Node root;
    protected Node head = null;
    protected Node tail = null;

    int size;

    /**
     * insert an ele.
     *
     * @param ele
     * @return return true if success, return false if just replacing existing element.
     */
    public boolean insert(E ele) {
        Node r = this.root;
        if (isEmpty()) {
            this.root = new Node(ele, Color.BLACK);
            tail = head = this.root;
            this.size++;
            return true;
        }
        int t = 0;
        while (r != null) {
            if ((t = r.ele.compareTo(ele)) == 0) {
                r.ele = ele; // replace...
                return false;
            } else if (t < 0) {
                if (r.right == null)
                    break;
                r = r.right;
            } else {
                if (r.left == null)
                    break;
                r = r.left;
            }
        }
        Node n;
        if (t > 0) {
            // the element ele needs to be inserted as the left child of t.
            n = new Node(ele, r);
            r.left = n;
        } else {
            // the ele needs to be inserted as the right child of t.
            n = new Node(ele, r);
            r.right = n;
        }
        if (r.color == Color.RED)
            adjustUpForInsert(r);
        this.size++;
        n.prev = tail;
        tail.next = n;
        tail = n;
        return true;
    }

    protected void insertForRehash(Node n) {
        Node r = this.root;
        if (isEmpty()) {
            this.root = n;
            n.color = Color.BLACK;
            this.size ++;
            return;
        }
        int t = 0;
        while (r != null) {
            t = r.ele.compareTo(n.ele);
            if (t < 0) {
                if (r.right == null)
                    break;
                r = r.right;
            } else {
                if (r.left == null)
                    break;
                r = r.left;
            }
        }
        if (t > 0) {
            n.reset(r);
            r.left = n;
        } else {
            // the ele needs to be inserted as the right child of t.
            n.reset(r);
            r.right = n;
        }
        if (r.color == Color.RED)
            adjustUpForInsert(r);
        this.size ++;
    }

    /**
     * perform a right rotate around upper.
     *
     * @param c
     * @param upper
     */
    private void rightRotate(Node c, Node upper) {
        // perform a right rotation.
        c.parent = upper.parent;
        if (upper.parent != null) {
            if (upper.parent.left == upper) {
                upper.parent.left = c;
            } else {
                upper.parent.right = c;
            }
        }
        upper.parent = c;
        upper.left = c.right;
        if (c.right != null)
            c.right.parent = upper;
        c.right = upper;
    }

    /**
     * perform a left rotate around c.
     *
     * @param c
     * @param lower
     */
    private void leftRotate(Node c, Node lower) {
        lower.parent = c.parent;
        if (c.parent != null) {
            if (c.parent.left == c) {
                c.parent.left = lower;
            } else {
                c.parent.right = lower;
            }
        }
        c.parent = lower;
        if (lower.left != null)
            lower.left.parent = c;
        c.right = lower.left;
        lower.left = c;
    }

    /**
     * adjust the red-black tree property by bottom-up.
     */
    private void adjustUpForInsert(Node c) {
        // if is root node, just set it as black and return.
        while (c.parent != null) {
            Node upper = c.parent;
            Node other = (upper.left == c) ? upper.right : upper.left;
            if (other != null && other.color == Color.RED) {
                c.color = other.color = Color.BLACK;
                upper.color = Color.RED;
                c = upper;
            } else {
                if (upper.left == c) {
                    if (c.right == null || c.right.color == Color.BLACK) {
                        rightRotate(c, upper);
                        c.color = Color.BLACK;
                        upper.color = Color.RED;
                        break;
                    } else {
                        leftRotate(c, c.right);
                        rightRotate(c.parent, upper);
                        c.color = Color.BLACK;
                        c = c.parent;
                    }
                } else {
                    if (c.left == null || c.left.color == Color.BLACK) {
                        leftRotate(upper, c);
                        upper.color = Color.RED;
                        c.color = Color.BLACK;
                        break;
                    } else {
                        rightRotate(c.left, c);
                        leftRotate(upper, c.parent);
                        c.color = Color.BLACK;
                        c = c.parent;
                    }
                }
            }
            if (c.parent != null && c.parent.color == Color.RED)
                c = c.parent;
            else
                break;
        }
        if (c.parent == null) {
            c.color = Color.BLACK;
            this.root = c;
            return;
        }
        this.root.color = Color.BLACK;
    }

    /**
     * verify whether this tree contains the specific element.
     *
     * @param ele
     * @return
     */
    public boolean contains(E ele) {
        Node r = this.root;
        int z;
        while (r != null) {
            if ((z = r.ele.compareTo(ele)) == 0)
                return true;
            else if (z < 0) {
                r = r.right;
            } else {
                r = r.left;
            }
        }
        return false;
    }

    /**
     * @return whether this tree is empty.
     */
    public boolean isEmpty() {
        return this.size == 0;
    }

    /**
     * @return the min element of this tree, null if empty.
     */
    public E getMin() {
        if (isEmpty())
            return null;
        Node r = this.root;
        while (r.left != null)
            r = r.left;
        return r.ele;
    }

    /**
     * @return the max element of this tree, null if empty.
     */
    public E getMax() {
        if (isEmpty())
            return null;
        Node r = this.root;
        while (r.right != null)
            r = r.right;
        return r.ele;
    }

    void removeNode(Node r) {
        if (r.parent == null) {
            // is root node
            this.root = null;
        } else {
            if (r.parent.left == r) {
                r.parent.left = null;
            } else {
                r.parent.right = null;
            }
        }
    }

    Node replaceNode(Node r, Node rep) {
        r.ele = rep.ele;
        if (rep.right != null) {
            rep.right.parent = rep.parent;
            if (rep.parent.left == rep) {
                rep.parent.left = rep.right;
            } else {
                rep.parent.right = rep.right;
            }
            return rep.right;
        } else {
            rep.parent.right = rep.left;
            return rep.left;
        }
    }

    /**
     * adjust for remove routine.
     * @param c
     * @param p
     */
    void adjustForRemove(Node c, Node p) {
        // adjust c and p, p is the parent node of c, note c, p, other all might be null.
        Node brother;
        while (p != null) {
            if (c == p.left) {
                brother = p.right;
                if (brother.color == Color.RED) {
                    leftRotate(p, brother);
                    p.color = Color.RED;
                    brother.color = Color.BLACK;
                    if (brother.parent == null)
                        this.root = brother;
                    brother = p.right;
                }
                if (brother.left == null && brother.right == null) {
                    if (p.color == Color.BLACK) {
                        brother.color = Color.RED;
                        c = p;
                        p = p.parent;
                        continue;
                    } else {
                        p.color = Color.BLACK;
                        brother.color = Color.RED;
                        break;
                    }
                }
                if (brother.left != null && brother.left.color == Color.RED) {
                    brother.left.color = p.color;
                    p.color = Color.BLACK;
                    rightRotate(brother.left, brother);
                    leftRotate(p, brother.parent); // need to check root.
                    if (p.parent.parent == null)
                        this.root = p.parent;
                    break;
                } else if (brother.right != null && brother.right.color == Color.RED) {
                    brother.color = p.color;
                    p.color = Color.BLACK;
                    brother.right.color = Color.BLACK;
                    leftRotate(p, brother); // need to check root.
                    if (brother.parent == null)
                        this.root = brother;
                    break;
                } else {
                    // the children of brother is both black.
                    if (p.color == Color.RED) {
                        p.color = Color.BLACK;
                        brother.color = Color.RED;
                        break;
                    } else {
                        brother.color = Color.RED;
                        c = p;
                        p = p.parent;
                        continue;
                    }
                }
            } else {
                brother = p.left;
                if (brother.color == Color.RED) {
                    p.color = Color.RED;
                    brother.color = Color.BLACK;
                    rightRotate(brother, p);
                    if (brother.parent == null)
                        this.root = brother;
                    brother = p.left;
                }
                if (brother.left == null && brother.right == null) {
                    if (p.color == Color.BLACK) {
                        brother.color = Color.RED;
                        c = p;
                        p = p.parent;
                        continue;
                    } else {
                        p.color = Color.BLACK;
                        brother.color = Color.RED;
                        break;
                    }
                }
                if (brother.left != null && brother.left.color == Color.RED) {
                    brother.color = p.color;
                    p.color = Color.BLACK;
                    brother.left.color = Color.BLACK;
                    rightRotate(brother, p); // need to check root.
                    if (brother.parent == null)
                        this.root = brother;
                    break;
                } else if (brother.right != null && brother.right.color == Color.RED) {
                    brother.right.color = p.color;
                    p.color = Color.BLACK;
                    leftRotate(brother, brother.right);
                    rightRotate(p.left, p); // need to check root.
                    if (p.parent.parent == null)
                        this.root = p.parent;
                    break;
                } else {
                    // the children of brother is both black.
                    if (p.color == Color.RED) {
                        p.color = Color.BLACK;
                        brother.color = Color.RED;
                        break;
                    } else {
                        brother.color = Color.RED;
                        c = p;
                        p = p.parent;
                        continue;
                    }
                }
            }
        }


        if (p == null) {
            this.root = c;
        }

        if (this.root != null)
            this.root.color = Color.BLACK;
    }


    /**
     * remove an element from this tree.
     *
     * @param ele
     * @return true if found, false if the ele is not found.
     */
    public boolean remove(E ele) {
        if (isEmpty())
            return false;
        Node r = this.root;
        int z = 0;
        while (r != null) {
            if (( z = r.ele.compareTo(ele)) == 0) {
                break;
            } else if (z > 0) {
                r = r.left;
            } else
                r = r.right;
        }
        if (r != null) {
            Node c = successorNode(r);
            if (c == null) {
                // node r has no children.
                // just remove this node and will be ok.
                removeNode(r);
                if (r.color == Color.BLACK) {
                    // todo, need adjust
                    adjustForRemove(null, r.parent);
                }
            } else if (c.left == null && c.right == null) {
                // the replacing node r has no children, replace and do adjusting.
                r.ele = c.ele;
                if (c.parent.left == c)
                    c.parent.left = null;
                else
                    c.parent.right = null;
                if (c.color == Color.BLACK) {
                    // todo, need adjust
                    adjustForRemove(null, c.parent);
                }
            } else {
                // since c has only one child, replace r with c,
                // then color r.child as black.
                Node t = replaceNode(r, c);
                t.color = Color.BLACK;
            }

            // adjust list when removing element.
            if (r == this.head) {
              this.head = r.next;
              if (r.next != null) r.next.prev = null;
              else this.tail = null;
            }
            r.prev.next = r.next;
            if (r.next != null) r.next.prev = r.prev;
            else this.tail = r.prev;

            this.size--;
            return true;
        } else {
            return false;
        }
    }

    /**
     * @param r
     * @return
     */
    Node successorNode(Node r) {
        if (r.left == null && r.right == null)
            return null;
        if (r.right == null) {
            r = r.left;
            while (r.right != null)
                r = r.right;
            return r;
        } else {
            r = r.right;
            while (r.left != null)
                r = r.left;
            return r;
        }
    }

    private String visitNode (int level, Node n) {
        StringBuilder s = new StringBuilder("\nLevel[").append(level).append("]:");
        if (n == null) {
            s.append("NIL");
            return s.toString();
        }
        s.append(n.ele).append("(").append(n.color == Color.BLACK ? "B" : "R").append(")");
        s.append(visitNode(level + 1, n.left));
        s.append(visitNode(level + 1, n.right));
        return s.toString();
    }

    public String toString() {
        Node r =  this.root;
        return visitNode(0, r);
    }



}
