package io.github.xiaobogaga.tree;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * testing {@link BasicTree}
 *
 * @author tomzhu
 * @since 1.7
 */

public class BasicTreeTest {

    private BasicTree<Integer> basicTree = new BasicTree<Integer>(10);

    @org.junit.Test
    public void getRoot() throws Exception {
        Assert.assertEquals(10 , (int) basicTree.getRoot().getValue());
    }

    @org.junit.Test
    public void isEmpty() throws Exception {
        Assert.assertFalse(basicTree.isEmpty());
        BasicTree<Integer> anotherTree = new BasicTree<Integer>();
        Assert.assertTrue(anotherTree.isEmpty());
    }

    @org.junit.Test
    public void hasChildren() throws Exception {
        Assert.assertFalse(basicTree.hasChildren(basicTree.getRoot()));
        basicTree.addChild(basicTree.getRoot() , 10);
        Assert.assertTrue(basicTree.getRoot().hasChildren());
    }

    @org.junit.Test
    public void hasLeftSibling() throws Exception {
        Assert.assertFalse(basicTree.getRoot().hasLeftSibling());
        basicTree.getRoot().addChild(4);
        basicTree.getRoot().addChild(8);
        // Assert.assertTrue(basicTree.getRoot().getLastChild().hasLeftSibling());
        Assert.assertTrue(basicTree.hasLeftSibling(basicTree.getRoot().getLastChild()));
    }

    @org.junit.Test
    public void hasRightSibling() throws Exception {
        Assert.assertFalse(basicTree.getRoot().hasRightSibling());
        basicTree.getRoot().addChild(4);
        basicTree.getRoot().addChild(8);
        Assert.assertTrue(basicTree.hasRightSibling(basicTree.getRoot().getFirstChild()));
    }

    @org.junit.Test
    public void isRoot() throws Exception {
        Assert.assertTrue(basicTree.getRoot().isRoot());
        basicTree.getRoot().addChild(4);
        basicTree.getRoot().addChild(8);
        Assert.assertFalse(basicTree.isRoot(basicTree.getFirstChild(basicTree.getRoot())));
    }

    @Test
    public void addChild() throws Exception {
        basicTree.addChild(basicTree.getRoot() , 8);
        Assert.assertEquals(8 , (int) basicTree.getRoot().getFirstChild().getValue());
    }

    @Test
    public void addChild1() throws Exception {
        basicTree.addChild(basicTree.getRoot() , 8 , 0);
        basicTree.addChild(basicTree.getRoot() , 4 , 0);
        basicTree.addChild(basicTree.getRoot() , 6 , 1);
        assertEquals(3 , basicTree.getRoot().getChildrenSize());
        Assert.assertEquals(4 , (int) basicTree.getRoot().getFirstChild().getValue());
        Assert.assertEquals(6 , (int) basicTree.getRoot().getChild(1).getValue());
        Assert.assertEquals(8 , (int) basicTree.getRoot().getLastChild().getValue());
    }

    @org.junit.Test
    public void addChild2() throws Exception {
        BasicTreeNode<Integer> l1 = new BasicTreeNode<Integer>(8);
        BasicTreeNode<Integer> l2 = new BasicTreeNode<Integer>(10);
        basicTree.addChild(basicTree.getRoot() , l1);
        basicTree.addChild(basicTree.getRoot() , l2);
        Assert.assertEquals(2 , basicTree.getChildrenSize(basicTree.getRoot()));
        Assert.assertEquals(8 , (int) basicTree.getFirstChild(basicTree.getRoot()).getValue());
        Assert.assertEquals(10 , (int) basicTree.getLastChild(basicTree.getRoot()).getValue());
    }

    @org.junit.Test
    public void addChild3() throws Exception {
        BasicTreeNode<Integer> l1 = new BasicTreeNode<Integer>(4);
        BasicTreeNode<Integer> l2 = new BasicTreeNode<Integer>(6);
        BasicTreeNode<Integer> l3 = new BasicTreeNode<Integer>(8);
        basicTree.addChild(basicTree.getRoot() , l3 , 0);
        basicTree.addChild(basicTree.getRoot() , l1 , 0);
        basicTree.addChild(basicTree.getRoot() , l2 , 1);
        Assert.assertEquals(3 , basicTree.getChildrenSize(basicTree.getRoot()));
        Assert.assertEquals(4 , (int) basicTree.getFirstChild(basicTree.getRoot()).getValue());
        Assert.assertEquals(6 , (int) basicTree.getChild(basicTree.getRoot() , 1).getValue());
        Assert.assertEquals(8 , (int) basicTree.getLastChild(basicTree.getRoot()).getValue());
    }

