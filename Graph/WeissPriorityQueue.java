//TODO:
//  (1) Update this code to meet the style and JavaDoc requirements.
//			Why? So that you get experience with the code for a heap!
//			Also, this happens a lot in industry (updating old code
//			to meet your new standards). We've done this for you in
//			WeissCollection and WeissAbstractCollection.
//  (2) Implement getIndex() method and the related map integration
//			 -- see project description
//  (3) Implement update() method -- see project description

import java.util.*;

//You may use the JCF HashMap or the HashMap from Project 3
//that depends on your ThreeTenHashSet.

//To use the JCF class, uncomment this line:


//To use your code, just copy over HashMap and ThreeTenHashSet
//from Project 3 and DON'T uncomment the line above.


/**
 * PriorityQueue class implemented via the binary heap.
 * From your textbook (Weiss)
 * @param <AnyType> type to use with the weisspriorityqueue
 */
//The cs310 code checker is giving me an error for this class, saying that the AnyType isnot valid ebcause its not satisfying the required naming conventions
public class WeissPriorityQueue<AnyType> extends WeissAbstractCollection<AnyType>
{
	//you may not have any other class variables, only this one
	//if you make more class variables your priority queue class
	//will receive a 0, no matter how well it works
	/**
	 * default capacity of the weisspriorityqueue.
	 */
	private static final int DEFAULT_CAPACITY = 100;

	//you may not have any other instance variables, only these four
	//if you make more instance variables your priority queue class 
	//will receive a 0, no matter how well it works
	/**
	 * current size of the wiesspirorityqueue.
	 */
	private int currentSize;   // Number of elements in heap
	/**
	 * holds the elements of the weisspriorityqueue.
	 */
	private AnyType [ ] array; // The heap array
	/**
	 * comparator to use within the weissprirorityqueue.
	 */
	private Comparator<? super AnyType> cmp;
	/**
	 * the indexmap to hold the objects and their associated indexes from the array.
	 */
	private HashMap<AnyType, Integer> indexMap;

	//--------------------------------------------------------
	// testing code goes here... edit this as much as you want!
	//--------------------------------------------------------

	/**
	 * main method for weisspriorityqueue, used for testing method of class.
	 * @param args args to be fed into main method
	 */
	public static void main(String[] args) {
		/**
		 * student class to use with testing weisspriorityqueue.
		 */
		class Student {
			String gnum;
			String name;
			Student(String gnum, String name) { this.gnum = gnum; this.name = name; }

			/**
			 * see if this object is eqaul to one another.
			 * @param o object for this to be compared to
			 * @return if the object being compared is equal or not
			 */
			public boolean equals(Object o) {
				if(o instanceof Student) return this.gnum.equals(((Student)o).gnum);
				return false;
			}
			public String toString() { return name + "(" + gnum + ")"; }
			public int hashCode() { return gnum.hashCode(); }
		}
		
		Comparator<Student> comp = new Comparator<>() {
			public int compare(Student s1, Student s2) {
				return s1.name.compareTo(s2.name);
			}
		};
		
		
		//TESTS FOR INDEXING -- you'll need more testing...
		
		WeissPriorityQueue<Student> q = new WeissPriorityQueue<>(comp);
		q.add(new Student("G00000000", "Robert"));
		System.out.print(q.getIndex(new Student("G00000001", "Cindi")) + " "); //-1, no index
		System.out.print(q.getIndex(new Student("G00000000", "Robert")) + " "); //1, at root
		System.out.println();
		
		q.add(new Student("G00000001", "Cindi"));
		System.out.print(q.getIndex(new Student("G00000001", "Cindi")) + " "); //1, at root
		System.out.print(q.getIndex(new Student("G00000000", "Robert")) + " "); //2, lower down
		System.out.println();
		
		q.remove(); //remove Cindi
		System.out.print(q.getIndex(new Student("G00000001", "Cindi")) + " "); //-1, no index
		System.out.print(q.getIndex(new Student("G00000000", "Robert")) + " "); //1, at root
		System.out.println();
		System.out.println();
		
		
		//TESTS FOR UPDATING -- you'll need more testing...
		
		q = new WeissPriorityQueue<>(comp);
		q.add(new Student("G00000000", "Robert"));
		q.add(new Student("G00000001", "Cindi"));
		
		for(Student s : q) System.out.print(q.getIndex(s) + " "); //1 2
		System.out.println();
		for(Student s : q) System.out.print(s.name + " "); //Cindi Robert
		System.out.println();
		
		Student bobby = new Student("G00000000", "Bobby");
		q.update(bobby);
		
		for(Student s : q) System.out.print(q.getIndex(s) + " "); //1 2
		System.out.println();
		for(Student s : q) System.out.print(s.name + " ");  //Bobby Cindi
		System.out.println();
		
		bobby.name = "Robert";
		q.update(bobby);
		
		for(Student s : q) System.out.print(q.getIndex(s) + " "); //1 2
		System.out.println();
		for(Student s : q) System.out.print(s.name + " "); //Cindi Robert
		System.out.println();
		
		//you'll need more testing...
	}
	
