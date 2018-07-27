package com.tomzhu.string;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @tomzhu.
 */
public class BasicNumberTrieTest {

    private BasicNumberTrie basicNumberTrie;

    @Test
    public void buildBasicNumberTries() throws Exception {
    }

    @Test
    public void contains() throws Exception {

        basicNumberTrie = BasicNumberTrie.buildBasicNumberTries(
                new String[]{"123", "121", "124"});

        basicNumberTrie.insert("128");

        assertTrue(basicNumberTrie.contains("123"));
        assertTrue(basicNumberTrie.contains("124"));

        assertFalse(basicNumberTrie.contains("198"));

        basicNumberTrie.remove("123");
        basicNumberTrie.remove("128");
        assertFalse(basicNumberTrie.contains("123"));
        assertFalse(basicNumberTrie.contains("128"));
        assertTrue(basicNumberTrie.contains("121"));

    }

    @Test
    public void remove() throws Exception {
    }

    @Test
    public void insert() throws Exception {
    }

}