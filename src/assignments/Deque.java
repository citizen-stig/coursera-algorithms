import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Node head = null;
    private Node tail = null;
    private int size = 0;

    // construct an empty deque
    public Deque() {

    }

    public boolean isEmpty() {
        return head == null;
    }

    public int size() {
        return size;
    }

    public void addFirst(Item item) {
        if (head == null) {
            head = tail = new Node(item);
        } else {
            head = new Node(item, head);
        }
        size++;
    }

    public void addLast(Item item) {
        if (tail == null) {
            head = tail = new Node(item);
        } else {
            tail = new Node(tail, item);
        }
        size++;
    }

    public Item removeFirst() {
        if (head == null) {
            throw new NoSuchElementException();
        }
        Node oldHead = head;
        head = oldHead.next;
        if (head == null) {
            tail = null;
        } else {
            head.previous = null;
        }
        size--;
        return oldHead.item;
    }

    public Item removeLast() {
        if (tail == null) {
            throw new NoSuchElementException();
        }
        Node oldTail = tail;
        tail = oldTail.previous;
        if (tail == null) {
            head = null;
        } else {
            tail.next = null;
        }
        size--;
        return oldTail.item;
    }

    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private void print() {
        for (Item item: this) {
            System.out.print(String.format("'%s' ", item));
        }
        System.out.println();
    }


    // unit testing (required)
    public static void main(String[] args) {
        Deque<String> d = new Deque<String>();
        System.out.println(d.isEmpty());
        d.addFirst("First");
        d.addFirst("Pre First");
        d.addFirst("Another Pre First");
        d.addLast("Last");
        d.addLast("Add Another Last");
        System.out.println("----");
        d.print();
        System.out.println("----");
        System.out.println(d.removeFirst());
        d.print();
        System.out.println("----");
        System.out.println(d.removeLast());
        d.print();
    }

    private class Node {
        Item item;
        Node next;
        Node previous;

        public Node(Item item) {
            if (item == null) {
                throw new IllegalArgumentException("item cannot be null");
            }
            this.item = item;
        }

        public Node(Item item, Node next) {
            this.item = item;
            this.next = next;
            next.previous = this;
        }

        public Node(Node previous, Item item) {
            this.item = item;
            this.previous = previous;
            previous.next = this;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "item=" + item +
                    '}';
        }
    }

    private class ListIterator implements Iterator<Item> {
        private Node next;

        public ListIterator() {
            this.next = head;
        }

        @Override
        public boolean hasNext() {
            return next != null;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        @Override
        public Item next() {
            if (next == null) {
                throw new NoSuchElementException();
            }
            Node next = this.next;
            this.next = next.next;
            return next.item;
        }
    }

}