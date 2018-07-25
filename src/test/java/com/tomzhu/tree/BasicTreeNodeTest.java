package com.tomzhu.tree;

import org.junit.Assert;
import org.junit.Test;

import javax.swing.plaf.basic.BasicScrollBarUI;


/**
 * Created by tomzhu on 2017/7/12.
 */
public class BasicTreeNodeTest {

    private BasicTreeNode<Integer> basicTreeNode = new BasicTreeNode<Integer>(10);

    @Test
    public void hasChildren() throws Exception {
        Assert.assertFalse(basicTreeNode.hasChildren());
        basicTreeNode.addChild(4);
        Assert.assertTrue(basicTreeNode.hasChildren());
    }

    @Test
    public void hasLeftSibling() throws Exception {
        Assert.assertFalse(basicTreeNode.hasLeftSibling());
        basicTreeNode.addChild(4);
        basicTreeNode.addChild(8);
        Assert.assertTrue(basicTreeNode.getLastChild().hasLeftSibling());
    }

    @Test
    public void hasRightSibling() throws Exception {
        Assert.assertFalse(basicTreeNode.hasRightSibling());
        basicTreeNode.addChild(4);
        basicTreeNode.addChild(8);
        Assert.assertTrue(basicTreeNode.getFirstChild().hasRightSibling());
    }

    @Test
    public void isRoot() throws Exception {
        Assert.assertTrue(basicTreeNode.isRoot());
        basicTreeNode.addChild(4);
        basicTreeNode.addChild(8);
        Assert.assertFalse(basicTreeNode.getLastChild().isRoot());
    }

    @Test
    public void addChild() throws Exception {
        basicTreeNode.addChild(8);
        Assert.assertEquals(8 , (int) basicTreeNode.getFirstChild().getValue());
    }

    @Test
    public void addChild1() throws Exception {
        basicTreeNode.addChild(0 , 8);
        basicTreeNode.addChild(0 , 4);
        basicTreeNode.addChild(1 , 6);
        Assert.assertEquals(3 , basicTreeNode.getChildrenSize());
        Assert.assertEquals(4 , (int) basicTreeNode.getFirstChild().getValue());
        Assert.assertEquals(6 , (int) basicTreeNode.getChild(1).getValue());
        Assert.assertEquals(8 , (int) basicTreeNode.getLastChild().getValue());
    }

    @Test
    public void addChild2() throws Exception {
        BasicTreeNode<Integer> l1 = new BasicTreeNode<Integer>(8 , basicTreeNode);
        BasicTreeNode<Integer> l2 = new BasicTreeNode<Integer>(10 , basicTreeNode , l1);
        basicTreeNode.addChild(l1);
        basicTreeNode.addChild(l2);
        Assert.assertEquals(2 , basicTreeNode.getChildrenSize());
        Assert.assertEquals(8 , (int) basicTreeNode.getFirstChild().getValue());
        Assert.assertEquals(10 , (int) basicTreeNode.getLastChild().getValue());
    }

    @Test
    public void addChild3() throws Exception {
        BasicTreeNode<Integer> l1 = new BasicTreeNode<Integer>(4 , basicTreeNode);
        BasicTreeNode<Integer> l2 = new BasicTreeNode<Integer>(6 , basicTreeNode , l1);
        BasicTreeNode<Integer> l3 = new BasicTreeNode<Integer>(8 , basicTreeNode , l2);
        basicTreeNode.addChild(0 , l3);
        basicTreeNode.addChild(0 , l1);
        basicTreeNode.addChild(1 , l2);
        Assert.assertEquals(3 , basicTreeNode.getChildrenSize());
        Assert.assertEquals(4 , (int) basicTreeNode.getFirstChild().getValue());
        Assert.assertEquals(6 , (int) basicTreeNode.getChild(1).getValue());
        Assert.assertEquals(8 , (int) basicTreeNode.getLastChild().getValue());
    }

    @Test
    public void addChildren() throws Exception {
        Integer[] nodes = {4 , 6 , 8};
        basicTreeNode.addChildren(nodes);
        Assert.assertEquals(3 , basicTreeNode.getChildrenSize());
        Assert.assertEquals(4 , (int) basicTreeNode.getFirstChild().getValue());
        Assert.assertEquals(6 , (int) basicTreeNode.getChild(1).getValue());
        Assert.assertEquals(8 , (int) basicTreeNode.getLastChild().getValue());
    }

