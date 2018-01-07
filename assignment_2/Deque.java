import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 *
 * @author svoit
 */
public class Deque<Item> implements Iterable<Item> {

    private Node first;
    private Node last;
    private int size;

    /**
     * construct an empty deque
     */
    public Deque() {
        first = null;
        last = null;
        size = 0;
    }

    /**
     * is the deque empty?
     *
     * @return true or false
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * return the number of items on the deque
     *
     * @return size of the deque
     */
    public int size() {
        return size;
    }

    /**
     * add the item to the front
     *
     * @param item which to be added to the front
     * @throws IllegalArgumentException if item is null
     */
    public void addFirst(Item item) {
        if (null == item) throw new IllegalArgumentException("Can't add null item to the deque!");

        Node oldFirst = first;
        first = new Node();
        first.item = item;
        first.prev = null;
        first.next = oldFirst;

        if (isEmpty()) {
            last = first;
        } else {
            oldFirst.prev = first;
        }

        size++;
    }

    /**
     * add the item to the end
     *
     * @param item which to be added to the end
     * @throws IllegalArgumentException if item is null
     */
    public void addLast(Item item) {
        if (null == item) throw new IllegalArgumentException("Can't add null item to the deque!");

        Node newLast = new Node();
        newLast.item = item;
        newLast.next = null;
        newLast.prev = last;

        if (isEmpty()) {
            first = newLast;
        } else {
            last.next = newLast;
        }

        last = newLast;
        size++;
    }

    /**
     * remove and return the item from the front
     *
     * @return item which to be removed from the front
     * @throws NoSuchElementException if the deque is empty
     */
    public Item removeFirst() {
        if (isEmpty()) throw new NoSuchElementException("The deque is empty!");

        Node oldFirst = first;
        Item item = oldFirst.item;

        first = oldFirst.next;
        size--;

        if (isEmpty()) {
            first = null;
            last = null;
        } else {
            first.prev = null;
        }

        return item;
    }

    /**
     * remove and return the item from the end
     *
     * @return item which to be removed from the end
     * @throws NoSuchElementException if the deque is empty
     */
    public Item removeLast() {
        if (isEmpty()) throw new NoSuchElementException("The deque is empty!");

        Node oldLast = last;
        Item item = oldLast.item;

        last = oldLast.prev;
        size--;

        if (isEmpty()) {
            first = null;
            last = null;
        } else {
            last.next = null;
        }

        return item;
    }

    /**
     * return an iterator over items in order from front to end
     *
     * @return Iterator
     */
    @Override
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {
        private Node current = first;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException("There is no more items!");

            Item item = current.item;
            current = current.next;

            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Remove operator is unsupported!");
        }
    }

    private class Node {
        private Node next;
        private Node prev;
        private Item item;
    }

    /**
     * unit testing (optional)
     */
    public static void main(String[] args) {
        // unit testing (optional)
    }
}
