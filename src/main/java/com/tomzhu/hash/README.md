a group of hash table implementation, including:

* HashTableByLinearProbing
* HashTableByQuadraticProbing
* HashTableBySeparateChain
* PerfectHashTable
* CuckooHashTable
* HopscotchHashTable
* ExtensibleHashTable

Those hash table implementation both exposed similar and basic interfaces
like `insert(K key, V value)`, `remove(K key)`, `get(K key)`, `contains(K key)`,
I don't add iteration method for this hash table.

**Usage**

```java
HashTableByLinearProbing<Integer, Integer> hashTable = new
    HashTableByLinearProbing<Integer, Integer> ();
hashTable.insert(1, 2);
hashTable.contains(1); // return true
hashTable.get(1); // return 2
hashTable.remove(1);
hashTable.contains(1) // return false
```

for more detail, see [api doc]() for more.

