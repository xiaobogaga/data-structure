package com.tomzhu.tree;

/**
 * a simple Treap structure implementation.
 *
 * @param <K> the type of element
 * @param <P> the priority element
 *
 * @author tomzhu
 * @since 1.7
 */

public class Treap<K extends Comparable<K>, P extends Comparable<P>> {

    private int size;

    /**
     * Node holder.
     */
    class Node {
        P priority;
        K key;
        Node left;
        Node right;
        Node parent;

        public Node(K key, P priority) {
            this.key = key;
            this.priority = priority;
        }

        public Node(K key, P priority, Node parent) {
            this.key = key;
            this.priority = priority;
            this.parent = parent;
        }
    }

    /**
     * Heap type. max heap or min heap.
     */
    enum HeapType {
        MAXHEAP, MINHEAP
    }

    private Node root;
    private HeapType heapType;

    /**
     * construct a treap with given heapType.
     *
     * @param heapType
     */
    public Treap(HeapType heapType) {
        this.heapType = heapType;
    }

    /**
     * insert an element key with given priority p.
     *
     * @param key
     * @param p
     */
    public void insert(K key, P p) {
        if (isEmpty()) {
            this.root = new Node(key, p);
            this.size++;
            return;
        }
        Node r = this.root;
        int z = 0;
        while (true) {
            if ((z = r.key.compareTo(key)) == 0) {
                // replace...
                r.priority = p;
                adjustForRemove(r);
                return;
            } else if (z > 0) {
                if (r.left == null)
                    break;
                else
                    r = r.left;
            } else {
                if (r.right == null)
                    break;
                else
                    r = r.right;
            }
        }
        Node n = new Node(key, p, r);
        if (z < 0) {
            // inserted as r's right child.
            r.right = n;
            adjustForInsert(n);
        } else {
            // inserted as r's left child.
            r.left = n;
            adjustForInsert(n);
        }
        this.size ++;
    }

    /**
     * adjust for insert procedure by bottom-up.
     *
     * @param n
     */
    private void adjustForInsert(Node n) {
        if (isMinHeap()) {
            while (n.parent != null) {
                if (n.parent.priority.compareTo(n.priority) > 0) {
                    if (n.parent.right == n) {
                        leftRotate(n.parent);
                    } else {
                        RightRotate(n.parent);
                    }
                } else {
                    break;
                }
            }
        } else {
            while (n.parent != null) {
                if (n.parent.priority.compareTo(n.priority) < 0) {
                    if (n.parent.right == n) {
                        leftRotate(n.parent);
                    } else {
                        RightRotate(n.parent);
                    }
                } else {
                    break;
                }
            }
        }

        if (n.parent == null)
            this.root = n;
    }

    /**
     * left rotate a node.
     * @param n
     */
    private void leftRotate(Node n) {
        if (n.right != null) {
            Node right = n.right;
            Node parent = n.parent;
            if (parent != null) {
                if (parent.left == n)
                    parent.left = right;
                else
                    parent.right = right;
            }
            right.parent = parent;
            n.parent = right;
            if (right.left != null)
                right.left.parent = n;
            n.right = right.left;
            right.left = n;
        }
    }

    /**
     * right rotate a node.
     * @param n
     */
    private void RightRotate(Node n) {
        if (n.left != null) {
            Node left = n.left;
            Node parent = n.parent;
            if (parent != null) {
                if (parent.left == n)
                    parent.left = left;
                else
                    parent.right = left;
            }
            left.parent = parent;
            n.parent = left;
            if (left.right != null)
                left.right.parent = n;
            n.left = left.right;
            left.right = n;
        }
    }

    /**
     * @return whether this treap is empty.
     */
    public boolean isEmpty() {
        return this.size == 0;
    }

    /**
     * try to remove and return the minimal element of this treap, return <tt>null</tt>
     * if it is a max treap or is empty.
     *
     * @return the minimum element of this treap, return <tt>null</tt> if it is\
     * empty or is a max treap.
     */
    public K removeMin() {
        if (isEmpty() || isMaxHeap())
            return null;
        K k = this.root.key;
        this.remove(this.root.key);
        return k;
    }

    /**
     * try to remove and return the maximum element of this treap. return <tt>null</tt>
     * if it is a min treap or is empty.
     *
     * @return the maximum element of this treap, return <tt>null</tt> if it is\
     * empty or is a min treap
     */
    public K removeMax() {
        if (isEmpty() || isMinHeap())
            return null;
        K k = this.root.key;
        this.remove(this.root.key);
        return k;
    }


    /**
     * remove an element.
     *
     * @param key
     * @return return <tt>true</tt> if success, return <tt>false</tt> if no such element.
     */
    public boolean remove(K key) {
        if (isEmpty())
            return false;
        Node r = this.root;
        int z = 0;
        while (true) {
            if ((z = r.key.compareTo(key)) == 0) {
                break;
            } else if (z > 0) {
                if (r.left == null)
                    return false;
                else
                    r = r.left;
            } else {
                if (r.right == null)
                    return false;
                else
                    r = r.right;
            }
        }

        if (r.left == null || r.right == null) { // has no children or has only one child.
            if (r.parent != null) {
                if (r.parent.left == r) {
                    r.parent.left = (r.left == null) ? r.right : r.left;
                } else {
                    r.parent.right = (r.left == null) ? r.right : r.left;
                }
                if (r.left != null)
                    r.left.parent = r.parent;
                if (r.right != null)
                    r.right.parent = r.parent;
            } else {
                this.root = (r.left == null) ? r.right : r.left;
                if (this.root != null)
                    this.root.parent = null;
            }
        } else {
            // has two children.
            Node s = successorNode(r);
            // repace r by s, and removing previous location s
            if (s.parent.left == s)
                s.parent.left = s.right;
            else
                s.parent.right = s.right;
            if (s.right != null)
                s.right.parent = s.parent;
            r.key = s.key;
            r.priority = s.priority;
            adjustForRemove(r);
        }
        this.size --;
        return true;
    }

