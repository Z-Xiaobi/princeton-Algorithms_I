import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import java.util.Iterator;

/**
 * A generic data type for a deque and a randomized queue.
 * */

public class RandomizedQueue<Item> implements Iterable<Item> {
    
    private Item rdqueue[];      // array of randomized queue
    private int n;               // number of elements on randomized queue
    
    
    // construct an empty randomized queue
//    @SuppressWarnings("unchecked")
    public RandomizedQueue() {
        int capacity = 50;
        rdqueue = (Item[]) new Object[capacity];
        n = 0;
    }
    
    // is the randomized queue empty?
    public boolean isEmpty() { return n == 0;}

    // return the number of items on the randomized queue
    public int size() { return n; }

    // add the item
    public void enqueue(Item item) {
        if (item ==  null) {
            throw new IllegalArgumentException();
        }
        // resize array
        // enlarge array size
        if (rdqueue.length == n) {
            int newLen = rdqueue.length * 2;
//            @SuppressWarnings("unchecked")
            Item[] temp = (Item[]) new Object[newLen];
            for (int i = 0; i < n;i++) {
                temp[i] = rdqueue[i];
                rdqueue[i] = null;
            }
            rdqueue = temp;            
        }
        // store item
        rdqueue[n] = item;
        n++;
    }

    // remove and return a random item
    // random on the same input should not be the same
    public Item dequeue() {
        if (n == 0) throw new NoSuchElementException("randomized queue underflow");
        Item item = null;
        StdRandom.shuffle(rdqueue, 0, n);
        item = rdqueue[n-1];
        rdqueue[n-1] = null;
        n--;
        return item;
    }

    // return a random item (but do not remove it)
    // cannot return null element
    public Item sample() {
        if (n == 0) throw new NoSuchElementException("randomized queue underflow");
        StdRandom.shuffle(rdqueue, 0, n);
        int rdIndex = StdRandom.uniform(n);
        Item item = rdqueue[rdIndex];
        return item;
    }
    /**
     * iterator
     * */

    private class ReverseArrayIterator implements Iterator<Item>
    {
        private int i = n; // last index

        public boolean hasNext() {  return i > 0;        }
        public void remove()     {
            /* not supported */
            throw new UnsupportedOperationException("remove() is not supported");
        }
        public Item next()       {
            if (!hasNext()) throw new NoSuchElementException("no next node");
            return rdqueue[--i]; 
        }
    }
    // return an independent iterator over items in random order
    public Iterator<Item> iterator(){
        return new ReverseArrayIterator();
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();
        
        // Test enque
        // Test size()
        for (int i=0; i<5; i++) {
            rq.enqueue(i);
            StdOut.println("Size:");
            StdOut.println(rq.size());
        }
        
        // Test iterator
        Iterator<Integer> it = rq.iterator();
        while (it.hasNext()) {
            StdOut.print(it.next() + " ");    
        }
        
        StdOut.println("\n---------------------------");
        for (int a : rq) {
            for (int b : rq)
                StdOut.print(a + "-" + b + " ");
            StdOut.println();
        }
        // Test sample
        StdOut.print(rq.sample());
        
        StdOut.println("\n---------------------------");
        // Test deque
        while (!(rq.isEmpty())) {
            StdOut.println(rq.dequeue());
        }
        
        
    }

}