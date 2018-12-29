this package provides basic array-liked data structure including:

* ArrayList
* LinkedList
* Queue
* Stack

## examples

### ArrayList

`ArrayList` provides basic operation like:

* `get(int i)` return the element at location `i`
* `insert(E ele, int i)` insert element at location `i`
* `remove(int i)` remove and return element at location `i`

**Usage:**

```java
ArrayList<Integer> list = new ArrayList<Integer> ()
list.insert(5, 0) // insert element 5 at head.
list.insert(6, list.getSize()) // insert element 6 at tail.
list.get(0) // will get 5.
list.get(1) // will get 6.
list.remove(0) // will remove and return element 5 at location 0
```

for more usage, see [API]()

### LinkedList

`LinkedList` just like `java.util.LinkedList`, it provides basic operations like:

* `get(int i)` return the element at location `i`
* `insert(E ele, int i)` insert element at location `i`
* `remove(int i)` remove and return element at location `i`

just like `ArrayList`