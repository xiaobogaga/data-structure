package io.github.xiaobogaga.hash;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static org.junit.Assert.*;

/**
 * testing {@link HopscotchHashTable}
 *
 * @author tomzhu
 * @since 1.7
 */
public class HopscotchHashTableTest {

    private HopscotchHashTable<Integer, Integer> hopscotchHashTable;
    private int size = 1000;
    private java.util.HashMap<Integer, Integer> hashMap;


    @Test
    public void insert() throws Exception {
        hashMap = new HashMap<>();
        hopscotchHashTable = new HopscotchHashTable<>();
        Random rand = new Random(System.currentTimeMillis());
        for (int i = 0; i < size; ) {
            //  testing add.
            if (i != 0 && i % 5 == 0) {
                // testing replacing.
                Object[] arr = hashMap.keySet().toArray();
                int loc = rand.nextInt();
                if (loc < 0) loc = -loc;
                int k = (int) arr[loc % arr.length];
                assertTrue(hopscotchHashTable.contains(k));
                assertEquals(hopscotchHashTable.get(k), hashMap.get(k));
                int newV = rand.nextInt();
                System.out.println("replacing : [" + k + ", " + newV + "]");
                hashMap.put(k, newV);
                hopscotchHashTable.insert(k, newV);
                assertTrue(hopscotchHashTable.contains(k));
                assertEquals((int) hopscotchHashTable.get(k), newV);
                i++;
            } else {
                int k = rand.nextInt();
                if (hashMap.containsKey(k)) {
                    assertTrue(hopscotchHashTable.contains(k));
                    assertEquals(hopscotchHashTable.get(k), hashMap.get(k));
                    continue;
                }
                // assertFalse(hopscotchHashTable.contains(k));
                int v = rand.nextInt();
                System.out.println("insert : [" + k + ", " + v + "]");
                hashMap.put(k, v);
                hopscotchHashTable.insert(k, v);
                i++;
            }
        }
        for (Map.Entry<Integer, Integer> entry : hashMap.entrySet()) {
            assertTrue(hopscotchHashTable.contains(entry.getKey()));
            assertEquals(hopscotchHashTable.get(entry.getKey()), entry.getValue());
            assertTrue(hopscotchHashTable.remove(entry.getKey()));
        }
        //  testing remove.
    }

    @Test
    public void contains() throws Exception {
        hashMap = new HashMap<>();
        hopscotchHashTable = new HopscotchHashTable<>();
        Random rand = new Random(System.currentTimeMillis());
        for (int i = 0; i < size; ) {
            if (i != 0 && i % 5 == 0) {
                // testing replacing.
                Object[] arr = hashMap.keySet().toArray();
                int loc = rand.nextInt();
                if (loc < 0) loc = -loc;
                int k = (int) arr[loc % arr.length];
                assertTrue(hopscotchHashTable.contains(k));
                assertEquals(hopscotchHashTable.get(k), hashMap.get(k));
                int newV = rand.nextInt();
                hashMap.put(k, newV);
                hopscotchHashTable.insert(k, newV);
                assertTrue(hopscotchHashTable.contains(k));
                assertEquals((int) hopscotchHashTable.get(k), newV);
                i++;
            } else if (i != 0 && i % 4 == 0) {
                // testing remove
                Object[] arr = hashMap.keySet().toArray();
                int loc = rand.nextInt();
                if (loc < 0) loc = -loc;
                int k = (int) arr[loc % arr.length];
                assertTrue(hopscotchHashTable.contains(k));
                assertEquals(hopscotchHashTable.get(k), hashMap.get(k));
                hashMap.remove(k);
                assertTrue(hopscotchHashTable.remove(k));
                assertFalse(hopscotchHashTable.contains(k));
                i++;
            } else {
                //  testing add.
                int k = rand.nextInt();
                if (hashMap.containsKey(k)) {
                    assertTrue(hopscotchHashTable.contains(k));
                    assertEquals(hopscotchHashTable.get(k), hashMap.get(k));
                    continue;
                }
                assertFalse(hopscotchHashTable.contains(k));
                int v = rand.nextInt();
                hashMap.put(k, v);
                hopscotchHashTable.insert(k, v);
                i++;
            }
        }
        for (Map.Entry<Integer, Integer> entry : hashMap.entrySet()) {
            assertTrue(hopscotchHashTable.contains(entry.getKey()));
            assertEquals(hopscotchHashTable.get(entry.getKey()), entry.getValue());
        }
    }

    @Test
    public void remove() throws Exception {
        /**
         * insert : [983512940, -2059123915]
         * insert : [-822812767, 415427716]
         * insert : [-1357082653, 1377106858]
         * insert : [-135236698, 1323539481]
         * insert : [1924421974, 923062386]
         * replacing : [983512940, 1547838672]
         * insert : [850803254, 1759168832]
         * insert : [-132260115, -1613420367]
         * insert : [-1510136555, 1982275037]
         * insert : [293967548, 552654586]
         * replacing : [-135236698, 1897208376]
         * insert : [-1276471860, -2134491053]
         * insert : [-599373474, 426684352]
         * insert : [-641779828, -385402299]
         * insert : [1781269213, 3622902]
         */
        hopscotchHashTable = new HopscotchHashTable<>();
        int[] keys = new int[] {
                232935113, -373027492, -1877122320, -2105606301, 835938498, -373027492, 420047041,
                1606277121, 370774329, 157956532, 157956532, -1396762643, -1349912858, -997178895,
                3993043
        };

        int[] values = new int[] {
                727922798, -791885533, -498288858, -1362874660, -1712569288, 1614877487, -1718447260,
                -1343477706, -909427048, 363621462, 1635913913, 157187235, 837531810, 767830403,
                -1407702932
        };

        for (int i = 0; i < keys.length; i++) {
            hopscotchHashTable.insert(keys[i], values[i]);
        }

        for (int i = 0; i < keys.length; i++) {
            assertTrue(hopscotchHashTable.contains(keys[i]));
        }
    }

}