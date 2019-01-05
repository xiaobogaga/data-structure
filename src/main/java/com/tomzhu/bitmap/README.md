a simple bitmap implementation. it supports `set(int loc)`, `clear(int i)`,
`isSet(int loc)`, `isClear(int loc)` methods.

**Usage**

```java
BitMap bitMap = new BitMap(1000);
bitMap.set(10);
bitMap.isSet(10); // return true
bitMap.clear(10);
bitMap.isSet(10); // return false
```

go to test file for more usages.