    @org.junit.Test
    public void addChildren() throws Exception {
        Integer[] nodes = {4 , 6 , 8};
        basicTree.addChildren(basicTree.getRoot() , nodes);
        Assert.assertEquals(3 , basicTree.getChildrenSize(basicTree.getRoot()));
        Assert.assertEquals(4 , (int) basicTree.getFirstChild(basicTree.getRoot()).getValue());
        Assert.assertEquals(6 , (int) basicTree.getChild(basicTree.getRoot() , 1).getValue());
        Assert.assertEquals(8 , (int) basicTree.getLastChild(basicTree.getRoot()).getValue());
    }

    @org.junit.Test
    public void addChildren1() throws Exception {
        BasicTreeNode<Integer> l1 = new BasicTreeNode<Integer>(4);
        BasicTreeNode<Integer> l2 = new BasicTreeNode<Integer>(6);
        BasicTreeNode<Integer> l3 = new BasicTreeNode<Integer>(8);
        BasicTreeNode<Integer>[] nodes = new BasicTreeNode[] {
                l1 , l2 , l3
        };
        basicTree.addChildren(basicTree.getRoot() , nodes);
        Assert.assertEquals(4 , (int) basicTree.getFirstChild(basicTree.getRoot()).getValue());
        Assert.assertEquals(6 , (int) basicTree.getChild(basicTree.getRoot() , 1).getValue());
        Assert.assertEquals(8 , (int) basicTree.getLastChild(basicTree.getRoot()).getValue());
    }

    @org.junit.Test
    public void isFirstChild() throws Exception {
        basicTree.addChild(basicTree.getRoot() , 4);
        basicTree.addChild(basicTree.getRoot() , 6);
        Assert.assertTrue(basicTree.getChild(basicTree.getRoot() , 0).isFirstChild());
        Assert.assertFalse(basicTree.getChild(basicTree.getRoot() , 1).isFirstChild());
    }

    @org.junit.Test
    public void isLastChild() throws Exception {
        basicTree.addChild(basicTree.getRoot() , 4);
        basicTree.addChild(basicTree.getRoot() , 6);
        Assert.assertFalse(basicTree.getChild(basicTree.getRoot() , 0).isLastChild());
        Assert.assertTrue(basicTree.getChild(basicTree.getRoot() , 1).isLastChild());
    }

    @org.junit.Test
    public void addLeftSibling() throws Exception {
        basicTree.addChild(basicTree.getRoot() , 8).addLeftSibling(6).addLeftSibling(4);
        Assert.assertEquals(3 , basicTree.getChildrenSize(basicTree.getRoot()));
        Assert.assertEquals(4 , (int) basicTree.getFirstChild(basicTree.getRoot()).getValue());
        Assert.assertEquals(6 , (int) basicTree.getChild(basicTree.getRoot() , 1).getValue());
        Assert.assertEquals(8 , (int) basicTree.getLastChild(basicTree.getRoot()).getValue());
    }

    @org.junit.Test
    public void addLeftSibling1() throws Exception {
        BasicTreeNode<Integer> l1 = new BasicTreeNode<Integer>(4);
        BasicTreeNode<Integer> l2 = new BasicTreeNode<Integer>(6);
        BasicTreeNode<Integer> l3 = new BasicTreeNode<Integer>(8);
        basicTree.addChild(basicTree.getRoot() , l3).addLeftSibling(l2).addLeftSibling(l1);
        Assert.assertEquals(3 , basicTree.getChildrenSize(basicTree.getRoot()));
        Assert.assertEquals(4 , (int) basicTree.getFirstChild(basicTree.getRoot()).getValue());
        Assert.assertEquals(6 , (int) basicTree.getChild(basicTree.getRoot() , 1).getValue());
        Assert.assertEquals(8 , (int) basicTree.getLastChild(basicTree.getRoot()).getValue());
    }

