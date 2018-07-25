package com.tomzhu.tree.iterator;

import com.tomzhu.tree.BasicTreeNode;

import java.util.Iterator;

/**
 * Created by tomzhu on 17-7-21.
 * this class is not implemented, because it
 * is ambiguous, since the order is not defined absolutely.
 * For a generic tree, the order should be define by user.
 * so this class is not implemented.
 */
public class BasicTreeInOrderIterator<E> implements Iterator<BasicTreeNode<E>> {

    /*
        think about the in order travel for tree structure, the
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
                    mystack.push(node.secondChild());
     */



    public boolean hasNext() {
        return false;
    }

    public BasicTreeNode<E> next() {
        return null;
    }

    public void remove() {

    }

}
