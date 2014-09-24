import java.util.Iterator;
import java.lang.NullPointerException;
import java.lang.UnsupportedOperationException;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
	private int STARTING_ARRAY_SIZE=10;
	private boolean isEmpty;
	private int frontIdx;
	private int nextIdx;
	Item[] data;
	   public Deque()   { 
		   frontIdx = 0; 
		   nextIdx = 0; 
		   isEmpty = true; 
		   data = (Item[]) new Object[STARTING_ARRAY_SIZE];
	   }                        // construct an empty deque
	   public boolean isEmpty(){return isEmpty;}                 // is the deque empty?
	   public int size() {
		   if ( !isEmpty ) {
			   if ( nextIdx < frontIdx )
				   return data.length + nextIdx - frontIdx;
			   else
				   return nextIdx - frontIdx;
		   }
		   return 0;
	   }                       // return the number of items on the deque
	   private void insert( Item item, int idx ) {
		   if ( null == item ) {
			   throw new java.lang.NullPointerException();
		   }
		   data[idx] = item;
		   isEmpty = false;
	   }
	   public void addFirst(Item item){
		   if ( frontIdx > 0 )
			   --frontIdx;
		   else
			   frontIdx = data.length - 1;
		   insert( item, frontIdx );
	   }          // insert the item at the front
	   public void addLast(Item item) {
		   insert( item, nextIdx );
		   if ( nextIdx < (data.length - 1) )
			   ++nextIdx;
		   else
			   nextIdx = 0;
	   }          // insert the item at the end
	   public Item removeFirst() {
		   throw new java.lang.UnsupportedOperationException();
		   }               // delete and return the item at the front
	   
	   public Item removeLast() {
		   throw new java.lang.UnsupportedOperationException();}                // delete and return the item at the end
	   
	   private class DequeIterator implements Iterator<Item> {
		   private int currIdx;
		   DequeIterator() { currIdx = frontIdx; }
		   public boolean hasNext() { return !isEmpty() && currIdx != nextIdx; }
		   public void remove() {throw new java.lang.UnsupportedOperationException();}
		   public Item next() { 
			   if ( hasNext() ) { 
				   int idxToUse = currIdx;
			   	   if ( ++currIdx == data.length )
			   		   currIdx = 0;
				   return data[idxToUse];
			   }
			   throw new java.util.NoSuchElementException();
		   };
	   
	   };
	   
	   public Iterator<Item> iterator() { return new DequeIterator(); }        // return an iterator over items in order from front to end
	   public static void main(String[] args) {  // unit testing
	   
	   }
	}

