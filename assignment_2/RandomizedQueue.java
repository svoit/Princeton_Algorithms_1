import edu.princeton.cs.algs4.StdRandom;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 *
 * @author svoit
 */
public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] items;
    private int size;

    /**
     * construct an empty randomized queue
     */
    public RandomizedQueue() {
        this.items = (Item[]) new Object[1];
        this.size = 0;
    }

    /**
     * is the queue empty?
     *
     * @return true or false
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * return the number of items on the randomized queue
     *
     * @return size of the randomized deque
     */
    public int size() {
        return size;
    }

    /**
     * add the item
     *
     * @param item
     * @throws IllegalArgumentException if enqueue null value
     */
    public void enqueue(Item item) {
        if (null == item) throw new IllegalArgumentException("Can't enqueue null value!");

        if (size == items.length) {
            resize(items.length * 2);
        }

        items[size++] = item;
    }

    /**
     * resize the queue
     *
     * @param capacity
     */
    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];

        for (int i = 0; i < size; i++) {
            copy[i] = items[i];
        }

        items = copy;
    }

    /**
     * remove and return a random item
     *
     * @return random item
     * @throws NoSuchElementException if the deque is empty
     */
    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException("The queue is empty!");

        if (size == items.length / 4) {
            resize(items.length / 2);
        }

        int random = StdRandom.uniform(size);
        Item item = items[random];
        items[random] = items[--size];
        items[size] = null;

        return item;
    }

    /**
     * return (but do not remove) a random item
     *
     * @return random item
     * @throws NoSuchElementException if the deque is empty
     */
    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException("The queue is empty!");

        return items[StdRandom.uniform(size)];
    }

    /**
     * return an independent iterator over items in random order
     *
     * @return Iterator
     */
    @Override
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        private final int[] random;
        private int current;

        public RandomizedQueueIterator() {
            this.random = new int[size];

            for (int i = 0; i < size; i++) {
                random[i] = i;
            }
            StdRandom.shuffle(random);
            current = 0;
        }

        @Override
        public boolean hasNext() {
            return current != random.length;
        }

        @Override
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException("There is no more items!");

            return items[random[current++]];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Remove operator is unsupported!");
        }
    }

    /**
     * unit testing (optional)
     */
    public static void main(String[] args) {
        // unit testing (optional)
    }
}
