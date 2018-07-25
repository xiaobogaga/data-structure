package com.tomzhu.tree;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static org.junit.Assert.*;

/**
 * Created by tomzhu on 18-3-18.
 */
public class AVLTreeTest {

    private static AVLTree<Integer> avlTree;

    static {
        avlTree = new AVLTree<Integer>();
        avlTree.insert(2);
        avlTree.insert(5);
        avlTree.insert(3);
    }


    @Test
    public void contains() throws Exception {
        printAVLTree(avlTree.getRootNode());
    }

    @Test
    public void findMax() throws Exception {
        assertEquals(avlTree.findMax() , new Integer(4));
    }

    @Test
    public void findMin() throws Exception {
        assertEquals(avlTree.findMin() , new Integer(1));
    }

    @Test
    public void insert() throws Exception {
        int time = 20;
        int i = 0;
        Random rand = new Random(System.currentTimeMillis());
        Map<Integer , Boolean> dm = new HashMap<Integer , Boolean>();
        dm.put(2 , true);
        dm.put(5 , true);
        dm.put(3 , true);
        while (i < time) {
            int key = rand.nextInt(i + 4 + time);
            if (dm.containsKey(key)) {
                continue;
            } else {
                System.out.println("key : " + key);
                dm.put(key , true);
                avlTree.insert(key);
                System.out.println("-----------------------");
                printAVLTree(avlTree.getRootNode());
                System.out.println("-----------------------");
            }
            i ++;
        }

        // let's check remove.

        for (Integer g : dm.keySet()) {
            System.out.println("remove : key : " + g);
            avlTree.remove(g);
            printAVLTree(avlTree.getRootNode());
        }

    }

    private void printAVLTree(AVLTreeNode node) {
        if (node == null) {
            return;
        }
        System.out.println(node);
        printAVLTree(node.getlChildl());
        printAVLTree(node.getrChild());
    }


    @Test
    public void remove() throws Exception {
        avlTree.insert(1);
        avlTree.insert(6);
        avlTree.insert(4);
        avlTree.insert(7);
        printAVLTree(avlTree.getRootNode());
        System.out.println("test remove");
        avlTree.remove(3);
        printAVLTree(avlTree.getRootNode());
    }

}