package com.tomzhu.hash;

/**
 * a simple hopscotch hash table implementation.
 * <p>
 * The hop information is holded by the hop variable.
 */
public class HopscotchHashTable<K, V> {

    /**
     * a simple key-value pair holder.
     */
    class Entry {
        private K key;
        private V value;

        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    /**
     * a table item, holding entry and the hop information.
     */
    class TableItem {
        private Entry entry;
        private long hop = 0;

        public TableItem(Entry entry, long hop) {
            this.entry = entry;
            this.hop = hop;
        }
    }

    private TableItem[] tables;
    private int capacity;
    private int size = 0;
    private int hopSize;

    /**
     * construct a hopscotchashTable by initialCapacity and hopSize.
     *
     * @param initialCapacity
     * @param hopSize
     */
    public HopscotchHashTable(int initialCapacity, int hopSize) {
        this.capacity = sizeFor(initialCapacity);
        this.tables = new HopscotchHashTable.TableItem[this.capacity];
        this.hopSize = hopSize;
    }

    /**
     * construct a default hopscotchHashTable with initialCapacity (16) and hopSize (4)
     */
    public HopscotchHashTable() {
        this.capacity = 16;
        this.hopSize = 4;
        this.tables = new HopscotchHashTable.TableItem[this.capacity];
    }


    /**
     * return a appropriate size for current hopscotch hash table.
     *
     * @param initialCapacity
     * @return
     */
    private int sizeFor(int initialCapacity) {
        if (initialCapacity <= 0)
            return 16;
        initialCapacity |= initialCapacity >>> 1;
        initialCapacity |= initialCapacity >>> 2;
        initialCapacity |= initialCapacity >>> 4;
        initialCapacity |= initialCapacity >>> 8;
        initialCapacity |= initialCapacity >>> 16;
        return initialCapacity + 1;
    }

    /**
     * insert the key-value pair to current map.
     *
     * @param key
     * @param value
     */
    public void insert(K key, V value) {
        int l = hash(key) & (this.capacity - 1);
        TableItem item = this.tables[l];
        if (item == null) {
            this.tables[l] = new TableItem(new Entry(key, value), 1);
            this.size++;
            return;

        }
        // try to replacing existing element.
        int i = 0;
        while (i < this.hopSize) {
            if (((item.hop >>> i) & 1) == 1) {
                int t = (l + i) & (this.capacity - 1);
                if (this.tables[t].entry.key.equals(key)) {
                    this.tables[t].entry.value = value;
                    return;
                }
            }
            i++;
        }
        // try to find a vacant location to insert.
        Entry n = new Entry(key, value);
        boolean hasVacant = false;
        i = 1;
        int t = 0;
        while (i < this.capacity) {
            t = (l + i) & (this.capacity - 1);
            if (i < this.hopSize) {
                if (this.tables[t] == null) {
                    // must be 0, check whether this location is null.
                    this.tables[t] = new TableItem(n, 0);
                    this.size++;
                    item.hop |= (1 << i);
                    return;
                } else if (this.tables[t].entry == null) {
                    this.tables[t].entry = n;
                    this.size++;
                    item.hop |= (1 << i);
                    return;
                }
            } else {
                if (this.tables[t] == null || this.tables[t].entry == null) {
                    // finding the first location by linear probing, will evict from this location.
                    hasVacant = true;
                    break;
                }
            }
            i++;
        }

        // cannot find location to insert, try to evict items.
        i = t;
        while (hasVacant) {
            int prev = i;
            i = (i - this.hopSize + 1 + this.capacity) & (this.capacity - 1);
            int step = 0, bitStep = 0;
            while (step < this.hopSize) {
                bitStep = 0;
                while (bitStep < (this.hopSize - step - 1)) {
                    if (((this.tables[i].hop >>> bitStep) & 1) == 1) {
                        // find a location to move.
                        t = (i + bitStep) & (this.capacity - 1);
                        if (this.tables[prev] == null)
                            this.tables[prev] = new TableItem(this.tables[t].entry, 0);
                        else
                            this.tables[prev].entry = this.tables[t].entry;
                        this.tables[i].hop &= ~(1 << bitStep);
                        this.tables[i].hop |= (1 << (this.hopSize - step - 1));
                        if ((t > l && t - l < this.hopSize) ||
                                (t <= l && (t + this.capacity - l) < this.hopSize)) {
                            // finished.
                            this.tables[t].entry = n;
                            if (t > 1 && t - l < this.hopSize)
                                this.tables[l].hop |= (1 << (t - l));
                            else
                                this.tables[l].hop |= (1 << (t + this.capacity - l));
                            return;
                        } else {
                            this.tables[t].entry = null;
                        }
                        i = (i + bitStep) & (this.capacity - 1);
                        hasVacant = true;
                        step = this.hopSize;
                        break;
                    }
                    hasVacant = false;
                    bitStep++;
                }
                step++;
                if (!hasVacant)
                    i = (1 + i) & (this.capacity - 1);
            }
        }

        // if doesn't have location, double size and try insert.
        TableItem[] newtables = null;
        boolean finished = false;
        while (!finished) {
            finished = true;
            this.capacity = this.capacity << 1;
            this.size = 0;
            newtables = new HopscotchHashTable.TableItem[this.capacity];
            for (TableItem it : this.tables) {
                if (it != null && it.entry != null && !insertForRehash(newtables, it.entry)) {
                    finished = false;
                    break;
                }
            }
            if (finished) {
                finished = insertForRehash(newtables, n);
            }
        }
        this.tables = newtables;
    }

