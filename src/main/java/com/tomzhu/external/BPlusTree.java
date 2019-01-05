package com.tomzhu.external;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.reflect.Method;
import java.nio.LongBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.LinkedList;

/**
 * an external memory and efficient bplus tree implementation. Note that this bplustree only can hold
 * long type key and value, for other type, must change the code here.
 * <p>
 * we design the external bplus tree as following:
 * it contains three files :
 * 1. a meta data file.
 * 1. a mid_node file for mid nodes.
 * 2. a leaf_node file for leaf nodes.
 *
 * the meta data file organize the data as:
 * total 4k.
 * real data (48 bytes)
 * |long| -> mid_length_threshold
 * |long| -> current mid node size.
 * |long| -> leaf_length_threshold
 * |long| -> current leaf node size.
 * |long| -> root's offset
 * |long| -> is Root leaf.
 *
 * 2. the mid_node file organized as follows:
 * long -> current size
 * long -> parent offset
 * [key (long type), offset (long type) ] pairs. remember that the first bit for offset of the pair
 * used as a sign when it is 1, then this offset point to a leaf node, otherwise 0 represent a mid node.
 *
 *
 * 3. the leaf_node file organize as follows:
 * long -> current size
 * long -> previous offset
 * long -> next offset
 * long -> parent offset
 * [key (long type) , info (long type) ] pairs.
 *
 *
 * Again, BPlusTree can only save positive
 * long type as value.
 *
 * when do deleting, a lazy deletion strategy is actually what happened. instead of removing the key
 * value pair, this bplustree implementation would set the value to a negative integer, and this is
 * why this bplustree cannot hold negative values.
 *
 * BPlusTree is built with the saving path and leaf node mapped file size, mid node mapped file size.
 * these two mapped file size are very important, it controls how many elements at most this bplustree
 * can holds. Leaf mapped file size is the maximum leaf file size, for example, given leaf mapped file
 * size 1024 * 16, then there could be at most 4 leaf block (by 1024 * 16 / (leaf block sie = 1024 * 4)).
 * each leaf block has 4 long data to represent current size, previous offset, and so on (see previous
 * leaf node file organization for more detail), and then remaining elements are used to save key-info pairs,
 * then at most 4 * ((1024 * 4 - 4 * 8) / 8) = 2032 elements. and if given mid node mapped file size
 * 1024 * 16. then at most 4 * ( (1024 * 4 - 2 * 8) / 8) = 2040 leaves can be maintained.
 *
 * @author tomzhu
 * @since 1.7
 */

public class BPlusTree {

    private static int LeafBlock = 1024 * 4;
    private static int MIDBlock = 1024 * 4;
    private String PATH;
    private final String leafFileName = "leaf";
    private final String midFileName = "mid";
    private final String metaFileName = "meta";
    private RandomAccessFile leafFile;
    private RandomAccessFile midFile;
    private RandomAccessFile metaFile;
    private MappedByteBuffer metaMappedByteBuffer;
    private MappedByteBuffer midMappedByteBuffer;
    private MappedByteBuffer leafMappedByteBuffer;
    private LongBuffer metaLongBuffer;
    private LongBuffer midLongBuffer;
    private LongBuffer leafLongBuffer;
    private int metaSize = 1024 * 4;
    private int leafMappedMaxSize = 1024 * 1024;
    private int midMappedMaxSize = 1024 * 4;

    private int LEAF_LENGTH_THRESHOLD = (LeafBlock - 4 * 8) / 16;
    private int MID_LENGTH_THRESHOLD = (MIDBlock - 2 * 8) / 16;
    private long current_mid_size = 0;
    private long current_leaf_size = 0;
    private long rootOffset = -1;
    private long isRootLeaf;

    private Node root;

