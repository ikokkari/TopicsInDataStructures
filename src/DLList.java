import java.util.List;
import java.util.function.Predicate;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * Doubly linked cyclic list with an internal header node.
 * @param <E> Type of keys stored in this list.
 */
public class DLList<E> {

    // Header node of the cyclic doubly linked list.
    private final DLNode<E> HEAD = new DLNode<E>();

    /**
     * Default constructor of {@code DLList}.
     */
    public DLList() {}

    /**
     * Constructor of {@code DLList} from the given array of keys.
     * @param keys The array of keys to insert to the new list.
     */
    public DLList(E[] keys) {
        for(E key: keys) {
            HEAD.insertPredecessor(key);
        }
    }

    /**
     * Constructor of {@code DLList} from the given list of keys.
     * @param keys The array of keys to insert to the new list.
     */
    public DLList(List<E> keys) {
        for(E key: keys) {
            HEAD.insertPredecessor(key);
        }
    }

    /**
     * Insert a new node with the given key to the beginning of the list.
     * @param key Key of the new node to be created.
     * @return The new node that was created.
     */
    public DLNode<E> insertFront(E key) {
        return HEAD.insertSuccessor(key);
    }

    /**
     * Insert a new node with the given key to the end of the list.
     * @param key Key of the new node to be created.
     * @return The new node that was created.
     */
    public DLNode<E> insertBack(E key) {
        return HEAD.insertPredecessor(key);
    }

    /**
     * Remove the first node from the list.
     * @return The removed first node.
     */
    public DLNode<E> removeFront() {
        if(HEAD.getNext() == HEAD) {
            throw new IllegalStateException("Cannot remove front element in empty list");
        }
        return HEAD.getNext().unlink();
    }

    /**
     * Remove the last node from the list.
     * @return The removed last node.
     */
    public DLNode<E> removeBack() {
        if(HEAD.getNext() == HEAD) {
            throw new IllegalStateException("Cannot remove back element in empty list");
        }
        return HEAD.getPrevious().unlink();
    }

    /**
     * Compute the String representation of this list.
     * @return The String representation of this list.
     */
    @Override public String toString() {
        StringBuilder result = new StringBuilder("[");
        DLNode<E> curr = HEAD.getNext();
        while(curr != HEAD) {
            result.append(curr.getKey());
            curr = curr.getNext();
            if(curr != HEAD) { result.append(", "); }
        }
        result.append("]");
        return result.toString();
    }

    public ListIterator<E> iterator() {
        return new DLListIterator();
    }

    private class DLListIterator implements ListIterator<E> {

        private DLNode<E> current = HEAD;
        private DLNode<E> lastReturned = null;

        public DLListIterator() {}

        /**
         * Returns {@code true} if this list iterator has more elements when
         * traversing the list in the forward direction. (In other words,
         * returns {@code true} if {@link #next} would return an element rather
         * than throwing an exception.)
         *
         * @return {@code true} if the list iterator has more elements when
         * traversing the list in the forward direction
         */
        @Override
        public boolean hasNext() {
            return current.getNext() != HEAD;
        }

        /**
         * Returns the next element in the list and advances the cursor position.
         * This method may be called repeatedly to iterate through the list,
         * or intermixed with calls to {@link #previous} to go back and forth.
         * (Note that alternating calls to {@code next} and {@code previous}
         * will return the same element repeatedly.)
         *
         * @return the next element in the list
         * @throws NoSuchElementException if the iteration has no next element
         */
        @Override
        public E next() {
            if(!hasNext()) { throw new NoSuchElementException(); }
            lastReturned = current = current.getNext();
            return current.getKey();
        }

        /**
         * Returns {@code true} if this list iterator has more elements when
         * traversing the list in the reverse direction.  (In other words,
         * returns {@code true} if {@link #previous} would return an element
         * rather than throwing an exception.)
         *
         * @return {@code true} if the list iterator has more elements when
         * traversing the list in the reverse direction
         */
        @Override
        public boolean hasPrevious() {
            return current != HEAD;
        }

        /**
         * Returns the previous element in the list and moves the cursor
         * position backwards.  This method may be called repeatedly to
         * iterate through the list backwards, or intermixed with calls to
         * {@link #next} to go back and forth.  (Note that alternating calls
         * to {@code next} and {@code previous} will return the same
         * element repeatedly.)
         *
         * @return the previous element in the list
         * @throws NoSuchElementException if the iteration has no previous
         *                                element
         */
        @Override
        public E previous() {
            if(!hasPrevious()) { throw new NoSuchElementException(); }
            E result = current.getKey();
            lastReturned = current;
            current = current.getPrevious();
            return result;
        }

        /**
         * Returns the index of the element that would be returned by a
         * subsequent call to {@link #next}. (Returns list size if the list
         * iterator is at the end of the list.)
         *
         * @return the index of the element that would be returned by a
         * subsequent call to {@code next}, or list size if the list
         * iterator is at the end of the list
         */
        @Override
        public int nextIndex() {
            throw new UnsupportedOperationException("Method nextIndex not supported in DLList");
        }