    /**
     * only used after rehashing.
     *
     * @param entry
     */
    private boolean insertForRehash(TableItem[] newTables, Entry entry) {
        int l = hash(entry.key) & (this.capacity - 1);
        TableItem item = newTables[l];
        if (item == null) {
            newTables[l] = new TableItem(entry, 1);
            this.size++;
            return true;
        }
        // try to find a vacant location to insert.
        boolean hasVacant = false;
        int i = 1, t = 0;
        while (i < this.capacity) {
            t = (l + i) & (this.capacity - 1);
            if (i < this.hopSize) {
                if (newTables[t] == null) {
                    // must be 0, check whether this location is null.
                    newTables[t] = new TableItem(entry, 0);
                    this.size++;
                    item.hop |= (1 << i);
                    return true;
                }
            } else {
                if (newTables[t] == null) {
                    // finding the first location by linear probing, will evict from this location.
                    hasVacant = true;
                    break;
                }
            }
            i++;
        }

        // cannot find location to insert, try to evict items.
        i = t;
        while (hasVacant) {
            int prev = i;
            i = (i - this.hopSize + 1 + this.capacity) & (this.capacity - 1);
            int step = 0, bitStep = 0;
            while (step < this.hopSize) {
                bitStep = 0;
                while (bitStep < (this.hopSize - step - 1)) {
                    if (((newTables[i].hop >>> bitStep) & 1) == 1) {
                        // find a location to move.
                        t = (i + bitStep) & (this.capacity - 1);
                        if (newTables[prev] == null)
                            newTables[prev] = new TableItem(newTables[t].entry, 0);
                        else
                            newTables[prev].entry = newTables[t].entry;
                        newTables[i].hop &= ~(1 << bitStep);
                        newTables[i].hop |= (1 << (this.hopSize - step - 1));
                        if ((t > l && t - l < this.hopSize) ||
                                (t <= l && (t + this.capacity - l) < this.hopSize)) {
                            // finished.
                            newTables[t].entry = entry;
                            if (t > 1 && t - l < this.hopSize)
                                newTables[l].hop |= (1 << (t - l));
                            else
                                newTables[l].hop |= (1 << (t + this.capacity - l));
                            return true;
                        }else {
                            newTables[t].entry = null;
                        }
                        i = (i + bitStep) & (this.capacity - 1);
                        hasVacant = true;
                        step = this.hopSize;
                        break;
                    }
                    hasVacant = false;
                    bitStep++;
                }
                step++;
                if (!hasVacant)
                    i = (1 + i) & (this.capacity - 1);
            }
        }
        return false;
    }

    /**
     * verify whether the current table contains the key.
     *
     * @param key
     * @return
     */
    public boolean contains(K key) {
        int l = hash(key) & (this.capacity - 1);
        TableItem item = this.tables[l];
        if (item == null || item.hop == 0)
            return false;
        int i = 0;
        while (i < this.hopSize) {
            if (((item.hop >>> i) & 1) == 1) {
                int t = (l + i) & (this.capacity - 1);
                if (this.tables[t].entry.key.equals(key))
                    return true;
            }
            i++;
        }
        return false;
    }

    /**
     * remove the key-value pair from current map, return false if not found.
     *
     * @param key
     * @return
     */
    public boolean remove(K key) {
        int l = hash(key) & (this.capacity - 1);
        TableItem item = this.tables[l];
        if (item == null || item.hop == 0)
            return false;
        int i = 0;
        while (i < this.hopSize) {
            if (((item.hop >>> i) & 1) == 1) {
                int t = (l + i) & (this.capacity - 1);
                if (this.tables[t].entry.key.equals(key)) {
                    this.tables[t].entry = null;
                    item.hop &= ~(1 << i);
                    this.size--;
                    return true;
                }
            }
            i++;
        }
        return false;
    }

    private int hash(K key) {
        int h = 0;
        h = key == null ? 0 : ((h = key.hashCode()) ^ (h >>> 16));
        return h < 0 ? -h : h;
    }


}