    /**
     * construct a default bplustree with leaf mapped size : 1024 * 1024,
     * and mid node mapped file size 1024 * 4
     *
     * @param path
     * @throws IOException
     */
    public BPlusTree(String path) throws IOException {
        this.PATH = path;
        File p = new File(path);
        if (!p.exists()) p.mkdirs();
        this.leafFile = new RandomAccessFile(this.PATH + "/" + this.leafFileName, "rw");
        this.midFile = new RandomAccessFile(this.PATH + "/" + this.midFileName, "rw");
        this.metaFile = new RandomAccessFile(this.PATH + "/" + this.metaFileName, "rw");
        long length = this.metaFile.length();
        this.metaMappedByteBuffer = this.metaFile.getChannel()
                .map(FileChannel.MapMode.READ_WRITE, 0, metaSize);
        this.metaLongBuffer = this.metaMappedByteBuffer.asLongBuffer();
        this.midMappedByteBuffer = this.midFile.getChannel().map(FileChannel.MapMode.READ_WRITE,
                0, this.midMappedMaxSize);
        this.midLongBuffer = this.midMappedByteBuffer.asLongBuffer();
        this.leafMappedByteBuffer = this.leafFile.getChannel().map(FileChannel.MapMode.READ_WRITE, 0,
                this.leafMappedMaxSize);
        this.leafLongBuffer = this.leafMappedByteBuffer.asLongBuffer();
        if (length != 0) {
            this.MID_LENGTH_THRESHOLD = (int) this.metaLongBuffer.get(0);
            this.current_mid_size = this.metaLongBuffer.get(1);
            this.LEAF_LENGTH_THRESHOLD = (int) this.metaLongBuffer.get(2);
            this.current_leaf_size = this.metaLongBuffer.get(3);
            this.rootOffset = this.metaLongBuffer.get(4);
            this.isRootLeaf = this.metaLongBuffer.get(5);
        } else {
            this.metaLongBuffer.put(0, this.MID_LENGTH_THRESHOLD);
            this.metaLongBuffer.put(1, 0);
            this.metaLongBuffer.put(2, this.LEAF_LENGTH_THRESHOLD);
            this.metaLongBuffer.put(3, 0);
            this.metaLongBuffer.put(4, -1);
            this.metaLongBuffer.put(5, 1);
        }
        if (this.rootOffset != -1) {
            // we can read the root node.
            this.root = readNode((int) this.rootOffset, this.isRootLeaf == 1l);
        }
    }


    /**
     * construct a bplustree with specific saving path, leaf node mapped file size and mid node mapped file size
     *
     * @param path
     * @throws IOException
     */
    public BPlusTree(String path, int leafMappedMaxSize, int midMappedMaxSize) throws IOException {
        this.PATH = path;
        this.leafMappedMaxSize = leafMappedMaxSize;
        this.midMappedMaxSize = midMappedMaxSize;
        File p = new File(path);
        if (!p.exists()) p.mkdirs();
        this.leafFile = new RandomAccessFile(this.PATH + "/" + this.leafFileName, "rw");
        this.midFile = new RandomAccessFile(this.PATH + "/" + this.midFileName, "rw");
        this.metaFile = new RandomAccessFile(this.PATH + "/" + this.metaFileName, "rw");
        long length = this.metaFile.length();
        this.metaMappedByteBuffer = this.metaFile.getChannel()
                .map(FileChannel.MapMode.READ_WRITE, 0, metaSize);
        this.metaLongBuffer = this.metaMappedByteBuffer.asLongBuffer();
        this.midMappedByteBuffer = this.midFile.getChannel().map(FileChannel.MapMode.READ_WRITE,
                0, this.midMappedMaxSize);
        this.midLongBuffer = this.midMappedByteBuffer.asLongBuffer();
        this.leafMappedByteBuffer = this.leafFile.getChannel().map(FileChannel.MapMode.READ_WRITE, 0,
                this.leafMappedMaxSize);
        this.leafLongBuffer = this.leafMappedByteBuffer.asLongBuffer();
        if (length != 0) {
            this.MID_LENGTH_THRESHOLD = (int) this.metaLongBuffer.get(0);
            this.current_mid_size = this.metaLongBuffer.get(1);
            this.LEAF_LENGTH_THRESHOLD = (int) this.metaLongBuffer.get(2);
            this.current_leaf_size = this.metaLongBuffer.get(3);
            this.rootOffset = this.metaLongBuffer.get(4);
            this.isRootLeaf = this.metaLongBuffer.get(5);
        } else {
            this.metaLongBuffer.put(0, this.MID_LENGTH_THRESHOLD);
            this.metaLongBuffer.put(1, 0);
            this.metaLongBuffer.put(2, this.LEAF_LENGTH_THRESHOLD);
            this.metaLongBuffer.put(3, 0);
            this.metaLongBuffer.put(4, -1);
            this.metaLongBuffer.put(5, 1);
        }
        if (this.rootOffset != -1) {
            // we can read the root node.
            this.root = readNode((int) this.rootOffset, this.isRootLeaf == 1l);
        }
    }

