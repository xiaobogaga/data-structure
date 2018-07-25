package com.tomzhu.hash;

import com.tomzhu.list.MyNosuchElementException;
import com.tomzhu.list.MyNotSupportException;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple hash table implementation using seperate Chain.
 * The chain is a list which holds collision elements, actually the chain could be
 * a binary search tree | red-black tree or even a hash table which can construct a
 * multi-level hash table.
 *
 * For hash  table, the load factor is very important since it controls when to extend the hash
 * table and when to perform rehash procedure, for seperate chain strategy, the load factor can be larger than
 * the probing method. In jdk, the default setting of load factor for {@link java.util.HashMap} is 0.75
 * and so are we.
 *
 * @see java.util.HashMap
 * @author tomzhu
 */
public class HashTableBySeperateChain<K, V> {

    private int length = 1 << 4; // default 16.
    private Object[] chains;
    private float loadFactor = 0.75f;
    private int size = 0;

    private class Node {
        private K key;
        private V value;
        // avoid to perform hash computing again.
        private int hash;

        public Node (K key, V value, int hash) {
            this.key = key;
            this.value = value;
            this.hash = hash;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Node node = (Node) o;
            return key != null ? key.equals(node.key) : node.key == null;
        }

        @Override
        public int hashCode() {
            return key != null ? key.hashCode() : 0;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }


    }

    /**
     * initialize a default hash table, with load factor is 0.75, and initialized length is 16.
     */
    public HashTableBySeperateChain() {
        chains = new Object[length];
    }

    /**
     * initialize a hash table by initialCapacity and load factor.
     * @param initialCapacity
     * @param loadFactor
     */
    public HashTableBySeperateChain(int initialCapacity, float loadFactor) {
        this.length = initialCapacity <= 1 ? 1 << 4 : sizeFor(initialCapacity);
        // load factor must range for [0, 1].
        if (loadFactor > 0 && loadFactor < 1)
            this.loadFactor = loadFactor;
        this.chains = new Object[this.length];
    }

    /**
     * return a power of two size.
     * @param capacity
     * @return
     */
    private int sizeFor(int capacity) {
        capacity |= capacity >>> 1;
        capacity |= capacity >>> 2;
        capacity |= capacity >>> 4;
        capacity |= capacity >>> 8;
        capacity |= capacity >>> 16;
        return capacity + 1;
    }


    /**
     * insert an key-value pair.
     * @param key
     * @param value
     */
    public void insert(K key, V value) {
        int h = hash(key);
        int i = h & (this.length - 1);
        if (this.chains[i] == null) {
            this.chains[i] = new Node(key, value, h);
            this.size++;
        } else {
            // iterator the chain to find the element.
            if (this.chains[i] instanceof HashTableBySeperateChain.KeydList) {
                KeydList list = (KeydList) this.chains[i];
                if (list.insert(key, value, h)) {
                    this.size++;
                } else {
                    return;
                }
            } else {
                Node n = (Node) this.chains[i];
                if (n.key.equals(key)) {
                    n.value = value;
                    return;
                } else {
                    // otherwise, change to a keydlist.
                    KeydList list = new KeydList(h);
                    list.insertToTail(n);
                    list.insertToTail(new Node(key, value, h));
                    this.chains[i] = list;
                    this.size++;
                }
            }
        }

        // check to whether do rehash.
        if (this.size > this.length * loadFactor) {
            int oldLength = this.length;
            this.length = this.length << 1;
            Object[] newArr = new Object[this.length];
            // iterator the previous obj to rehash
            Object[] obs = this.chains;
            i = 0;
            while (i < obs.length) {
                if (obs[i] == null) {
                    // ignore
                } else if (obs[i] instanceof HashTableBySeperateChain.Node) {
                    newArr[((Node)obs[i]).hash & (this.length - 1)] = obs[i];
                } else {
                    // must be a list, since these list would be seperate two list node, lets see.
                    KeydList list = (KeydList) obs[i];
                    KeydList newList1 = null;
                    KeydList newList2 = null;
                    int index = 0;
                    Node temp;
                    while (index < list.size) {
                        temp = list.get(index);
                        if ((temp.hash & oldLength) != 0) {
                            if (newList1 == null) {
                                newList1 = new KeydList(temp.hash);
                            }
                            newList1.insertToTail(temp);
                        } else {
                            if (newList2 == null) {
                                newList2 = new KeydList(temp.hash);
                            }
                            newList2.insertToTail(temp);
                        }
                        index++;
                    }
                    if (newList2 != null) {
                        newArr[i] = newList2;
                    }
                    if (newList1 != null) {
                        newArr[i + oldLength] = newList1;
                    }
                }
                i++;
            }
            obs = null;
            this.chains = newArr;
        }
    }

