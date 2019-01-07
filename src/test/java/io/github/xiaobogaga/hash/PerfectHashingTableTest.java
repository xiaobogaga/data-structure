package io.github.xiaobogaga.hash;

import org.junit.Test;

import java.util.HashMap;
import java.util.Random;

import static org.junit.Assert.*;

/**
 * testing {@link PerfectHashingTable}
 *
 * @author tomzhu
 * @since 1.7
 */
public class PerfectHashingTableTest {

    private int size = 100;
    private PerfectHashingTable<Integer, Integer> perfectHashingTable;
    private HashMap<Integer, Integer> maps;
    private int range = 10000;
    Random rand = new Random();

    {
        this.maps = new HashMap<Integer, Integer>();
        int i = 0;
        perfectHashingTable = new PerfectHashingTable<Integer, Integer>(size);
        int randNumber;
        while (i < size) {
            do {
                randNumber = rand.nextInt();
            } while (maps.containsKey(randNumber));
            maps.put(randNumber, randNumber);
            perfectHashingTable.insert(randNumber, randNumber);
            i ++;
        }

        // start build.
        perfectHashingTable.build();
    }


    @Test
    public void contains() {

    }

    @Test
    public void remove() {
        for (int v : this.maps.keySet()) {
            perfectHashingTable.remove(v);
            assertFalse(perfectHashingTable.contains(v));
        }
    }
}