    private Node readNode(int offset, boolean isLeaf) {
        Node node = new Node(offset, isLeaf);
        return node;
    }

    /**
     * try to add a key, value pair to this bplustree. replace previous
     * value if a same key already exists.
     *
     * @param key
     * @param value
     */
    public void addOrUpdate(long key, long value) {
        if (this.root == null) {
            this.rootOffset = 0;
            this.isRootLeaf = 1l;
            this.root = new Node(0, -1, true);
            updateRoot(0, 1l);
            this.current_leaf_size ++;
            updateLeafNodeSize();
            this.root.leafNode.addOrUpdateKey(key, value);
        } else {
            Node n = searchForAdd(key);
            n.leafNode.addOrUpdateKey(key, value);
        }
    }

    private void updateRoot(long offset, long isLeaf) {
        this.rootOffset = offset;
        this.isRootLeaf = isLeaf;
        metaLongBuffer.put(4, offset);
        metaLongBuffer.put(5, isLeaf);
    }

    private void updateLeafNodeSize() {
        metaLongBuffer.put(3, this.current_leaf_size);
    }

    private void updateMidNodeSize() {
        metaLongBuffer.put(1, this.current_mid_size);
    }

    /**
     * @param key
     * @return the leaf node that the key should appear.
     */
    private Node search(long key) {
        Node node = this.root;
        while (node != null) {
            if (node.isLeaf()) {
                return node;
            } else {
                node = node.midNode.leafOfKey(key);
            }
        }
        return null;
    }

    /**
     * @param key
     * @return the leaf node that the key should appear.
     */
    private Node searchForAdd(long key) {
        Node node = this.root;
        while (node != null) {
            if (node.isLeaf()) {
                return node;
            } else {
                node = node.midNode.leafOfKeyForUpdate(key);
            }
        }
        return null;
    }

    /**
     * try to get the associated value for the key and return a negative integer
     * if no such key exists
     *
     * @param key
     * @return
     */
    public long get(long key) {
        Node ans = this.search(key);
        if (ans != null) {
            long value = ans.leafNode.getValue(key);
            // todo
            return value;
        } else {
            // todo
            return -1l;
        }
    }

    /**
     * try to remove and return the associated value for the key, return <tt>-1</tt>
     * if no such key exists.
     *
     * @param key
     * @return the associated value for the key, return <tt>-1</tt> if no such key exists.
     */
    public Long remove(long key) {
        if (this.root == null) return null;
        Node n = searchForAdd(key);
        return n.leafNode.removeKey(key);
    }

    /**
     * @param key
     * @return whether this bplustree contains the key.
     */
    public boolean contains(long key) {
        Long ans =  this.get(key);
        return ans != null && ans > 0;
    }

    /**
     * a simple Node holder
     */
    class Node {

        MidNode midNode;
        LeafNode leafNode;
        boolean leaf;

