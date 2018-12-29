package com.tomzhu.tree.iterator;

import com.tomzhu.list.Stack;
import com.tomzhu.tree.TreeNode;

import java.util.Iterator;

/**
 * Created by tomzhu on 17-7-20.
 * a pre order iterator for the tree.
 * For a tree whose node may contains multi children ( > 2).
 * the pre order is :
 *      root node is first
 *      preorder traveling the subtrees from left to right.
 */
public class PreOrderIterator<E> implements Iterator<TreeNode<E>> {

    Stack<TreeNode<E>> stack = new Stack<TreeNode<E>>();

    /**
     * commonly , the node is the root node.
     * @param node
     */
    public PreOrderIterator(TreeNode<E> node) {
        stack.push(node);
    }

    /**
     * whether the iterator has next node to travel.
     * @return
     */
    public boolean hasNext() {
        return !stack.isEmpty();
    }

    /**
     * return the next node.
     * @return
     */
    public TreeNode<E> next() {
        TreeNode<E> node = stack.pop();
        for (TreeNode<E> n : node.getChilds(false))
            if (n != null)
                stack.push(n);
        return node;
    }

    /*
        think the pre order of tree.
        usually as follow :
            visit(node)
                visit(node)
                visit(node.leftChild)
                visit(node.rightChild)
        think a simple tree as follow :
                10
                / \
               4   8
              / \  /
             3   5 6
                   /
                  5
         the pre order of the tree using pre order stack:
           while (!stack.isEmpty()) {
                node = stack.pop();
                visit(node);
                if (node.hasRightChild()) stack.push(node.rightChild());
                if (node.hasLeftChild()) stack.push(node.leftChild());
           }

         How to prove the code is correct, simply use induction method:
            1. the root node is the first visiting node
            2. behind the current visiting node is the expected node.
         when the two steps is correct, we could say the code is correct.

     */

    public void remove() {
        // pass
    }

}
