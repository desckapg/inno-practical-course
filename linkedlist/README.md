# LinkedList (Java)

A generic doubly linked list implementation in Java. Not thread-safe. Provides O(1) operations at the ends and O(n) indexed operations.

## Features
- Generic type parameter (LinkedList<T>)
- addFirst, addLast, add(index, value)
- getFirst, getLast, get(index)
- removeFirst, removeLast, remove(index)
- size()
- Allows null elements

## Complexity (amortized)
- addFirst / addLast / removeFirst / removeLast: O(1)
- get / add(index, value) / remove(index): O(n)

## API Summary
- int size()
- T getFirst() — throws NoSuchElementException if empty
- T getLast() — throws NoSuchElementException if empty
- T get(int index) — throws IndexOutOfBoundsException if index ∉ [0, size-1]
- void addFirst(T value)
- void addLast(T value)
- void add(int index, T value)
  - Inserts before the element at index.
  - index == 0 equals addFirst(value).
  - index == size appends to the end (same effect as addLast(value)).
  - Otherwise throws IndexOutOfBoundsException if index ∉ [0, size].
- T removeFirst() — throws NoSuchElementException if empty
- T removeLast() — throws NoSuchElementException if empty
- T remove(int index) — throws IndexOutOfBoundsException if index ∉ [0, size-1]

Note: This list is not thread-safe.

## Usage

```java
package demo;

import by.desckapg.linkedlist.LinkedList;

public class Demo {
    public static void main(String[] args) {
        LinkedList<Integer> list = new LinkedList<>();

        list.addFirst(2);      // [2]
        list.addLast(3);       // [2, 3]
        list.add(1, 99);       // [2, 99, 3]

        System.out.println(list.getFirst()); // 2
        System.out.println(list.getLast());  // 3
        System.out.println(list.get(1));     // 99

        list.remove(1);        // [2, 3]
        list.removeFirst();    // [3]
        list.removeLast();     // []

        System.out.println(list.size());     // 0
    }
}
```

## Build and Run

- Plain javac (example):

```bash
# From project root
javac -d out src/main/java/by/desckapg/linkedlist/LinkedList.java Demo.java
java -cp out demo.Demo
```

- Maven/Gradle: add this source under src/main/java and compile as usual.

## Notes
- Index bounds are strictly checked.
- The implementation is optimized for operations at the ends.
- No synchronization — wrap externally if used across threads.