        public Node(int offset, int parentOffset, boolean leaf) {
            this.leaf = leaf;
            if (this.leaf) {
                this.leafNode = new LeafNode(offset, parentOffset);
            } else {
                this.midNode = new MidNode(offset, parentOffset);
            }
        }

        public Node(int offset, boolean isLeaf) {
            this.leaf = isLeaf;
            if (this.leaf) {
                this.leafNode = new LeafNode(offset);
            } else {
                this.midNode = new MidNode(offset);
            }
        }

        /**
         * @return whether this node is a leaf node
         */
        public boolean isLeaf() {
            return this.leaf;
        }

        public void resetLeaf(int offset, int parentOffset, boolean isLeaf) {
            this.leaf = isLeaf;
            if (this.leaf) {
                this.leafNode = new LeafNode(offset, parentOffset);
            } else {
                this.midNode = new MidNode(offset, parentOffset);
            }
        }

    }

    private Node dummyLeaf = new Node(-1,  true);
    private Node dummyMid = new Node(-1, false);

    /**
     * a mid node has:
     * long -> size.
     * long -> parentOffset.
     * keys which holding $size items: [long, long] pair and the first long is key, second is offset
     */
    class MidNode {

        int offset;
        int parentOffset = -2;

        public MidNode(int offset, int parentOffset) {
            this.offset = offset;
            this.parentOffset = parentOffset;
            this.setParentOffset(parentOffset);
        }

        public MidNode(int offset) {
            this.offset = offset;
        }

        public long getParentOffset() {
            return midLongBuffer.get(offset + 1);
        }

        public void setParentOffset(long parentOffset) {
            midLongBuffer.put(offset + 1, parentOffset);
        }

        public long getSize() {
            return midLongBuffer.get(offset);
        }

        public void setSize(long size) {
            midLongBuffer.put(offset, size);
        }

        public long getKey(int i) {
            return midLongBuffer.get(offset + 2 + (i * 2));
        }

        /**
         * add the key to this midNode. the isLeaf represent that whether this offset point to
         * a leaf node.
         *
         * @param replacedKey the left key, it needs updated.
         * @param rightKey and keyOffset needs to be inserted.
         * @param isLeaf
         */
        public void addKey(long replacedKey, long replaceOffset,
                           long rightKey, long rightKeyOffset, boolean isLeaf) {
            if (isLeaf) { rightKeyOffset |= (1l << 63); replaceOffset |= (1l << 63); }
            int size = (int) this.getSize();
            if (size == 0) {
                this.setSize(2);
                midLongBuffer.put(offset + 2, replacedKey);
                midLongBuffer.put(offset + 2 + 1, replaceOffset);
                midLongBuffer.put(offset + 2 + 2, rightKey);
                midLongBuffer.put(offset + 2 + 3, rightKeyOffset);
                return;
            }
            this.readBuffer(buffer, size);
            int ans = this.binarySearch(replacedKey, size);
            if (size >= MID_LENGTH_THRESHOLD) {
                // need split.
                System.arraycopy(buffer, ans * 2 + 2, buffer, ans * 2 + 4,
                        (size - ans - 1) * 2);
                buffer[ans * 2] = replacedKey;
                buffer[ans * 2 + 1] = replaceOffset;
                buffer[ans * 2 + 2] = rightKey;
                buffer[ans * 2 + 3] = rightKeyOffset;
                splitAndAdd(replacedKey, ans, isLeaf);
            } else {
                this.setSize(size + 1);
                if (ans == size - 1) {
                    midLongBuffer.put(offset + 2 + ans * 2, replacedKey);
                    midLongBuffer.put(offset + 2 + ans * 2 + 1, replaceOffset);
                    midLongBuffer.put(offset + 2 + ans * 2 + 2, rightKey);
                    midLongBuffer.put(offset + 2 + ans * 2 + 3, rightKeyOffset);
                } else {
                    System.arraycopy(buffer, ans * 2 + 2, buffer, ans * 2 + 4,
                            (size - ans - 1) * 2);
                    buffer[ans * 2] = replacedKey;
                    buffer[ans * 2 + 1] = replaceOffset;
                    buffer[ans * 2 + 2] = rightKey;
                    buffer[ans * 2 + 3] = rightKeyOffset;
                    this.putBuffer(buffer, ans , ans * 2, size - ans + 1);
                }
            }
        }