    @Test
    public void addChildren1() throws Exception {
        BasicTreeNode<Integer> l1 = new BasicTreeNode<Integer>(4 , basicTreeNode);
        BasicTreeNode<Integer> l2 = new BasicTreeNode<Integer>(6 , basicTreeNode , l1);
        BasicTreeNode<Integer> l3 = new BasicTreeNode<Integer>(8 , basicTreeNode , l2);
        BasicTreeNode<Integer>[] nodes = new BasicTreeNode[] {
                l1 , l2 , l3
        };
        basicTreeNode.addChildren(nodes);
        Assert.assertEquals(4 , (int) basicTreeNode.getFirstChild().getValue());
        Assert.assertEquals(6 , (int) basicTreeNode.getChild(1).getValue());
        Assert.assertEquals(8 , (int) basicTreeNode.getLastChild().getValue());
    }

    @Test
    public void isFirstChild() throws Exception {
        basicTreeNode.addChild(4);
        basicTreeNode.addChild(6);
        Assert.assertTrue(basicTreeNode.getChild(0).isFirstChild());
        Assert.assertFalse(basicTreeNode.getChild(1).isFirstChild());
    }

    @Test
    public void isLastChild() throws Exception {
        basicTreeNode.addChild(4);
        basicTreeNode.addChild(6);
        Assert.assertFalse(basicTreeNode.getChild(0).isLastChild());
        Assert.assertTrue(basicTreeNode.getChild(1).isLastChild());
    }

    @Test
    public void addLeftSibling() throws Exception {
        basicTreeNode.addChild(8).addLeftSibling(6).addLeftSibling(4);
        Assert.assertEquals(3 , basicTreeNode.getChildrenSize());
        Assert.assertEquals(4 , (int) basicTreeNode.getFirstChild().getValue());
        Assert.assertEquals(6 , (int) basicTreeNode.getChild(1).getValue());
        Assert.assertEquals(8 , (int) basicTreeNode.getLastChild().getValue());
    }

    @Test
    public void addLeftSibling1() throws Exception {
        BasicTreeNode<Integer> l1 = new BasicTreeNode<Integer>(4 , basicTreeNode);
        BasicTreeNode<Integer> l2 = new BasicTreeNode<Integer>(6 , basicTreeNode);
        BasicTreeNode<Integer> l3 = new BasicTreeNode<Integer>(8 , basicTreeNode);
        basicTreeNode.addChild(l3).addLeftSibling(l2).addLeftSibling(l1);
        Assert.assertEquals(3 , basicTreeNode.getChildrenSize());
        Assert.assertEquals(4 , (int) basicTreeNode.getFirstChild().getValue());
        Assert.assertEquals(6 , (int) basicTreeNode.getChild(1).getValue());
        Assert.assertEquals(8 , (int) basicTreeNode.getLastChild().getValue());
    }

    @Test
    public void addRightSibling() throws Exception {
        basicTreeNode.addChild(4).addRightSibling(6).addRightSibling(8);
        Assert.assertEquals(3 , basicTreeNode.getChildrenSize());
        Assert.assertEquals(4 , (int) basicTreeNode.getFirstChild().getValue());
        Assert.assertEquals(6 , (int) basicTreeNode.getChild(1).getValue());
        Assert.assertEquals(8 , (int) basicTreeNode.getLastChild().getValue());
    }

    @Test
    public void addRightSibling1() throws Exception {
        BasicTreeNode<Integer> l1 = new BasicTreeNode<Integer>(4 , basicTreeNode);
        BasicTreeNode<Integer> l2 = new BasicTreeNode<Integer>(6 , basicTreeNode);
        BasicTreeNode<Integer> l3 = new BasicTreeNode<Integer>(8 , basicTreeNode);
        basicTreeNode.addChild(l1).addRightSibling(l2).addRightSibling(l3);
        Assert.assertEquals(3 , basicTreeNode.getChildrenSize());
        Assert.assertEquals(4 , (int) basicTreeNode.getFirstChild().getValue());
        Assert.assertEquals(6 , (int) basicTreeNode.getChild(1).getValue());
        Assert.assertEquals(8 , (int) basicTreeNode.getLastChild().getValue());
    }

    @Test
    public void getChildrenSize() throws Exception {
        Assert.assertEquals(0 , basicTreeNode.getChildrenSize());
        basicTreeNode.addChild(1);
        Assert.assertEquals(1 , basicTreeNode.getChildrenSize());
    }

    @Test
    public void getLeftSibling() throws Exception {
        basicTreeNode.addChild(8).addLeftSibling(6).addLeftSibling(4);
        Assert.assertEquals(6 , (int) basicTreeNode.getLastChild().getLeftSibling().getValue());
    }

    @Test
    public void getRightSibling() throws Exception {
        basicTreeNode.addChild(4).addRightSibling(6).addRightSibling(8);
        Assert.assertEquals(6 , (int) basicTreeNode.getFirstChild().getRightSibling().getValue());
    }

    @Test
    public void getParent() throws Exception {
        basicTreeNode.addChild(4).addRightSibling(6).addRightSibling(8);
        Assert.assertEquals(10 , (int) basicTreeNode.getChild(0).getParent().getValue());
    }

