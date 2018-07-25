package com.tomzhu.tree.iterator;

import com.tomzhu.list.MyStack;
import com.tomzhu.tree.BinaryTreeNode;
import com.tomzhu.tree.TreeNode;

import java.util.Iterator;

/**
 * Created by tomzhu on 17-7-20.
 * an in order iterator for the tree.
 */
public class BinaryTreeInOrderIterator<E> implements Iterator<BinaryTreeNode<E>> {

    /*
        think about the inorder travel for tree structure, the
        recuisive way is like this:
            def visit(node)
                visit(node.leftChild)
                node.visit = true
                visit(node.rightChild)
        when we using stack, it could be this:
            def visit(node)
                Stack mystack = new Stack(node);
                while(!mystack.isEmpty())
                    node = mystack.pop();
                    while (node != null)
                        mystack.push(node);
                        node = node.leftChild;
                    if (mystack.isEmpty())
                        return;
                    node = mystack.pop()
                    node.visit = true;
                    mystack.push(node.rightChild());
         for a tree whose node may contain more children than 2, the
         behavior of this in order travel is that:
         def visit(node)
                Stack mystack = new Stack(node);
                while(!mystack.isEmpty())
                    node = mystack.pop();
                    while (node != null)
                        mystack.push(node);
                        node = node.firstChild;
                    if (mystack.isEmpty())
                        return;
                    node = mystack.pop()
                    node.visit = true;
                    mystack.push(node.nextChild());
     */

    private MyStack<BinaryTreeNode<E>> myStack;

    public BinaryTreeInOrderIterator(BinaryTreeNode<E> root) {
        this.myStack = new MyStack<BinaryTreeNode<E>>();
        this.myStack.push(root);
    }

    public boolean hasNext() {
        BinaryTreeNode<E> node;
        node = myStack.pop();
        while (node != null) {
            myStack.push(node);
            node = node.getLeftChild();
        }
        if (myStack.isEmpty())
            return false;
        return true;
    }

    public BinaryTreeNode<E> next() {
        BinaryTreeNode<E> node = myStack.pop();
        myStack.push(node.getRightChild());
        return node;
    }

    public void remove() {
        //
    }
}
