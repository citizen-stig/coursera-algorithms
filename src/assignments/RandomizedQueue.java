import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] storage;
    private int currentIndex = 0;

    public RandomizedQueue() {
        storage = (Item[]) new Object[2];
    }

    public boolean isEmpty() {
        return currentIndex == 0;
    }

    public int size() {
        return currentIndex;
    }

    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        if (currentIndex == storage.length) {
            resize(storage.length * 2);
        }
        storage[currentIndex++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        int randomIndex = getRandomIndex();
        Item randomItem = storage[randomIndex];

        currentIndex--;
        if (randomIndex != currentIndex) {
            storage[randomIndex] = storage[currentIndex];
        }
        storage[currentIndex] = null;
        if (currentIndex > 0 && currentIndex == storage.length / 4) resize(storage.length / 2);
        return randomItem;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return storage[getRandomIndex()];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedIterator();
    }

    private void resize(int newSize) {
        Item[] newStorage = (Item[]) new Object[newSize];
        for (int i = 0; i < newSize && i < storage.length; i++) {
            newStorage[i] = storage[i];
        }
        storage = newStorage;
    }

    private int getRandomIndex() {
        return StdRandom.uniform(currentIndex);
    }


    private class RandomizedIterator implements Iterator<Item> {
        int iteratorIndex = 0;
        int[] indexes;

        public RandomizedIterator() {
            indexes = new int[currentIndex];
            for (int i = 0; i < currentIndex; i++) {
                indexes[i] = i;
            }
            StdRandom.shuffle(indexes);
        }

        @Override
        public boolean hasNext() {
            return iteratorIndex < indexes.length;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            int nextIndex = indexes[iteratorIndex++];
            return storage[nextIndex];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }


    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<String> q = new RandomizedQueue<>();
        System.out.println(q.size());
        q.enqueue("Hi");
        System.out.println(q.size());
        System.out.println(q.sample());
        System.out.println(q.dequeue());
        q.enqueue("Foo");
        q.enqueue("Baar");
        q.enqueue("baaz");
        q.enqueue("Nikolai");
        System.out.println(q.size());
        for (String item : q) {
            System.out.println(item);
        }
        // System.out.println(q.dequeue());
        // System.out.println(q.dequeue());
        // System.out.println(q.dequeue());
        // System.out.println(q.dequeue());
    }

}