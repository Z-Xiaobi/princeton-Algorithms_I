import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
   public static void main(String[] args) {
       int  k =  Integer.parseInt(args[0]);
       String item = null;
       RandomizedQueue<String> rqs = new RandomizedQueue<String>();
       int count = 0; // count input
       

       // windows use ctrl+z to end input
       // mac use ctrl+d to end input (not command+d)
       while (!StdIn.isEmpty()) {
           item = StdIn.readString();
           rqs.enqueue(item);
           count++;
       }
       int numOfItems = count > k ? k:count;

       for (int i = 0; i < numOfItems; i++) {
           StdOut.println(rqs.dequeue());
       }
       
   }
}