    /**
     * adjust for remove.
     * @param n
     */
    private void adjustForRemove(Node n) {
        if (isMinHeap()) {
            if (n.parent == null) { // avoid comparing root.
                Node left = n.left;
                Node right = n.right;
                if (left == null && right == null) {
                    // ignore.
                    return;
                } else if (left == null) {
                    if (right.priority.compareTo(n.priority) < 0) {
                        leftRotate(n);
                        this.root = right;
                    } else {
                        return;
                    }
                } else if (right == null) {
                    if (left.priority.compareTo(n.priority) < 0) {
                        RightRotate(n);
                        this.root = left;
                    } else {
                        return;
                    }
                } else {
                    if (left.priority.compareTo(right.priority) < 0 &&
                            left.priority.compareTo(n.priority) < 0) {
                        RightRotate(n);
                        this.root = left;
                    } else if (right.priority.compareTo(n.priority) < 0) {
                        leftRotate(n);
                        this.root = right;
                    } else {
                        return;
                    }
                }
            }

            while (n != null) {
                Node left = n.left;
                Node right = n.right;
                if (left == null && right == null) {
                    return;
                } else if (left == null) {
                    if (right.priority.compareTo(n.priority) < 0) {
                        leftRotate(n);
                    } else {
                        return;
                    }
                } else if (right == null) {
                    if (left.priority.compareTo(n.priority) < 0) {
                        RightRotate(n);
                    } else {
                        return;
                    }
                } else {
                    if (left.priority.compareTo(right.priority) < 0 &&
                            left.priority.compareTo(n.priority) < 0) {
                        RightRotate(n);
                    } else if (right.priority.compareTo(n.priority) < 0) {
                        leftRotate(n);
                    } else {
                        return;
                    }
                }
            }
        } else {
            if (n.parent == null) { // avoid comparing root.
                Node left = n.left;
                Node right = n.right;
                if (left == null && right == null) {
                    // ignore.
                    return;
                } else if (left == null) {
                    if (right.priority.compareTo(n.priority) > 0) {
                        leftRotate(n);
                        this.root = right;
                    } else {
                        return;
                    }
                } else if (right == null) {
                    if (left.priority.compareTo(n.priority) > 0) {
                        RightRotate(n);
                        this.root = left;
                    } else {
                        return;
                    }
                } else {
                    if (left.priority.compareTo(right.priority) > 0 &&
                            left.priority.compareTo(n.priority) > 0) {
                        RightRotate(n);
                        this.root = left;
                    } else if (right.priority.compareTo(n.priority) > 0){
                        leftRotate(n);
                        this.root = right;
                    } else {
                        return;
                    }
                }
            }

            while (n != null) {
                Node left = n.left;
                Node right = n.right;
                if (left == null && right == null) {
                    return;
                } else if (left == null) {
                    if (right.priority.compareTo(n.priority) > 0) {
                        leftRotate(n);
                    } else {
                        return;
                    }
                } else if (right == null) {
                    if (left.priority.compareTo(n.priority) > 0) {
                        RightRotate(n);
                    } else {
                        return;
                    }
                } else {
                    if (left.priority.compareTo(right.priority) > 0 &&
                            left.priority.compareTo(n.priority) > 0) {
                        RightRotate(n);
                    } else if (right.priority.compareTo(n.priority) > 0) {
                        leftRotate(n);
                    } else {
                        return;
                    }
                }
            }
        }
    }

    /**
     * @param n
     * @return the successor node of n.
     */
    private Node successorNode(Node n) {
        n = n.right;
        while (n.left != null)
            n = n.left;
        return n;
    }

    /**
     * @param key
     * @return whether this treap contains the key.
     */
    public boolean contains(K key) {
        Node r = this.root;
        int z = 0;
        while (r != null) {
            if ((z = r.key.compareTo(key)) == 0) {
                return true;
            } else if (z > 0) {
                if (r.left == null)
                    break;
                else
                    r = r.left;
            } else {
                if (r.right == null)
                    break;
                else
                    r = r.right;
            }
        }
        return false;
    }

    /**
     * @return the min priority element of this heap with O(1), return <tt>null</tt> if empty or is a max heap.
     */
    public K getPriorityMin() {
        return (isMaxHeap() || isEmpty()) ? null : this.root.key;
    }

    /**
     * @return the max priority element of this heap with O(1), return <tt>null</tt> if empty or is a min heap.
     */
    public K getPriorityMax() {
        return (isMinHeap() || isEmpty()) ? null : this.root.key;
    }

    /**
     * @return whether this heap is a min heap.
     */
    public boolean isMinHeap() {
        return this.heapType == HeapType.MINHEAP;
    }

    /**
     * @return whether this heap is a max heap.
     */
    public boolean isMaxHeap() {
        return this.heapType == HeapType.MAXHEAP;
    }

    public String visitNode (Node r, int i) {
        StringBuilder build = new StringBuilder();
        if (r != null) {
            build.append("Level-").append(i).append(":").append(r.key).
                    append(" , ").append(r.priority).append("\n");
            build.append(visitNode(r.left, i + 1));
            build.append(visitNode(r.right, i + 1));
        } else {
            build.append("Level-").append(i).append(":").append("NIL").append("\n");
        }
        return build.toString();
    }

    public String toString() {
        return visitNode(this.root, 0);
    }

}