        public void init(int offset, int parentOffset) {
            this.offset = offset;
            this.parentOffset = parentOffset;
            this.setParentOffset(parentOffset);
        }

        public void init(int offset) {
            this.offset = offset;
        }

        public void splitAndAdd(long replaceKey, int loc, boolean isLeaf) {
            // do balancing here.
            long min = buffer[0], max = buffer[MID_LENGTH_THRESHOLD * 2];
            long splitKey = (min / 2 + max / 2);
            int size = binarySearch(splitKey, MID_LENGTH_THRESHOLD + 1) + 1;
            size --;
            splitKey = buffer[ (size - 1) * 2];
            this.setSize(size);
            // todo here.
            // buffer[ (size - 1) * 2] = splitKey;
            if (splitKey >= replaceKey) {
                this.putBuffer(buffer, loc, loc * 2, size - loc);
            }
            this.parentOffset = (int) this.getParentOffset();
            long newNodeOffset = current_mid_size * MIDBlock / 8;
            current_mid_size ++;
            updateMidNodeSize();
            Node rightNode = new Node((int) newNodeOffset, parentOffset, false);
            rightNode.midNode.putBuffer(buffer, 0, size * 2,
                    (MID_LENGTH_THRESHOLD - size + 1));
            rightNode.midNode.setSize(MID_LENGTH_THRESHOLD - size + 1);
            // update children's parent offset.
            for (int i = 0; i < MID_LENGTH_THRESHOLD - size + 1; i ++) {
                int child = (int) buffer[ (size + i) * 2 + 1];
                if (isLeaf) leafLongBuffer.put((int) child + 3, newNodeOffset);
                else midLongBuffer.put((int) child + 1, newNodeOffset);
            }

            if (parentOffset == -1) {
                int rootOffset = (int) (current_mid_size * MIDBlock / 8);
                this.setParentOffset(rootOffset);
                root.midNode.init(rootOffset, -1);
                current_mid_size++;
                updateMidNodeSize();
                updateRoot(rootOffset, 0l);
                rightNode.midNode.setParentOffset(rootOffset);
                root.midNode.addKey(splitKey, this.offset, max, newNodeOffset, false);
            } else {
                long preOffset = this.offset;
                Node parentNode = new Node(parentOffset, false);
                parentNode.midNode.addKey(splitKey, preOffset,
                        max, newNodeOffset, false);
            }
        }

        public void readBuffer(long[] buffer, int size) {
            midLongBuffer.position(offset + 2);
            midLongBuffer.get(buffer, 0, size * 2);
        }

        public void putBuffer(long[] buffer, int preSize, int from, int size) {
            midLongBuffer.position(offset + 2 + preSize * 2);
            midLongBuffer.put(buffer, from, size * 2);
        }