    /**
     * remove a key-value pair from this map.
     * @param key
     * @return true if success, false if doesn't have this key.
     */
    public boolean remove(K key) {
        int i = hash(key) & (this.length - 1);
        if (this.chains[i] == null)
            return false;
        // iterator the chain to find the element.
        if (this.chains[i] instanceof HashTableBySeperateChain.KeydList) {
            KeydList list = (KeydList) this.chains[i];
            if(list.remove(key)){
                this.size--;
                return true;
            } else {
                return false;
            }
        } else {
            if (((Node) this.chains[i]).key.equals(key)) {
                this.chains[i] = null;
                this.size --;
                return true;
            }
            return false;
        }
    }

    /**
     * check whether this map has the key.
     * @param key
     * @return
     */
    public boolean contains(K key) {
        int i = hash(key) & (this.length - 1);
        if (this.chains[i] == null)
            return false;
        // iterator the chain to find the element.
        if (this.chains[i] instanceof HashTableBySeperateChain.KeydList) {
            KeydList list = (KeydList) this.chains[i];
            return list.contains(key);
        } else {
            return ((Node) this.chains[i]).key.equals(key);
        }
    }


    /**
     * return the hash code of the given key.
     * @param key
     * @return
     */
    private int hash(K key) {
        int h;
        h = key == null ? 0 : ((h = key.hashCode()) ^ (h >>> 16));
        return h < 0 ? -h : h;
    }

    private class KeydList {

        private int hash;

        /**
         * a simple node class which holds the before , after link and the node value.
         * @param <E>
         */
        class LNode<E> {

            public E value;
            public LNode<E> before;
            public LNode<E> after;

            public LNode(E value) {
                this.before = this.after = null;
                this.value = value;
            }

            public LNode(E value , LNode<E> before , LNode<E> after) {
                this.value = value;
                this.before = before;
                this.after = after;
            }

            public LNode(E value , LNode<E> before) {
                this.value = value;
                this.before = before;

            }

        }

        private LNode<Node> head;
        private LNode<Node> tail;

        private int size;

        /**
         * initialize a linkedlist
         */
        public KeydList(int hash) {
            this.head = this.tail = null;
            this.hash = hash;
        }

        public boolean isEmpty() {
            return this.head == null;
        }


        /**
         * insert an element to head.
         * @param value
         */
        public void insertToHead(Node value) {
            if (isEmpty()) {
                this.head = new LNode<Node>(value , null , null);
                this.tail = this.head;
                this.size = 1;
            } else {
                LNode<Node> newHead = new LNode(value, null, this.head);
                this.head.before = newHead;
                this.head = newHead;
                this.size++;
            }
        }

        /**
         * insert an element "value" to the specific location "i", throw
         * an MyNotSupportException when the location i is not reliable.
         * @param value
         * @param i
         * @throws MyNotSupportException
         */
        public void insert(Node value , int i) throws MyNotSupportException {
            if (this.size < i)
                throw new MyNotSupportException("the inserting location is not reliable");
            else {
                if (i == 0)
                    insertToHead(value);
                else if (i == this.size)
                    insertToTail(value);
                else {
                    int start = 0;
                    LNode<Node> temp = head;
                    while (start < i) {
                        temp = temp.after;
                        start++;
                    }
                    LNode<Node> newNode = new LNode<Node>(value, temp.before, temp);
                    newNode.before.after = newNode;
                    newNode.after.before = newNode;
                    this.size++;
                }
            }
        }