    @Test
    public void getFirstChild() throws Exception {
        basicTreeNode.addChild(4);
        basicTreeNode.addChild(6);
        Assert.assertEquals(4 , (int) basicTreeNode.getFirstChild().getValue());
    }

    @Test
    public void getLastChild() throws Exception {
        basicTreeNode.addChild(4);
        basicTreeNode.addChild(6);
        Assert.assertEquals(6 , (int) basicTreeNode.getLastChild().getValue());
    }

    @Test
    public void getChilds() throws Exception {
        basicTreeNode.addChild(4);
        basicTreeNode.addChild(6);
        BasicTreeNode<Integer>[] childs = basicTreeNode.getChilds(true);
        Assert.assertEquals(4 , (int) childs[0].getValue());
        Assert.assertEquals(6 , (int) childs[1].getValue());
    }

    @Test
    public void getChild() throws Exception {
        basicTreeNode.addChild(4);
        basicTreeNode.addChild(6);
        Assert.assertEquals(4 , (int) basicTreeNode.getChild(0).getValue());
    }

    @Test
    public void containChild() throws Exception {
        BasicTreeNode<Integer> c = basicTreeNode.addChild(4);
        Assert.assertTrue(basicTreeNode.containChild(c));
    }

    @Test
    public void searchChild() throws Exception {
        BasicTreeNode<Integer> c = basicTreeNode.addChild(4);
        Assert.assertEquals(0 , basicTreeNode.searchChild(c));
    }

    @Test
    public void removeLastChild() throws Exception {
        basicTreeNode.addChild(4);
        basicTreeNode.addChild(6);
        basicTreeNode.addChild(8);
        Assert.assertEquals(8 , (int) basicTreeNode.removeLastChild().getValue());
        Assert.assertEquals(2 , basicTreeNode.getChildrenSize());
        Assert.assertEquals(6 , (int) basicTreeNode.getLastChild().getValue());
        Assert.assertNull(basicTreeNode.getLastChild().getRightSibling());
    }

    @Test
    public void removeFirstChild() throws Exception {
        basicTreeNode.addChild(4);
        basicTreeNode.addChild(6);
        basicTreeNode.addChild(8);
        Assert.assertEquals(4 , (int) basicTreeNode.removeFirstChild().getValue());
        Assert.assertEquals(2 , basicTreeNode.getChildrenSize());
        Assert.assertEquals(6 , (int) basicTreeNode.getFirstChild().getValue());
        Assert.assertNull(basicTreeNode.getFirstChild().getLeftSibling());
    }

    @Test
    public void removeChild() throws Exception {
        basicTreeNode.addChild(4);
        basicTreeNode.addChild(6);
        basicTreeNode.addChild(8);
        Assert.assertEquals(6 , (int) basicTreeNode.removeChild(1).getValue());
        Assert.assertEquals(2 , basicTreeNode.getChildrenSize());
        Assert.assertEquals(4 , (int) basicTreeNode.getFirstChild().getValue());
        Assert.assertEquals(8 , (int) basicTreeNode.getLastChild().getValue());
        Assert.assertNull(basicTreeNode.getFirstChild().getLeftSibling());
        Assert.assertNull(basicTreeNode.getLastChild().getRightSibling());
        Assert.assertEquals(4 , (int) basicTreeNode.getLastChild().getLeftSibling().getValue());
    }

    @Test
    public void removeChild1() throws Exception {
        BasicTreeNode<Integer> l1 = new BasicTreeNode<Integer>(4 , basicTreeNode);
        BasicTreeNode<Integer> l2 = new BasicTreeNode<Integer>(6 , basicTreeNode);
        BasicTreeNode<Integer> l3 = new BasicTreeNode<Integer>(8 , basicTreeNode);
        basicTreeNode.addChild(l3).addLeftSibling(l2).addLeftSibling(l1);
        Assert.assertEquals(6 , (int) basicTreeNode.removeChild(l2).getValue());
        Assert.assertEquals(2 , basicTreeNode.getChildrenSize());
        Assert.assertEquals(4 , (int) basicTreeNode.getFirstChild().getValue());
        Assert.assertEquals(8 , (int) basicTreeNode.getLastChild().getValue());
        Assert.assertNull(basicTreeNode.getFirstChild().getLeftSibling());
        Assert.assertNull(basicTreeNode.getLastChild().getRightSibling());
        Assert.assertEquals(4 , (int) basicTreeNode.getLastChild().getLeftSibling().getValue());
    }

    @Test
    public void clearChildren() throws Exception {
        basicTreeNode.addChild(4);
        basicTreeNode.addChild(6);
        basicTreeNode.addChild(8);
        basicTreeNode.clearChildren();
        Assert.assertFalse(basicTreeNode.hasChildren());
    }

}