package com.tomzhu.external;

import java.io.File;
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
 * an offset+fileNo as value. Remember this is just a code template to show how to build a out-of-memory hash table.
 * If other key-value types are wanted, can simple change the code here.
 */

public class HashTable {

    private String PATH;

    private String fileName = "metadata";

    private RandomAccessFile mmapedFile;

    private FileChannel channel;

    private MappedByteBuffer hashTable;

    private LongBuffer buffer;

    public static int size = 1024 * 1024 * 32 * 3; //这个究竟可以设置多大.

    public static long item_size = 8 * 2;

    public HashTable(String PATH) {
        this.PATH = PATH;
    }

    public void init() throws IOException {
        File path = new File(this.PATH);
        if (!path.exists()) path.mkdirs();
        mmapedFile = new RandomAccessFile(new File(this.PATH + fileName), "rw");
        this.channel = mmapedFile.getChannel();
        this.hashTable = this.channel.map(FileChannel.MapMode.READ_WRITE,
                0l, size * item_size);
        this.buffer = this.hashTable.asLongBuffer();
    }

    public int hashCode(long key) {
        int ans = (int)(key ^ (key >>> 32));
        if (ans < 0) return -ans;
        return ans;
    }

    public void addOrUpdate(long key, int offset, int fileNo) {
        int loc = hashCode(key) % this.size;
        long currentTime = System.currentTimeMillis();
        while (isUse(loc)) {
            if (match(loc, key)) {
                update(loc, offset, fileNo);
                return ;
            }
            loc = (loc + 1) % this.size;
        }
        put(loc, key, offset, fileNo);
    }

    private void update(int loc, int offset, int fileNo) {
        loc = loc * 2;
        buffer.put(loc + 1, wrap(offset, fileNo));
    }

    private static long wrap(int offset, int fileNo) {
        long ans = 0;
        for (int i = 0; i < 32; i++) {
            ans |= ( ((long) ((offset >>> i) & 1)) << i);
            ans |= ( ((long) ((fileNo >>> i) & 1)) << (32 + i));
        }
        return ans;
    }

    private static int unwrapOffset(long wrapper) {
        int ans = 0;
        for (int i = 0; i < 32; i++) {
            ans |= (((wrapper >>> i) & 1) << i);
        }
        return ans;
    }

    private static int unwrapFileNo(long wrapper) {
        int ans = 0;
        for (int i = 0; i < 32; i++) {
            ans |= (((wrapper >>> (i + 32) ) & 1) << i);
        }
        return ans;
    }


    public void put(int loc, long key, int offset, int fileNo) {
        loc = loc * 2;
        this.buffer.put(loc, key);
        this.buffer.put(loc + 1, wrap(offset, fileNo));
    }


    private boolean isUse(long loc) {
        return unwrapFileNo(this.buffer.get( (int) (loc * 2 + 1) )) != 0;
    }

    private boolean match(int loc, long key) {
        return this.buffer.get(loc * 2) == key;
    }

    public long tryGet(long key) {
        int loc = hashCode(key) % this.size;
        long currentTime = System.currentTimeMillis();
        while (isUse(loc)) {
            if (match(loc, key)) {
                long info = this.buffer.get(loc * 2 + 1);
                return info;
            }
            loc = (loc + 1) % this.size;
        }
        return -1;
    }

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
