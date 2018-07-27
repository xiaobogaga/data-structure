package com.tomzhu.string;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @tomzhu.
 */
public class BasicStringTrieTest {

    private BasicStringTrie basicStringTrie;

    @Test
    public void buildTrie() throws Exception {
        basicStringTrie = BasicStringTrie.buildTrie(
                new String[]{"123", "121", "124"});

        basicStringTrie.insert("128");

        assertTrue(basicStringTrie.contains("123"));
        assertTrue(basicStringTrie.contains("124"));

        assertFalse(basicStringTrie.contains("198"));

        basicStringTrie.remove("123");
        basicStringTrie.remove("128");
        assertFalse(basicStringTrie.contains("123"));
        assertFalse(basicStringTrie.contains("128"));
        assertTrue(basicStringTrie.contains("121"));
    }

    @Test
    public void contains() throws Exception {
    }

    @Test
    public void insert() throws Exception {
    }

    @Test
    public void remove() throws Exception {
    }

}