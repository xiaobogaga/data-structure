package io.github.xiaobogaga.tree;

import org.junit.Test;

import java.util.Map;
import java.util.Random;

import static org.junit.Assert.*;

/**
 * testing {@link BTree}
 *
 * @author tomzhu
 * @since 1.7
 */
public class BTreeTest {

    private BTree<Integer> tree = new BTree<Integer>();
    private int size = 100;

    @Test
    public void contains() throws Exception {

    }


    @Test
    public void findMax() throws Exception {
        tree = new BTree<>();
        java.util.TreeMap<Integer, Integer> maps = new java.util.TreeMap<Integer, Integer> ();
        java.util.HashMap<Integer, Integer> hashMap = new java.util.HashMap<>();
        Random rand = new Random(System.currentTimeMillis());
        assertTrue(tree.isEmpty());
        for (int i = 0; i < size;) {
            int k = rand.nextInt();
            if (maps.containsKey(k)) continue;
            assertFalse(tree.contains(k));
            maps.put(k, k);
            hashMap.put(k, k);
            tree.insert(k);
            assertTrue(tree.contains(k));
            assertEquals(tree.getMin(), maps.firstKey());
            assertEquals(tree.getMax(), maps.lastKey());
            i ++;
        }
    }

    @Test
    public void findMin() throws Exception {

    }

    @Test
    public void insert() throws Exception {
        tree = new BTree<>();
        java.util.TreeMap<Integer, Integer> maps = new java.util.TreeMap<Integer, Integer> ();
        java.util.HashMap<Integer, Integer> hashMap = new java.util.HashMap<>();
        Random rand = new Random(System.currentTimeMillis());
        assertTrue(tree.isEmpty());
        for (int i = 0; i < size;) {
            int k = rand.nextInt();
            if (maps.containsKey(k)) continue;
//            System.out.println();
//            System.out.println("inserting : " + k);
//            System.out.println();

            maps.put(k, k);
            hashMap.put(k, k);
            tree.insert(k);
            // tree.PrintTree();
            assertEquals(tree.getMin(), maps.firstKey());
            assertEquals(tree.getMax(), maps.lastKey());
            if (i != 0 && i % 5 == 0) {
                Object[] arr = hashMap.values().toArray();
                int l = rand.nextInt();
                if (l < 0) l = -l;
                int removedK = (Integer) arr[l % arr.length];

//                System.out.println();
//                System.out.println("removing : " + removedK);
//                System.out.println();

                hashMap.remove(removedK);
                maps.remove(removedK);
                assertTrue(tree.contains(removedK));
                tree.remove(removedK);
                // tree.PrintTree();
                assertFalse(tree.contains(removedK));
                assertEquals(tree.getMin(), maps.firstKey());
                assertEquals(tree.getMax(), maps.lastKey());
            }
            i ++;
        }
        assertFalse(tree.isEmpty());

        for (Map.Entry<Integer, Integer> entry : hashMap.entrySet()) {
            // System.out.println("searching : " + entry.getKey());
            assertTrue(tree.contains(entry.getKey()));
        }

        // testing remove.
        for (Map.Entry<Integer, Integer> entry : hashMap.entrySet()) {
            // System.out.println("removing : " + entry.getKey());
            assertTrue(tree.contains(entry.getKey()));
            assertTrue(tree.remove(entry.getKey()));
            assertFalse(tree.contains(entry.getKey()));
        }
        assertTrue(tree.isEmpty());
    }

    @Test
    public void remove() throws Exception {
        tree = new BTree<>();
        java.util.TreeMap<Integer, Integer> maps = new java.util.TreeMap<Integer, Integer> ();
        java.util.HashMap<Integer, Integer> hashMap = new java.util.HashMap<>();
        Random rand = new Random(System.currentTimeMillis());
        assertTrue(tree.isEmpty());
        for (int i = 0; i < size;) {
            int k = rand.nextInt();
            if (maps.containsKey(k)) continue;
            assertFalse(tree.contains(k));

            // System.out.println("inserting : " + k);

            maps.put(k, k);
            hashMap.put(k, k);
            tree.insert(k);
            assertTrue(tree.contains(k));
            assertEquals(tree.getMin(), maps.firstKey());
            assertEquals(tree.getMax(), maps.lastKey());
            i ++;
        }

        //tree.PrintTree();

        for (Map.Entry<Integer, Integer> entry : hashMap.entrySet()) {
            // System.out.println("removing : " + entry.getKey());
            // System.out.println();
            // System.out.println();
            // tree.PrintTree();
            //System.out.println();
            assertTrue(tree.remove(entry.getKey()));
            assertFalse(tree.contains(entry.getKey()));
        }
        assertTrue(tree.isEmpty());
    }

}