        /**
         * Returns the index of the element that would be returned by a
         * subsequent call to {@link #previous}. (Returns -1 if the list
         * iterator is at the beginning of the list.)
         *
         *  @throws UnsupportedOperationException
         */
        @Override
        public int previousIndex() {
            throw new UnsupportedOperationException("Method previousIndex not supported in DLList");
        }

        /**
         * Removes from the list the last element that was returned by {@link
         * #next} or {@link #previous} (optional operation).  This call can
         * only be made once per call to {@code next} or {@code previous}.
         * It can be made only if {@link #add} has not been
         * called after the last call to {@code next} or {@code previous}.
         *
         * @throws IllegalStateException         if neither {@code next} nor
         *                                       {@code previous} have been called, or {@code remove} or
         *                                       {@code add} have been called after the last call to
         *                                       {@code next} or {@code previous}
         */
        @Override
        public void remove() {
            if(lastReturned == null) { throw new IllegalStateException("No previous element to remove"); }
            lastReturned.unlink();
            lastReturned = null;
        }

        /**
         * Replaces the last element returned by {@link #next} or
         * {@link #previous} with the specified element (optional operation).
         * This call can be made only if neither {@link #remove} nor {@link
         * #add} have been called after the last call to {@code next} or
         * {@code previous}.
         *
         * @param o the element with which to replace the last element returned by
         *          {@code next} or {@code previous}
         * @throws IllegalStateException         if neither {@code next} nor
         *                                       {@code previous} have been called, or {@code remove} or
         *                                       {@code add} have been called after the last call to
         *                                       {@code next} or {@code previous}
         */
        @Override
        public void set(E o) {
            if(lastReturned == null) { throw new IllegalStateException(); }
            lastReturned.setKey(o);
        }

        /**
         * Inserts the specified element into the list (optional operation).
         * The element is inserted immediately before the element that
         * would be returned by {@link #next}, if any, and after the element
         * that would be returned by {@link #previous}, if any.  (If the
         * list contains no elements, the new element becomes the sole element
         * on the list.)  The new element is inserted before the implicit
         * cursor: a subsequent call to {@code next} would be unaffected, and a
         * subsequent call to {@code previous} would return the new element.
         * (This call increases by one the value that would be returned by a
         * call to {@code nextIndex} or {@code previousIndex}.)
         *
         * @param o the element to insert
         * @throws UnsupportedOperationException if the {@code add} method is
         *                                       not supported by this list iterator
         * @throws ClassCastException            if the class of the specified element
         *                                       prevents it from being added to this list
         * @throws IllegalArgumentException      if some aspect of this element
         *                                       prevents it from being added to this list
         */
        @Override
        public void add(Object o) {
            current.insertSuccessor((E)o);
        }
    }


    /**
     * Cyclically rotate the nodes the number of steps to the left.
     * @param steps Number of steps to rotate the nodes.
     */
    public void rotateLeft(int steps) {
        for(int i = 0; i < steps; i++) {
            this.HEAD.linkPredecessor(this.HEAD.getNext().unlink());
        }
    }

    /**
     * Cyclically rotate the nodes the number of steps to the right.
     * @param steps Number of steps to rotate the nodes.
     */
    public void rotateRight(int steps) {
        for(int i = 0; i < steps; i++) {
            this.HEAD.linkSuccessor(this.HEAD.getPrevious().unlink());
        }
    }


    public boolean contains(E o) {
        this.HEAD.setKey(o); // Place the sentinel key in the header node.
        DLNode<E> curr = HEAD;
        do {
            curr = curr.getNext();
        } while(!curr.getKey().equals(o));
        return curr != HEAD;
    }

    /**
     * Remove all nodes for whose keys the given predicate is true.
     * @param predicate The predicate used to determine whether to remove a node.
     */
    public void removeIf(Predicate<E> predicate) {
        DLNode<E> curr = HEAD.getNext();
        while(curr != HEAD) {
            if(predicate.test(curr.getKey())) {
                curr.unlink();
            }
            curr = curr.getNext();
        }
    }

    /**
     * Assuming that the two parameter lists are sorted, merge their nodes into a new sorted
     * list. Both original parameter lists become empty after this operation.
     * @param first The first list to be merged.
     * @param second The second list to be merged.
     * @return A new list with the nodes of original lists in sorted order.
     * @param <E> Type of the keys stored in the lists to be merged.
     */
    public static <E extends Comparable<E>> DLList<E> merge(DLList<E> first, DLList<E> second) {
        DLList<E> result = new DLList<>();
        while(first.HEAD.getNext() != first.HEAD && second.HEAD.getNext() != second.HEAD) {
            E key1 = first.HEAD.getNext().getKey();
            E key2 = second.HEAD.getNext().getKey();
            result.HEAD.linkPredecessor(key1.compareTo(key2) <= 0 ? first.removeFront() : second.removeFront());
        }
        while(first.HEAD.getNext() != first.HEAD) {
            result.HEAD.linkPredecessor(first.removeFront());
        }
        while(second.HEAD.getNext() != second.HEAD) {
            result.HEAD.linkPredecessor(second.removeFront());
        }
        return result;
    }
}
