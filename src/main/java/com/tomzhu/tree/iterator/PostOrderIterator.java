package com.tomzhu.tree.iterator;

import com.tomzhu.list.MyStack;
import com.tomzhu.tree.BinaryTreeNode;
import com.tomzhu.tree.TreeNode;

import java.util.Iterator;

/**
 * Created by tomzhu on 17-7-20.
 * a post order iterator for the binary tree.
 */
public class PostOrderIterator<E> implements Iterator<TreeNode<E>>{

    /**
     * a simple post order traversal recuisively is that:
     *      def visit(Node node):
     *          visit(node.leftChild)
     *          visit(node.rightChild)
     *          node.visit = true
     *
     *  a simple post order  traversal using stack is :
     *      def visit(Node node)
     *          lastVisitNode = node;
     *          Stack mystack = new Stack(node)
     *          while (!stack.isEmpty()) {
     *              node = stack.pop();
     *              while (node != null) {
     *                  mystack.push(node)
     *                  node = node.leftChild
     *              }
     *              if (stack.isEmpty())
     *                  break;
     *              node = mystack.getTail()
     *              if (!node.hasRight() || lastVisitNode == node.rightChild()) {
     *                  node = mystack.pop()
     *                  node.visit = true;
     *                  lastVisitNode = node;
     *                  mystack.push(null);
     *              } else
     *                  mystack.push(node.rightChild())
     *          }
     */

    private BinaryTreeNode<E> root;
    private MyStack<BinaryTreeNode<E>> stack;
    private BinaryTreeNode<E> lastVisitNode;
    private BinaryTreeNode<E> currentNode;

    public PostOrderIterator(BinaryTreeNode<E> node) {
        this.root = node;
        this.stack = new MyStack<BinaryTreeNode<E>>();
        this.stack.push(this.root);
        this.lastVisitNode = this.root;
    }

    public boolean hasNext() {
        boolean notcall = true;
        while (notcall) {
            if (stack.isEmpty())
                return false;
            else {
                BinaryTreeNode<E> node = this.stack.pop();
                while (node != null) {
                    stack.push(node);
                    node = node.getLeftChild();
                }
                if (stack.isEmpty())
                    return false;
                node = stack.getHead();
                if (!node.hasRightChild() || this.lastVisitNode == node.getRightChild()) {
                    this.lastVisitNode = this.currentNode = node = stack.pop();
                    stack.push(null);
                    notcall = false;
                } else {
                    stack.push(node.getRightChild());
                }
            }
            // return true;
        }
        return true;
    }

    public TreeNode<E> next() {
        return this.currentNode;
    }

    public void remove() {

    }

}
