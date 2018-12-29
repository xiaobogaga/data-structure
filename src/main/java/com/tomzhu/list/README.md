this package provides basic array-liked data structure including:

* ArrayList
* LinkedList
* Queue
* Stack

## Examples

### ArrayList

`ArrayList` provides basic operation like:

* `get(int i)` return the element at location `i`
* `insert(E ele, int i)` insert element at location `i`
* `remove(int i)` remove and return element at location `i`

**Usage**

```java
ArrayList<Integer> list = new ArrayList<Integer> ()
list.insert(5, 0) // insert element 5 at head.
list.insert(6, list.getSize()) // insert element 6 at tail.
list.get(0) // will get 5.
list.get(1) // will get 6.
list.remove(0) // will remove and return element 5 at location 0
```

### LinkedList

`LinkedList` just like `java.util.LinkedList`, it provides basic operations like:

* `get(int i)` return the element at location `i`
* `insert(E ele, int i)` insert element at location `i`
* `remove(int i)` remove and return element at location `i`

just like `ArrayList`.

**Usage**

```java
LinkedList<Integer> list = new LinkedList();
list.insert(5, 0) // insert element 5 at head.
list.insert(6, list.getSize()) // insert element 6 at tail.
list.get(0) // will get 5.
list.get(1) // will get 6.
list.remove(0) // will remove and return element 5 at location 0
```

### Queue

`Queue` is a queue data structure implementation. it provides operations like:
`enQueue(E ele)`, `deQueue` and `getHead`.

**Usage**

```java
Queue<Integer> queue = new Queue<Integer>()
queue.enQueue(1);
queue.enQueue(2);
queue.enQueue(3); // 3 -> 2 -> 1
queue.deQueue(); // return 1.
// 3 -> 2
queue.getHead(); // return 2. 3 -> 2
```

### Stack

`Stack` is a stack implementation. it provides operations like `pop`, `push` and
`getHead` mewthods.

**Usage**

```java
Stack<Integer> stack = new Stack<Integer>()
stack.push(1)
stack.push(2)
stack.push(3) // 3 <- 2 <- 1
stack.pop() return 3 and change to 2 <- 1
stack.getHead() return 2
```

for more information. see [api doc]() and for real example, see test file.