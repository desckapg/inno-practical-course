package by.desckapg.linkedlist;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class LinkedListTest {

    @Test
    void addFirst_multipleElements_firstElementIsLastAdded() {
        LinkedList<Integer> list = new LinkedList<>();
        list.addFirst(10);
        list.addFirst(20);
        assertThat(list.getFirst()).isEqualTo(20);
        assertThat(list.size()).isEqualTo(2);
    }

    @Test
    void addLast_multipleElements_lastElementIsLastAdded() {
        LinkedList<String> list = new LinkedList<>();
        list.addLast("a");
        list.addLast("b");
        assertThat(list.getLast()).isEqualTo("b");
        assertThat(list.size()).isEqualTo(2);
    }

    @Test
    void get_indexWithinBounds_returnsCorrectElement() {
        LinkedList<Integer> list = new LinkedList<>();
        list.addLast(1);
        list.addLast(2);
        list.addLast(3);
        assertThat(list.get(0)).isEqualTo(1);
        assertThat(list.get(1)).isEqualTo(2);
        assertThat(list.get(2)).isEqualTo(3);
    }

    @Test
    void removeFirst_nonEmptyList_removesAndReturnsFirstElement() {
        LinkedList<Integer> list = new LinkedList<>();
        list.addLast(1);
        list.addLast(2);
        int removed = list.removeFirst();
        assertThat(removed).isEqualTo(1);
        assertThat(list.size()).isEqualTo(1);
        assertThat(list.getFirst()).isEqualTo(2);
    }

    @Test
    void removeLast_nonEmptyList_removesAndReturnsLastElement() {
        LinkedList<Integer> list = new LinkedList<>();
        list.addLast(1);
        list.addLast(2);
        int removed = list.removeLast();
        assertThat(removed).isEqualTo(2);
        assertThat(list.size()).isEqualTo(1);
        assertThat(list.getLast()).isEqualTo(1);
    }

    @Test
    void remove_indexWithinBounds_removesAndReturnsElement() {
        LinkedList<String> list = new LinkedList<>();
        list.addLast("a");
        list.addLast("b");
        list.addLast("c");
        String removed = list.remove(1);
        assertThat(removed).isEqualTo("b");
        assertThat(list.size()).isEqualTo(2);
        assertThat(list.get(0)).isEqualTo("a");
        assertThat(list.get(1)).isEqualTo("c");
    }

    @Test
    void getFirst_emptyList_throwsNoSuchElementException() {
        LinkedList<Integer> list = new LinkedList<>();
        assertThatThrownBy(list::getFirst).isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void getLast_emptyList_throwsNoSuchElementException() {
        LinkedList<Integer> list = new LinkedList<>();
        assertThatThrownBy(list::getLast).isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void removeFirst_emptyList_throwsNoSuchElementException() {
        LinkedList<Integer> list = new LinkedList<>();
        assertThatThrownBy(list::removeFirst).isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void removeLast_emptyList_throwsNoSuchElementException() {
        LinkedList<Integer> list = new LinkedList<>();
        assertThatThrownBy(list::removeLast).isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void get_indexOutOfBounds_throwsIndexOutOfBoundsException() {
        LinkedList<Integer> list = new LinkedList<>();
        list.addLast(1);
        assertThatThrownBy(() -> list.get(-1)).isInstanceOf(IndexOutOfBoundsException.class);
        assertThatThrownBy(() -> list.get(1)).isInstanceOf(IndexOutOfBoundsException.class);
    }

    @Test
    void remove_indexOutOfBounds_throwsIndexOutOfBoundsException() {
        LinkedList<Integer> list = new LinkedList<>();
        list.addLast(1);
        assertThatThrownBy(() -> list.remove(-1)).isInstanceOf(IndexOutOfBoundsException.class);
        assertThatThrownBy(() -> list.remove(1)).isInstanceOf(IndexOutOfBoundsException.class);
    }

    @Test
    void add_indexZeroOnEmptyList_thrownIndexOutOfBoundsException() {
        LinkedList<Integer> list = new LinkedList<>();
        assertThatThrownBy(() -> list.add(0, 42)).isInstanceOf(IndexOutOfBoundsException.class);
    }

    @Test
    void add_indexInHead_insertsAsNewHead() {
        LinkedList<Integer> list = new LinkedList<>();
        list.addLast(1);
        list.add(0, 2);
        assertThat(list.size()).isEqualTo(2);
        assertThat(list.get(0)).isEqualTo(2);
        assertThat(list.get(1)).isEqualTo(1);
    }

    @Test
    void add_indexInMiddle_insertsBetweenElements() {
        LinkedList<Integer> list = new LinkedList<>();
        list.addLast(1);
        list.addLast(3);

        assertThat(list.get(0)).isEqualTo(1);
        assertThat(list.get(1)).isEqualTo(3);
        list.add(1, 2);

        assertThat(list.size()).isEqualTo(3);
        assertThat(list.get(0)).isEqualTo(1);
        assertThat(list.get(1)).isEqualTo(2);
        assertThat(list.get(2)).isEqualTo(3);
    }

    @Test
    void add_indexInTail_insertsBetweenElements() {
        LinkedList<Integer> list = new LinkedList<>();
        list.addLast(1);
        list.addLast(2);
        list.add(1,42);
        assertThat(list.size()).isEqualTo(3);
        assertThat(list.get(0)).isEqualTo(1);
        assertThat(list.get(1)).isEqualTo(42);
        assertThat(list.get(2)).isEqualTo(2);
    }

    @Test
    void add_indexOutOfBounds_throwsIndexOutOfBoundsException() {
        LinkedList<Integer> empty = new LinkedList<>();
        assertThatThrownBy(() -> empty.add(-1, 1)).isInstanceOf(IndexOutOfBoundsException.class);
        assertThatThrownBy(() -> empty.add(1, 1)).isInstanceOf(IndexOutOfBoundsException.class);

        LinkedList<Integer> list = new LinkedList<>();
        list.addLast(1);
        list.addLast(2);
        assertThatThrownBy(() -> list.add(-1, 0)).isInstanceOf(IndexOutOfBoundsException.class);
        assertThatThrownBy(() -> list.add(2, 0)).isInstanceOf(IndexOutOfBoundsException.class);
    }

}