        public Node leafOfKey(long key) {
            int left = 0, right = (int) this.getSize() - 1;
            int ans = right;
            this.readBuffer(buffer, right + 1);
            while (left <= right) {
                int mid = (left + right) >>> 1;
                if (buffer[mid * 2] >= key) {
                    ans = mid;
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            }
            long childNodeOffset = buffer[ans * 2 + 1];
            return readNode( (int) (childNodeOffset & (~(1l << 63))), childNodeOffset < 0);
        }

        public Node leafOfKeyForUpdate(long key) {
            int left = 0, right = (int) this.getSize() - 1;
            int ans = right;
            this.readBuffer(buffer, right + 1);
            while (left <= right) {
                int mid = (left + right) >>> 1;
                if (buffer[mid * 2] >= key) {
                    ans = mid;
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            }
            if (ans == right) {
                if (buffer[ans * 2] <= key) midLongBuffer.put(offset + 2 + (ans * 2), key);
            }
            long childNodeOffset = buffer[ans * 2 + 1];
            return readNode( (int) (childNodeOffset & (~(1l << 63))), childNodeOffset < 0);
        }

        /**
         * search the location of key. if exist, would return the location of the key,
         * otherwise return the less key beforehead the search key. return <tt>-1</tt>
         * if all key are larger than the search key.
         *
         * @param key
         * @param size
         * @return
         */
        public int binarySearch(long key, int size) {
            int left = 0, right = size - 1;
            int ans = right;
            while (left <= right) {
                int mid = (left + right) >>> 1;
                if (buffer[mid * 2] >= key) {
                    ans = mid;
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            }
            return ans;
        }

    }

    static long[] buffer = new long[ (1024 * 4) / 8];


    /**
     * a leaf node holder.
     *
     * storing as :
     * long -> size
     * long -> previousOffset
     * long -> nextOffset
     * long -> parentOffset
     *
     * follows -> key, info pairs.
     */
    class LeafNode {

        int offset;

        public LeafNode(int offset) {
            this.offset = offset;
        }


        public LeafNode(int offset, int parentOffset) {
            this.offset = offset; this.setParentOffset(parentOffset);
        }

        public long getParentOffset() {
            return leafLongBuffer.get(offset + 3);
        }

        public void setParentOffset(long parentOffset) {
            leafLongBuffer.put(offset + 3, parentOffset);
        }

        public long getPreviousOffset() {
            return leafLongBuffer.get(offset + 1);
        }

        public long getSize() {
            return leafLongBuffer.get(offset);
        }

        public long getNextOffset() {
            return leafLongBuffer.get(offset + 2);
        }

        public void setSize(long size) {
            leafLongBuffer.put(offset, size);
        }

        public void setPreviousOffset(long previousOffset) {
            leafLongBuffer.put(offset + 1, previousOffset);
        }

        public void setNextOffset(long nextOffset) {
            leafLongBuffer.put(offset + 2, nextOffset);
        }

        public long getKey(int i) {
            return leafLongBuffer.get(offset + 4 + (i * 2));
        }

        public void setKeyInfo(int i, long info) {
            leafLongBuffer.put(offset + 4 + (i * 2) + 1, info);
        }

        /**
         * @param key
         * @return the value associated with the key.
         */
        public long getValue(long key) {
            int size = (int) this.getSize();
            this.readBuffer(buffer, size);
            int ans = binarySearch(key, size);
            if (ans != -1 && buffer[ans * 2] == key) {
                return buffer[ans * 2 + 1];
            }
            return -1;
        }

        /**
         * add a key,info pair to this node, if the key already appeared, then replace the previous info.
         * if the size of this node exceeds the limit, then split these nodes.
         *
         * @param key
         * @param info
         */
        public void addOrUpdateKey(long key, long info) {
            int size = (int) this.getSize();
            if (size == 0) {
                this.setPreviousOffset(-1);
                this.setNextOffset(-1);
                this.setSize(1);
                leafLongBuffer.put(offset + 4, key);
                leafLongBuffer.put(offset + 5, info);
                return;
            }
            this.readBuffer(buffer, size);
            int ans = binarySearch(key, size);
            if (ans != -1 && buffer[ans * 2] == key) {
                // just need update.
                this.setKeyInfo(ans, info);
            }  else {
                System.arraycopy(buffer, ans * 2 + 2,
                        buffer, ans * 2 + 4, (size - ans - 1) * 2);
                buffer[ans * 2 + 2] = key;
                buffer[ans * 2 + 3] = info;
                // need insert.
                if (size >= LEAF_LENGTH_THRESHOLD) {
                    // do splitting.
                    // the splitting procedure first split this nodes to two leaf nodes.
                    // and then add an index to parent mid node which might cause splitting procedure of
                    // parent node.
                    splitAndAdd(key, info, ans);
                } else {
                    this.setSize(size + 1);
                    this.putBuffer(buffer, ans + 1, ans * 2 + 2, size - ans);
                }
            }
        }

        public long removeKey(long key) {
            int size = (int) this.getSize();
            if (size == 0) {
                return -1;
            }
            this.readBuffer(buffer, size);
            int ans = binarySearch(key, size);
            if (ans != -1 && buffer[ans * 2] == key) {
                long info = leafLongBuffer.get(offset + 4 + (ans * 2) + 1);
                this.setKeyInfo(ans, -1);
                return info;
            }
            return -1;
        }

        public void init(int offset, int parentOffset) {
            this.offset = offset;
            this.setParentOffset(parentOffset);
        }

        public void splitAndAdd(long key, long info, int loc) {
            // do balancing here.
            long min = buffer[0], max = buffer[LEAF_LENGTH_THRESHOLD * 2];
            long splitKey = min / 2 + max / 2;
            int size = binarySearch(splitKey, LEAF_LENGTH_THRESHOLD + 1) + 1;
            if (splitKey >= key) {
                this.putBuffer(buffer, loc + 1, loc * 2 + 2, size - loc - 1);
            }
            int newNodeOffset = (int) (current_leaf_size * LeafBlock / 8);
            int parentOffset = (int) this.getParentOffset();
            Node rightNode = dummyLeaf;
            rightNode.leafNode.init(newNodeOffset, parentOffset);
            long nextOffset = this.getNextOffset();
            if (nextOffset != -1)
                leafLongBuffer.put((int) nextOffset + 1, newNodeOffset);
            this.setNextOffset(newNodeOffset);
            rightNode.leafNode.setSize(LEAF_LENGTH_THRESHOLD - size + 1);
            rightNode.leafNode.setPreviousOffset(offset);
            rightNode.leafNode.setNextOffset(nextOffset);
            rightNode.leafNode.putBuffer(buffer, 0, size * 2,
                    LEAF_LENGTH_THRESHOLD - size + 1);
            current_leaf_size ++;
            updateLeafNodeSize();
            this.setSize(size);
            if (parentOffset == -1) {
                // root node.
                this.setParentOffset(0);
                if (root.isLeaf()) root.resetLeaf(0, -1, false);
                current_mid_size ++;
                updateMidNodeSize();
                updateRoot(0, 0);
                rightNode.leafNode.setParentOffset(0);
                root.midNode.addKey(splitKey , offset, max , newNodeOffset, true);
            } else {
                // update parent Node directly.
                Node node = dummyMid;
                node.midNode.init(parentOffset);
                node.midNode.addKey(splitKey, offset, max, newNodeOffset, true);
            }
        }

        public void readBuffer(long[] buffer, int size) {
            leafLongBuffer.position(offset + 4);
            leafLongBuffer.get(buffer, 0, size * 2);
        }

        public void putBuffer(long[] buffer, int preSize, int from, int size) {
            leafLongBuffer.position(offset + 4 + preSize * 2);
            leafLongBuffer.put(buffer, from, size * 2);
        }

        /**
         * binary search a key, return the location if holding the key, otherwise
         * return the index of some key less than search key.
         *
         * @param key
         * @param size
         * @return
         */
        public int binarySearch(long key, int size) {
            int left = 0, right = size - 1;
            int ans = -1;
            while (left <= right) {
                int mid = (left + right) >>> 1;
                if (buffer[mid * 2] <= key) {
                    ans = mid;
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }
            return ans;
        }

        /**
         * @param key
         * @return whether this node has the key
         */
        public boolean hasKey(long key) {
            int size = (int) this.getSize();
            this.readBuffer(buffer, size);
            int ans = binarySearch(key, size);
            return ans != -1 && buffer[ans * 2] == key;
        }

    }

    /**
     * closing this bplustree
     */
    public void close() {
        AccessController.doPrivileged(new PrivilegedAction() {

            public Object run() {
                try {
                    Method getCleanerMethod = metaMappedByteBuffer.getClass().getMethod("cleaner",new Class[0]);
                    getCleanerMethod.setAccessible(true);
                    sun.misc.Cleaner cleaner =(sun.misc.Cleaner)getCleanerMethod.invoke(metaMappedByteBuffer,new Object[0]);
                    cleaner.clean();

                    getCleanerMethod = midMappedByteBuffer.getClass().getMethod("cleaner",new Class[0]);
                    getCleanerMethod.setAccessible(true);
                    cleaner =(sun.misc.Cleaner)getCleanerMethod.invoke(midMappedByteBuffer,new Object[0]);
                    cleaner.clean();

                    getCleanerMethod = leafMappedByteBuffer.getClass().getMethod("cleaner",new Class[0]);
                    getCleanerMethod.setAccessible(true);
                    cleaner =(sun.misc.Cleaner)getCleanerMethod.invoke(leafMappedByteBuffer,new Object[0]);
                    cleaner.clean();
                } catch(Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

        });

        try {
            this.metaFile.close();
            this.midFile.close();
            this.leafFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String arraysToString(int from, int till, boolean isLeaf) {
        StringBuilder build = new StringBuilder();
        if (isLeaf) {
            for (int i = from; i < till; i++) {
                build.append(" {" + buffer[i * 2] + " - " + buffer[i * 2 + 1] + "} ");
            }
        } else {
            for (int i = from; i < till; i++) {
                long offset = buffer[i * 2 + 1];
                if (offset < 0) build.append(" {" + buffer[i * 2] + " - " + ( offset & (~(1l << 63))) + "l} ");
                else build.append(" {" + buffer[i * 2] + " - " + (offset & (~(1l << 63))) + "m} ");
            }
        }
        return build.toString();
    }

    private void printNode(Node n, boolean isLeaf) {

        if (n.isLeaf()) {
            n.leafNode.readBuffer(buffer, (int) n.leafNode.getSize());
            System.err.println("[leaf-" + (int) n.leafNode.offset + "] : " + " size : " +
                    n.leafNode.getSize() + ", previousOffset : " +
                    n.leafNode.getPreviousOffset() + ", nextOffset : " +
                    n.leafNode.getNextOffset() + " , parentOffset : " +
                    n.leafNode.getParentOffset() + " . key-value : " +
                    arraysToString(0, (int) n.leafNode.getSize(),  true));
        } else {
            n.midNode.readBuffer(buffer, (int) n.midNode.getSize());
            System.err.println("[mid-" + (int) n.midNode.offset + "] : " + " size : " +
                    n.midNode.getSize() + " , parentOffset : " +
                    n.midNode.getParentOffset() +
                    ". key-value : " +
                    arraysToString(0, (int) n.midNode.getSize(), false));
        }
    }

    public void printTree() {
        LinkedList<Integer> offsetList = new LinkedList<>();
        LinkedList<Boolean> isLeafList = new LinkedList<>();
        offsetList.add((int) this.rootOffset);
        isLeafList.add(this.isRootLeaf == 1l);
        while (!offsetList.isEmpty()) {
            int offset = offsetList.pollFirst();
            Boolean isLeaf = isLeafList.pollFirst();
            Node n = readNode(offset, isLeaf);
            printNode(n, isLeaf);
            if (!isLeaf) {
                int size = (int) n.midNode.getSize();
                n.midNode.readBuffer(buffer, size);
                for (int i = 0; i < size; i++) {
                    long childNodeOffset = buffer[i * 2 + 1];
                    offsetList.addLast( (int) (childNodeOffset & (~(1l << 63))) );
                    isLeafList.addLast( childNodeOffset < 0 );
                }
            }
        }
    }


}