        /**
         * used by HashTable, insert a node by (k,v) to this list, if has the k, will replace the
         * previous key by new v.
         * @param k
         * @param v
         */
        public boolean insert(K k, V v, int hash) {
            if (isEmpty()) {
                this.insertToHead(new Node(k, v, hash));
                return true;
            }
            if (this.head.value.key.equals(k)) {
                this.head.value.value = v;
                return false;
            }
            LNode<Node> temp = this.head;
            while (temp != null) {
                if (temp.value.key.equals(k)) {
                    temp.value.value = v;
                    return false;
                }
                temp = temp.after;
            }
            this.insertToTail(new Node(k, v, hash));
            return true;
        }

        /**
         * insert an element to the tail.
         * @param value
         */
        public void insertToTail(Node value) {
            if (isEmpty()) {
                this.tail = new LNode<Node>(value , null , null);
                this.head = this.tail;
                this.size = 1;
            } else {
                LNode<Node> newTail = new LNode<Node>(value , this.tail , null);
                this.tail.after = newTail;
                this.tail = newTail;
                this.size++;
            }
        }


        private boolean ensureSize(int i) {
            return this.size > i;
        }

        /**
         * return the element in location "i" , throw MyNosuchElementException when
         * the location i is not reliable.
         * @param i
         * @return
         * @throws MyNosuchElementException
         */
        public Node get(int i){
            if (!ensureSize(i))
                return null;
            else {
                int start = 0;
                LNode<Node> temp = head;
                while(start < i) {
                    temp = temp.after;
                    start++;
                }
                return temp.value;
            }
        }

        /**
         * remove and return the head element , throw MyNosuchElementException when
         * the list is empty.
         * @return
         */
        public Node removeHead() {
            if (isEmpty())
                return null;
            else {
                LNode<Node> temp = this.head;
                if (this.size == 1)
                    this.head = this.tail = null;
                else {
                    this.head = this.head.after;
                    this.head.before = null;
                }
                this.size --;
                return temp.value;
            }
        }

        /**
         * remove and return the tail element , throw MyNosuchElementException when
         * the list is empty.
         * @return
         * @throws MyNosuchElementException
         */
        public Node removeTail() {
            if (isEmpty())
                return null;
            else {
                LNode<Node> temp = this.tail;
                if (this.size == 1) {
                    this.head= this.tail = null;
                } else {
                    this.tail = this.tail.before;
                    this.tail.after = null;
                }
                this.size --;
                return temp.value;
            }
        }

        /**
         * remove and return the element in specific location "i" , throw MyNosuchElementException
         * when the location "i" is not reliable.
         * @param i
         * @return
         * @throws MyNosuchElementException
         */
        public Node remove(int i) throws MyNosuchElementException {
            if (!this.ensureSize(i))
                throw new MyNosuchElementException("there is no such element in the specific location");
            else {
                if (i == 0)
                    return removeHead();
                else if (i == this.size - 1)
                    return removeTail();
                else {
                    LNode<Node> temp = head;
                    int start = 0;
                    while (start < i) {
                        temp = temp.after;
                        start++;
                    }
                    temp.before.after = temp.after;
                    temp.after.before = temp.before;
                    return temp.value;
                }
            }
        }

        /**
         * used by HashTable, remove a node by key.
         * @param key
         * @return
         */
        public boolean remove(K key) {
            if (isEmpty())
                return false;
            if (this.head.value.key.equals(key)) {
                removeHead();
                return true;
            }
            LNode<Node> temp = this.head;
            while (temp != null) {
                if (temp.value.key.equals(key)) {
                    if (temp == this.tail) {
                        removeTail();
                    } else {
                        temp.before.after = temp.after;
                        temp.after.before = temp.before;
                    }
                    return true;
                }
                temp = temp.after;
            }
            return false;
        }

        /**
         * check the list if it has the key node.
         * @param key
         * @return
         */
        public boolean contains(K key) {
            LNode<Node> temp = this.head;
            while (temp != null) {
                if (temp.value.key.equals(key))
                    return true;
                temp = temp.after;
            }
            return false;
        }

        /**
         * clear the list.
         */
        public void clear() {
            this.size = 0;
            this.head = null;
        }

        /**
         * return the current size of the list.
         * @return
         */
        public int getSize() {
            return this.size;
        }



    }

}
