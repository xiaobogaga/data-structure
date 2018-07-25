package com.tomzhu.tree;

import org.junit.Test;

import java.util.HashMap;
import java.util.Random;

import static org.junit.Assert.*;

/**
 * Created by tomzhu on 18-3-25.
 */
public class BTreeTest {

    private BTree<Integer> btree = new BTree<Integer>();

    @Test
    public void contains() throws Exception {
        btree.insert(1);
        btree.insert(2);
        btree.insert(3);
        btree.insert(4);
        btree.insert(5);
        btree.insert(6);
        btree.insert(7);
        btree.insert(8);
        btree.insert(9);
        btree.insert(10);
        btree.insert(11);
        btree.insert(12);
        btree.insert(13);
        btree.insert(14);
        btree.insert(15);
        btree.insert(16);
        btree.insert(17);
        // btree.PrintTree();
        btree.insert(18);
        btree.PrintTree();

        int i = 18;
        while (i >= 1) {
            if (i == 9)
                System.out.println("catch it");
            btree.remove(i);
            System.out.println("remove key : " + i);
            btree.PrintTree();
            i --;
        }

    }


    @Test
    public void findMax() throws Exception {
    }

    @Test
    public void findMin() throws Exception {

    }

    @Test
    public void insert() throws Exception {
        int i = 0, size = 30;
        int from = 0, end = 100;
        HashMap<Integer, Boolean> maps = new HashMap<Integer, Boolean>();
        // randomly insert element to the btree, and see result.
        Random rand = new Random(System.currentTimeMillis());
        while (i < size) {
            int key = rand.nextInt(end);
            if (maps.containsKey(key)) {
                // continue
            } else {
                maps.put(key , true);
                // System.out.println("aftere insert key : " + key);
                btree.insert(key);
                i ++;
            }
        }
        btree.PrintTree();
        for (Integer key : maps.keySet()) {
            System.out.println("remove key : " + key);
            btree.remove(key);
            btree.PrintTree();
        }
    }

    @Test
    public void remove() throws Exception {

    }

}