	//you implement this

	/**
	 * populates hashmpa indexmap wiht entires of hashmap,
	 * keys are the objects held by the array and the vals are the correspdoning indexes within the array.
	 */
	private void populate()
	{

		indexMap = new HashMap<>();
		for(int i = 0 ; i<= currentSize;i++)
		{
			indexMap.put(array[i],i);
		}

	}
	/**
	 * finds index of item.
	 * @param x item to look for
	 * @return int index of item x
	 */
	public int getIndex(AnyType x) {
		//average case O(1)
		if(array[0].equals(x))
		{
			if(array[0]==array[1])
			{
				return 1;
			}

		}
		for (int i = 1; i <= currentSize;i++)
		{
			if(array[i].equals(x))
			{
				return i;
			}
		}
		return -1;
	}
	
	//you implement this

	/**
	 * updates a vlaues position iwhtin the array of this object named array.
	 * alaos update sibjects position wihtin the hashmap indexmap for this obj
	 * @param x the objects whose position will be getting updateed
	 * @return true if an update was preformed and false if ni update was prefroemd
	 */
	public boolean update(AnyType x) {
		if (x == null) {
			return false;
		}
		boolean updated = false;
		if (this.indexMap == null) {//populate the indexmap
			this.populate();

		}
		boolean added = false;
		for (int i = 0; i <= currentSize; i++) {
			if (array[i].equals(x)) {
				indexMap.remove(array[i]);
				array[i] = x;
				indexMap.put(x,i);
				added = true;
				break;
			}
		}
		if (added = false) {
			this.add(x);
			indexMap.put(x, currentSize);
		}

		//___________________________works ^^^
		//check if x has children
		int index = this.getIndex(x);
		if (cmp.compare(array[index / 2], array[index]) > 0) {
			AnyType tmp = array[index / 2];
			array[index / 2] = array[index];
			array[index] = tmp;
			updated = true;
		}
		if(array[index*2]!=null)
		{
			if (cmp.compare(array[index], array[index * 2]) > 0) {
				AnyType tmp = array[index * 2];
				array[index * 2] = array[index];
				array[index] = tmp;
				updated = true;
			}
		}
		if(array[index*2+1]!=null)
		{
			if (cmp.compare(array[index], array[index * 2 + 1]) > 0) {
				AnyType tmp = array[index];
				array[index] = array[index * 2];
				array[index * 2] = tmp;
				updated = true;
			}

		}

		//if in order than return false because no update preformed
		//O(lg n) average case
		//or O(lg n) worst case if getIndex() is guarenteed O(1)

		return updated;
	}
	
	/**
	 * Construct an empty PriorityQueue.
	 */
	@SuppressWarnings("unchecked")
	public WeissPriorityQueue( )
	{
		currentSize = 0;
		cmp = null;
		array = (AnyType[]) new Object[ DEFAULT_CAPACITY + 1 ];
	}
	
	/**
	 * Construct an empty PriorityQueue with a specified comparator.
	 * @param c comparator for weisspriorityqueue to be based off of
	 */
	@SuppressWarnings("unchecked")
	public WeissPriorityQueue( Comparator<? super AnyType> c )
	{
		currentSize = 0;
		cmp = c;
		array = (AnyType[]) new Object[ DEFAULT_CAPACITY + 1 ];
	}
	
	 
	/**
	 * Construct a PriorityQueue from another Collection.
	 * @param coll collection to extend from
	 */
	@SuppressWarnings("unchecked")
	public WeissPriorityQueue( WeissCollection<? extends AnyType> coll )
	{
		cmp = null;
		currentSize = coll.size( );
		array = (AnyType[]) new Object[ ( currentSize + 2 ) * 11 / 10 ];
		
		int i = 1;
		for( AnyType item : coll )
			array[ i++ ] = item;
		buildHeap( );
	}
	
