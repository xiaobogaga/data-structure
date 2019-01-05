package com.tomzhu.external;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.LongBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.io.RandomAccessFile;

/**
 * an efficient and external memory hash table implementation by mapped file.
 *
 * Note that this external memory hash table is just a sample for showing how an out-of-memory data structure
 * is implemented. This hash table uses mapped file to save data efficiently, it can only save long data as key, and
 * long type as value. and value, key must be positive integers.
 *
 * the collision resolution strategy here is linear probing. and another important issue is that
 * this hash table must be built with a given size which defines how many elements this hash table
 * can save at most, so we don't consider rehash here.
 *
 * when removing an key value pair, what this hash table do is a lazy deletion, instead of removing the key value pair
 * directly, it just set the value to a negative integer which denotes that this key value pair is deleted.
 *
 * @author tomzhu
 * @since 1.7
 */

public class HashTable {

    private String PATH;

    private String fileName = "/metadata";

    private RandomAccessFile mmapedFile;

    private FileChannel channel;

    private MappedByteBuffer hashTable;

    private LongBuffer buffer;

    private int size = 1024 * 12; //这个究竟可以设置多大.

    private static long item_size = 8 * 2;

    /**
     * creating a default hash table.
     *
     * @param PATH
     * @throws IOException
     */
    public HashTable(String PATH) throws IOException {
        this.PATH = PATH;
        init();
    }

    /**
     * construct a hash table with the save path and maximum element size.
     *
     * @param PATH
     * @param size
     * @throws IOException
     */
    public HashTable(String PATH, int size) throws IOException {
        this.PATH = PATH;
        this.size = size;
        init();
    }

    private void init() throws IOException {

        File path = new File(this.PATH);
        if (!path.exists()) path.mkdirs();
        mmapedFile = new RandomAccessFile(new File(this.PATH + fileName), "rw");
        this.channel = mmapedFile.getChannel();
        this.hashTable = this.channel.map(FileChannel.MapMode.READ_WRITE,
                0l, size * item_size);
        this.buffer = this.hashTable.asLongBuffer();
    }

    private int hashCode(long key) {
        int ans = (int)(key ^ (key >>> 32));
        if (ans < 0) return -ans;
        return ans;
    }

    /**
     * try to add a key value pair, if such key already exists, replace the previous value
     *
     * @param key
     * @param value
     */
    public void addOrUpdate(long key, long value) {
        int loc = hashCode(key) % this.size;
        int deleteLoc = -1;
        while (isUse(loc)) {
            if (match(loc, key)) {
                update(loc, value);
                return ;
            } else if (deleteLoc == -1 && deleted(loc)) deleteLoc = loc;
            loc = (loc + 1) % this.size;
        }
        if (deleteLoc != -1) put(deleteLoc, key, value);
        else put(loc, key, value);
    }

    private boolean deleted(int loc) {
        return this.buffer.get(loc * 2 + 1) <= 0;
    }


    /**
     * try to remove and return the associate value for the key. return <tt>-1</tt>
     * if no such key found.
     *
     * @param key
     * @return the associated value
     */
    public long remove(long key) {
        int loc = hashCode(key) % this.size;
        while (isUse(loc)) {
            if (match(loc, key)) {
                if (deleted(loc)) return -1l;
                long info = this.buffer.get(loc * 2 + 1);
                update(loc, -1);
                return info;
            }
            loc = (loc + 1) % this.size;
        }
        return -1l;
    }

    private void update(int loc, long value) {
        loc = loc * 2;
        buffer.put(loc + 1, value);
    }

    private void put(int loc, long key, long value) {
        loc = loc * 2;
        this.buffer.put(loc, key);
        this.buffer.put(loc + 1, value);
    }

    private boolean isUse(long loc) {
        return this.buffer.get( (int) (loc * 2) ) > 0;
    }

    private boolean match(int loc, long key) {
        return this.buffer.get(loc * 2) == key;
    }

    /**
     * try to get the associated value for the key. return <tt>-1</tt> if no such key exists.
     *
     * @param key
     * @return
     */
    public long get(long key) {
        int loc = hashCode(key) % this.size;
        while (isUse(loc)) {
            if (match(loc, key)) {
                if (deleted(loc)) return -1l;
                long info = this.buffer.get(loc * 2 + 1);
                return info;
            }
            loc = (loc + 1) % this.size;
        }
        return -1l;
    }

    /**
     * @param key
     * @return whether this hash table contains the key.
     */
    public boolean contains(long key) {
        return this.get(key) > 0;
    }

    /**
     * closing this hash table.
     */
    public void close() {
        AccessController.doPrivileged(new PrivilegedAction() {
            public Object run() {
                try {
                    Method getCleanerMethod = hashTable.getClass().getMethod("cleaner",new Class[0]);
                    getCleanerMethod.setAccessible(true);
                    sun.misc.Cleaner cleaner =(sun.misc.Cleaner)getCleanerMethod.invoke(hashTable,new Object[0]);
                    cleaner.clean();
                } catch(Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

        });
        try {
            this.mmapedFile.close();
            this.channel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
