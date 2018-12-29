package com.tomzhu.tree.iterator;

/**
 * Created by tomzhu on 17-7-20.
 */

import com.tomzhu.list.Queue;
import com.tomzhu.tree.TreeNode;

import java.util.Iterator;

/**
 * a simple layer travel implementation for the tree.
 * @param <E>
 */
class LayerIterator<E> implements Iterator<TreeNode<E>> {

    private Queue<TreeNode<E>> queue;

    public LayerIterator(TreeNode<E> root) {
        queue = new Queue<TreeNode<E>>();
        queue.enQueue(root);
    }

    public boolean hasNext() {
        return !queue.isEmpty();
    }

    public TreeNode<E> next() {
        TreeNode<E> node = queue.deQueue();
        for (TreeNode<E> n : node.getChilds(true))
            if (n != null)
                queue.enQueue(n);
        return node;
    }

    public void remove() {
        //throw new MyNotSupportException("not support exception");

    }
}