	/**
	 * Compares lhs and rhs using comparator if
	 * provided by cmp, or the default comparator.
	 * @param lhs first object to compare with rhs
	 * @param rhs second object to compare to lhs
	 * @return the result of the comparison between lhs and rhs
	 */
	@SuppressWarnings("unchecked")
	private int compare( AnyType lhs, AnyType rhs )
	{
		if( cmp == null )
			return ((Comparable)lhs).compareTo( rhs );
		else
			return cmp.compare( lhs, rhs );	
	}
	
	/**
	 * Adds an item to this PriorityQueue.
	 * @param x any object.
	 * @return true.
	 */
	public boolean add( AnyType x )
	{
		if( currentSize + 1 == array.length )
			doubleArray( );

		// Percolate up
		int hole = ++currentSize;
		array[ 0 ] = x;
		
		for( ; compare( x, array[ hole / 2 ] ) < 0; hole /= 2 ) {
			array[ hole ] = array[ hole / 2 ];
		}
		
		array[ hole ] = x;
		
		return true;
	}
	
	/**
	 * Returns the number of items in this PriorityQueue.
	 * @return the number of items in this PriorityQueue.
	 */
	public int size( )
	{
		return currentSize;
	}
	
	/**
	 * Make this PriorityQueue empty.
	 */
	public void clear( )
	{
		currentSize = 0;
	}
	
	/**
	 * Returns an iterator over the elements in this PriorityQueue.
	 * The iterator does not view the elements in any particular order.
	 * @return an iterator to go through the weisspriorityqueue
	 */
	public Iterator<AnyType> iterator( )
	{
		return new Iterator<AnyType>( )
		{
			int current = 0;
			
			public boolean hasNext( )
			{
				return current != size( );
			}
			
			@SuppressWarnings("unchecked")
			public AnyType next( )
			{
				if( hasNext( ) )
					return array[ ++current ];
				else
					throw new NoSuchElementException( );
			}
			
			public void remove( )
			{
				throw new UnsupportedOperationException( );
			}
		};
	}
	 
	/**
	 * Returns the smallest item in the priority queue.
	 * @return the smallest item.
	 * @throws NoSuchElementException if empty.
	 */
	public AnyType element( )
	{
		if( isEmpty( ) )
			throw new NoSuchElementException( );
		return array[ 1 ];
	}
	
	/**
	 * Removes the smallest item in the priority queue.
	 * @return the smallest item.
	 * @throws NoSuchElementException if empty.
	 */
	public AnyType remove( )
	{
		AnyType minItem = element( );
		array[ 1 ] = array[ currentSize-- ];
		percolateDown( 1 );

		return minItem;
	}


	/**
	 * Establish heap order property from an arbitrary
	 * arrangement of items. Runs in linear time.
	 */
	private void buildHeap( )
	{
		for( int i = currentSize / 2; i > 0; i-- )
			percolateDown( i );
	}

	/**
	 * Internal method to percolate down in the heap.
	 * @param hole the index at which the percolate begins.
	 */
	private void percolateDown( int hole )
	{
		int child;
		AnyType tmp = array[ hole ];

		for( ; hole * 2 <= currentSize; hole = child )
		{
			child = hole * 2;
			if( child != currentSize &&
					compare( array[ child + 1 ], array[ child ] ) < 0 )
				child++;
			if( compare( array[ child ], tmp ) < 0 ) {
				array[ hole ] = array[ child ];
			}
			else
				break;
		}
		array[ hole ] = tmp;
	}
	
	/**
	 * Internal method to extend array.
	 */
	@SuppressWarnings("unchecked")
	private void doubleArray( )
	{
		AnyType [ ] newArray;

		newArray = (AnyType []) new Object[ array.length * 2 ];
		for( int i = 0; i < array.length; i++ )
			newArray[ i ] = array[ i ];
		array = newArray;
	}
}
