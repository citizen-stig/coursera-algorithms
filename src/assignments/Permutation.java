import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
    public static void main(String[] args) {
        if (args.length != 1) {
            throw new IllegalArgumentException("Please provide only 1 argument");
        }
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> queue = new RandomizedQueue<>();
        while (!StdIn.isEmpty()) {
            String value = StdIn.readString();
            queue.enqueue(value);
        }
        for (int i = 0; i < k; i++) {
            StdOut.println(queue.dequeue());
        }
    }
}