    @org.junit.Test
    public void addRightSibling() throws Exception {
        basicTree.addChild(basicTree.getRoot() , 4).addRightSibling(6).addRightSibling(8);
        Assert.assertEquals(3 , basicTree.getChildrenSize(basicTree.getRoot()));
        Assert.assertEquals(4 , (int) basicTree.getFirstChild(basicTree.getRoot()).getValue());
        Assert.assertEquals(6 , (int) basicTree.getChild(basicTree.getRoot() , 1).getValue());
        Assert.assertEquals(8 , (int) basicTree.getLastChild(basicTree.getRoot()).getValue());
    }

    @org.junit.Test
    public void addRightSibling1() throws Exception {
        BasicTreeNode<Integer> l1 = new BasicTreeNode<Integer>(4);
        BasicTreeNode<Integer> l2 = new BasicTreeNode<Integer>(6);
        BasicTreeNode<Integer> l3 = new BasicTreeNode<Integer>(8);
        basicTree.addChild(basicTree.getRoot() , l1).addRightSibling(l2).addRightSibling(l3);
        Assert.assertEquals(3 , basicTree.getChildrenSize(basicTree.getRoot()));
        Assert.assertEquals(4 , (int) basicTree.getFirstChild(basicTree.getRoot()).getValue());
        Assert.assertEquals(6 , (int) basicTree.getChild(basicTree.getRoot() , 1).getValue());
        Assert.assertEquals(8 , (int) basicTree.getLastChild(basicTree.getRoot()).getValue());
    }

    @org.junit.Test
    public void getChildrenSize() throws Exception {
        Assert.assertEquals(0 , basicTree.getChildrenSize(basicTree.getRoot()));
        basicTree.addChild(basicTree.getRoot(), 1);
        Assert.assertEquals(1 , basicTree.getChildrenSize(basicTree.getRoot()));
    }

    @org.junit.Test
    public void getLeftSibling() throws Exception {
        basicTree.addChild(basicTree.getRoot() , 8).addLeftSibling(6).addLeftSibling(4);
        Assert.assertEquals(6 , (int) basicTree.getLastChild(basicTree.getRoot()).getLeftSibling()
                .getValue());
    }

    @org.junit.Test
    public void getRightSibling() throws Exception {
        basicTree.addChild(basicTree.getRoot() , 4).addRightSibling(6).addRightSibling(8);
        Assert.assertEquals(6 , (int) basicTree.getFirstChild(basicTree.getRoot()).getRightSibling()
                .getValue());
    }

    @org.junit.Test
    public void getParent() throws Exception {
        basicTree.addChild(basicTree.getRoot() , 4).addRightSibling(6).addRightSibling(8);
        Assert.assertEquals(10 , (int) basicTree.getChild(basicTree.getRoot() , 0).getParent().getValue());
    }

    @org.junit.Test
    public void getFirstChild() throws Exception {
        basicTree.addChild(basicTree.getRoot() , 4);
        basicTree.addChild(basicTree.getRoot() , 6);
        Assert.assertEquals(4 , (int) basicTree.getFirstChild(basicTree.getRoot()).getValue());
    }

    @org.junit.Test
    public void getLastChild() throws Exception {
        basicTree.addChild(basicTree.getRoot() , 4);
        basicTree.addChild(basicTree.getRoot() , 6);
        Assert.assertEquals(6 , (int) basicTree.getLastChild(basicTree.getRoot()).getValue());
    }

    @org.junit.Test
    public void getChilds() throws Exception {
        basicTree.addChild(basicTree.getRoot() , 4);
        basicTree.addChild(basicTree.getRoot() , 6);
        BasicTreeNode<Integer>[] childs = basicTree.getChilds(basicTree.getRoot() , true);
        Assert.assertEquals(4 , (int) childs[0].getValue());
        Assert.assertEquals(6 , (int) childs[1].getValue());
    }

    @org.junit.Test
    public void getChild() throws Exception {
        basicTree.addChild(basicTree.getRoot() , 4);
        basicTree.addChild(basicTree.getRoot() , 6);
        Assert.assertEquals(4 , (int) basicTree.getChild(basicTree.getRoot() , 0).getValue());
    }

    @org.junit.Test
    public void containChild() throws Exception {
        BasicTreeNode<Integer> c = basicTree.addChild(basicTree.getRoot() , 4);
        Assert.assertTrue(basicTree.containChild(basicTree.getRoot() , c));
    }

