import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdOut;
import java.util.Iterator;


/**
 * A generic data type for a deque
 * */


public class Deque<Item> implements Iterable<Item> {

    // construct an empty deque
    // A deque containing n items must use at most 48n + 192 bytes 
    // of memory
    
    private MyNode<Item> first;  // first node of degue
    private MyNode<Item> last;   // last node of deque
    private int n;               // number of elements on deque
    
    
    // use a inner class to define a specified node
    // refer to edu.princeton.cs.algs4.Queue
    // 16 object overhead +  8 inner class extra overhead
    private static class MyNode<Item> {
        private Item item;
        private MyNode<Item> next; // 8 for MyNode
        private MyNode<Item> pre; // 8 for MyNode
    }
    
    // constructor
    public Deque() {
//        first = last = null;    
        n = 0;
    }
    

    // is the deque empty?
    public boolean isEmpty() {
//        return first == null;
        return n == 0;
    }

    // return the number of items on the deque
    public int size() {
    	return n;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item ==  null) {
            throw new IllegalArgumentException();
        }
        
        if (!(n == 0)) {         
            MyNode<Item> oldfirst = first;
            first = new MyNode<Item>();
            first.next = oldfirst;
            oldfirst.pre = first;
        }else {
            last = first = new MyNode<Item>();
        }
        first.item = item;        
        n++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item ==  null) {
            throw new IllegalArgumentException();
        }
        
        if (!(n == 0)) {
            MyNode<Item> oldlast = last;
            last = new MyNode<Item>();
            last.pre = oldlast;
            oldlast.next = last;
        }else {
            first = last = new MyNode<Item>();
        }
        last.item = item;
        n++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("Deque underflow.");          
        }
        Item item = first.item;
        first = first.next; 
        
        n--;
        if (isEmpty()) {
            
            first = null;
            last = null; // to avoid loitering 
        }else {
            first.pre = null;
        }
        // avoid holding a reference to an object when it is no longer needed)
        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("deque underflow");          
        }     
        Item item = last.item;
        last = last.pre;
        
        n--;
        if (isEmpty()) {
            first = null;
            last = null; // to avoid loitering 
        }else {
            last.next = null;
        }
        // avoid holding a reference to an object when it is no longer needed)
        return item;
    }

    
    /** deque iterator 
     * @return an iterator that iterates over the items in this queue in FIFO order
     * */
    private class LinkedIterator implements Iterator<Item>
    {
        private MyNode<Item> current = first;

//        public LinkedIterator(MyNode<Item> first) {
//            current = first;
//        } //  lead to pointer loss, current is not point to current first
        public boolean hasNext() { return current != null;  }
        public void remove() {
            /* not supported */
            throw new UnsupportedOperationException("remove() is not supported");
        }
        public Item next()
        {
            if (!hasNext()) throw new NoSuchElementException("no next node");
            Item item = current.item;
            current = current.next;
            return item;
        } 
     }
    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new LinkedIterator();  
    }
    
    
    // unit testing (required)
    public static void main(String[] args) {
        Deque<Integer> ds =  new Deque<Integer>();
        
        
        // Test addFirst and addLast
        // Test size()
        for (int i = 0; i < 5; i++) {
            ds.addFirst(i);
            ds.addLast(i);
            StdOut.println("Size:");
            StdOut.println(ds.size());
        }
        
        //  Test iterator
        Iterator<Integer> it = ds.iterator();
        while (it.hasNext()) {
            StdOut.print(it.next() + " ");    
        }
        StdOut.println("\n---------------------------");
        
        for (int a : ds) {
            for (int b : ds)
                StdOut.print(a + "-" + b + " ");
            StdOut.println();
        }
        
        // Test removeFirst and removeLast
        while (!ds.isEmpty()) {
            StdOut.println("remove first ");
            StdOut.println(ds.removeFirst());
            StdOut.println("remove last ");
            StdOut.println( ds.removeLast());
        }
        
        
        /*
        StdOut.println("empy:" + ds.isEmpty());
        StdOut.println("addFirst(9)");
        ds.addFirst(9);
        StdOut.println("empy:" + ds.isEmpty());
        StdOut.println("addFirst(11)");
        ds.addFirst(11);
        Iterator<Integer> it = ds.iterator(); // after add then iterate is fine
        StdOut.println("has next: " + it.hasNext());
        while (it.hasNext()) {
            StdOut.print(it.next() + " ");    
        }
        StdOut.println(ds.removeLast());
        StdOut.println("has next: " + it.hasNext());
        while (it.hasNext()) {
            StdOut.print(it.next() + " ");    
        }
        */
        
    }

}