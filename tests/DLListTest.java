import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

import static org.junit.Assert.assertEquals;

public class DLListTest {

    @Test public void testCreation() {
        List<String> keys = Arrays.asList("Alice", "Bob", "Carol");
        DLList<String> list0 = new DLList<>(keys);
        assertEquals("[Alice, Bob, Carol]", list0.toString());
        list0.insertBack("Dave");
        assertEquals("[Alice, Bob, Carol, Dave]", list0.toString());
        list0.insertFront("Zora");
        assertEquals("[Zora, Alice, Bob, Carol, Dave]", list0.toString());
        list0.removeBack();
        list0.removeBack();
        assertEquals("[Zora, Alice, Bob]", list0.toString());
        list0.removeFront();
        list0.removeFront();
        assertEquals("[Bob]", list0.toString());
        list0.removeBack();
        assertEquals("[]", list0.toString());
    }

    @Test public void testMerge() {
        List<Integer> keys0 = Arrays.asList(3, 7, 9, 17, 19, 22, 25);
        DLList<Integer> list0 = new DLList<>(keys0);
        List<Integer> keys1 = Arrays.asList(1, 2, 5, 7, 21, 22);
        DLList<Integer> list1 = new DLList<>(keys1);
        DLList<Integer> merged = DLList.merge(list0, list1);
        assertEquals("[]", list0.toString());
        assertEquals("[]", list1.toString());
        assertEquals("[1, 2, 3, 5, 7, 7, 9, 17, 19, 21, 22, 22, 25]", merged.toString());
    }

    @Test public void testRotate() {
        List<Integer> keys = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
        DLList<Integer> list = new DLList<>(keys);
        list.rotateLeft(3);
        assertEquals("[4, 5, 6, 7, 8, 9, 1, 2, 3]", list.toString());
        list.rotateLeft(0);
        assertEquals("[4, 5, 6, 7, 8, 9, 1, 2, 3]", list.toString());
        list.rotateRight(8);
        assertEquals("[5, 6, 7, 8, 9, 1, 2, 3, 4]", list.toString());
    }

    @Test public void testRemoveAll() {
        List<Integer> keys = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
        DLList<Integer> list = new DLList<>(keys);
        list.removeIf(e -> e % 2 == 0);
        assertEquals("[1, 3, 5, 7, 9]", list.toString());
        list.removeIf(e -> 2 < e && e < 8);
        assertEquals("[1, 9]", list.toString());
        list.removeIf(e -> true);
        assertEquals("[]", list.toString());
    }

    @Test public void testIterator() {
        List<Integer> keys = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
        DLList<Integer> list = new DLList<>(keys);
        ListIterator<Integer> i1 = list.iterator();
        List<Integer> items = new ArrayList<>();
        while(i1.hasNext()) {
            items.add(i1.next());
        }
        assertEquals(keys, items);
        items.clear();
        ListIterator<Integer> i2 = list.iterator();
        while(i2.hasNext()) {
            i2.set(i2.next() + 1);
        }
        assertEquals("[2, 3, 4, 5, 6, 7, 8, 9, 10]", list.toString());
    }
}