    @org.junit.Test
    public void searchChild() throws Exception {
        BasicTreeNode<Integer> c = basicTree.addChild(basicTree.getRoot() , 4);
        Assert.assertEquals(0 , basicTree.searchChild(basicTree.getRoot() , c));
    }

    @org.junit.Test
    public void removeLastChild() throws Exception {
        basicTree.addChild(basicTree.getRoot() , 4);
        basicTree.addChild(basicTree.getRoot() , 6);
        basicTree.addChild(basicTree.getRoot() , 8);
        Assert.assertEquals(8 , (int) basicTree.removeLastChild(basicTree.getRoot()).getValue());
        Assert.assertEquals(2 , basicTree.getChildrenSize(basicTree.getRoot()));
        Assert.assertEquals(6 , (int) basicTree.getLastChild(basicTree.getRoot()).getValue());
        Assert.assertNull(basicTree.getLastChild(basicTree.getRoot()).getRightSibling());
    }

    @org.junit.Test
    public void removeFirstChild() throws Exception {
        basicTree.addChild(basicTree.getRoot() , 4);
        basicTree.addChild(basicTree.getRoot() , 6);
        basicTree.addChild(basicTree.getRoot() , 8);
        Assert.assertEquals(4 , (int) basicTree.removeFirstChild(basicTree.getRoot()).getValue());
        Assert.assertEquals(2 , basicTree.getChildrenSize(basicTree.getRoot()));
        Assert.assertEquals(6 , (int) basicTree.getFirstChild(basicTree.getRoot()).getValue());
        Assert.assertNull(basicTree.getFirstChild(basicTree.getRoot()).getLeftSibling());
    }

    @org.junit.Test
    public void removeChild() throws Exception {
        basicTree.addChild(basicTree.getRoot() , 4);
        basicTree.addChild(basicTree.getRoot() , 6);
        basicTree.addChild(basicTree.getRoot() , 8);
        Assert.assertEquals(6 , (int) basicTree.removeChild(basicTree.getRoot() , 1).getValue());
        Assert.assertEquals(2 , basicTree.getChildrenSize(basicTree.getRoot()));
        Assert.assertEquals(4 , (int) basicTree.getFirstChild(basicTree.getRoot()).getValue());
        Assert.assertEquals(8 , (int) basicTree.getLastChild(basicTree.getRoot()).getValue());
        Assert.assertNull(basicTree.getFirstChild(basicTree.getRoot()).getLeftSibling());
        Assert.assertNull(basicTree.getLastChild(basicTree.getRoot()).getRightSibling());
        Assert.assertEquals(4 , (int) basicTree.getLastChild(basicTree.getRoot()).getLeftSibling().getValue());
    }

    @org.junit.Test
    public void removeChild1() throws Exception {
        BasicTreeNode<Integer> l1 = new BasicTreeNode<Integer>(4);
        BasicTreeNode<Integer> l2 = new BasicTreeNode<Integer>(6);
        BasicTreeNode<Integer> l3 = new BasicTreeNode<Integer>(8);
        basicTree.addChild(basicTree.getRoot() , l3).addLeftSibling(l2).addLeftSibling(l1);
        Assert.assertEquals(6 , (int) basicTree.removeChild(basicTree.getRoot() , l2).getValue());
        Assert.assertEquals(2 , basicTree.getChildrenSize(basicTree.getRoot()));
        Assert.assertEquals(4 , (int) basicTree.getFirstChild(basicTree.getRoot()).getValue());
        Assert.assertEquals(8 , (int) basicTree.getLastChild(basicTree.getRoot()).getValue());
        Assert.assertNull(basicTree.getFirstChild(basicTree.getRoot()).getLeftSibling());
        Assert.assertNull(basicTree.getLastChild(basicTree.getRoot()).getRightSibling());
        Assert.assertEquals(4 , (int) basicTree.getLastChild(basicTree.getRoot()).getLeftSibling().getValue());
    }

    @org.junit.Test
    public void clearChildren() throws Exception {
        basicTree.addChild(basicTree.getRoot() , 4);
        basicTree.addChild(basicTree.getRoot() , 6);
        basicTree.addChild(basicTree.getRoot() , 8);
        basicTree.clearChildren(basicTree.getRoot());
        Assert.assertFalse(basicTree.hasChildren(basicTree.getRoot()));
    }

}