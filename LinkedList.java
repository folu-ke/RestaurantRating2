package project3;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;


/**
 * The LinkedList<E> class implements the Java’s Collection<E> interface
 * and thus the Iterable<E> ineterface. The class would be used to store the 
 * restaurants read from the CSV file. The class is also generic i.e. it can hold 
 * others objects of a different type. It is implemented as a singly linked list
 * {one way traversal}. 
 * @author  Mofoluwake Adesanya
 * @version 11/7/2023
 */
public class LinkedList<E> implements Collection<E>, Iterable<E>{
	// first element in this collection
	private Node<E> head;
	// size of the list 
	private int size = 0;
	
	/**
	 * Returns the index of the first occurrence of the specified element in this list,
	 * or -1 if this list does not contain the element. More formally, returns the lowest 
	 * index i such that (o==null ? get(i)==null : o.equals(get(i))), or -1 if there is 
	 * no such index.
	 * @param o
	 * @return int index of the first occurence of the specified element in this list 
	 * 		   or -1 if the element is not in list.
	 */
	public int indexOf (Object o) {
		int index = 0;
		// if Object o is null
		// would this be used for lists that have a null object? //
		if ( o.equals(null) ) {
			// assuming that this collection would not add null objects
			return -1;
		}
		else {
			Node<E> pointer = head;
			// check till the end of the list
			while ( pointer.next != null ) {
				if ( pointer.data.equals(o) ) {
					// once the object is found, return its index
					return index;
				}
				// keep track of the index
				pointer = pointer.next;
				index++;
			}
			if ( pointer.equals(o) ) {
					return index;
			}
			else { return -1; }
		}
	}
	/**
	 * Returns the element at the specified position in this list. This does not use indexOf() 
	 * because the worst case runtime would be polynomial i.e. indexOf() is linear.
	 * @param int index of the element to return
	 * @return element at the specified position in this list
	 * @throws IndexOutOfBoundsException if the index is out of range [0, this.size() - 1]
	 */
	public E get (int index) throws IndexOutOfBoundsException {
		// throw exception if list is empty or index >= size
		if ( this.isEmpty() || index >= this.size ) 
		{	throw new IndexOutOfBoundsException();	}
		else {
			Node<E> pointer = head;
			int track = 0; //to track the index of nodes
			while ( pointer != null ) {
				if (track == index ) 
					return pointer.data;
				pointer = pointer.next;
				// increments track until track is the index of element to be found
				track++;
			}
		}
		return null;
	}
	/**
	 * Returns the string representation of this collection: a list of the collection's elements 
	 * in the order returned by the iterator, enclosed in "[]". Elements are separated by a comma 
	 * and a space (", "). Elements are converted to strings as by String.valueOf(Object).
	 * @return string representation of this collection.
	 */
	@Override
	public String toString() {
		String list = "[";
		// [] is an empty string
		if ( this.isEmpty() ) { return "[]"; }
		// if the list is not empty
		else {
			Node<E> pointer = head;
			while ( pointer.next != null ) {
				list += pointer.data + ", ";
				pointer = pointer.next;
			}
			// add the tail to the string
			list += pointer.data + "]";
		}
		// return string 
		return list;
	}	
	/**
	 * Sorts the specified list into ascending order, according to the natural ordering of its 
	 * elements. All elements in the list must implement the Comparable interface. Converts the list 
	 * to an array, sorts the array and then converts it back to a list.
	 */
	public void sort ( ) {
		// convert this collection to an array
		Object [] array = toArray();
		// sort using Array object built in sort function
		Arrays.sort(array); 
		// delete the contents of this list 
		this.clear();
		// then add the contents again but sorted now
		for (Object o : array ) {
			this.add( (E)o );
		}
	}	
	/**
	 * Ensures that this collection contains the specified element.
	 * @param e the object to add the collection. It must be the same type as
	 * the specified E.
	 * @return true if add operation worked. 
	 *         false if an exception is thrown.
	 */
	@Override
	public boolean add(E e) {
		// create a node
		Node<E> newNode = new Node<E>(e);
		// check if collection is empty first 
		if ( this.isEmpty() ) {
			// set e as the head node
			this.head = newNode;
			// increase size
			size++;
			// confirm that e has been added
			return true;
		}
		// if list is not empty
		else {
			// start at the head node
			Node<E> pointer = head;
			// slide pointer to the end of the collection
			while  ( pointer.next != null ) {
				pointer = pointer.next;
			}
			// when last element is found, it would point to the newNode.
			pointer.next = newNode;
			// increase size
			size++;
			return true;
		}
		// can add return false? Returns false if this collection does not permit duplicates 
		// and already contains the specified element.
		// should this collection allow duplicates?  
		// return false;
	}
	/**
	 * Removes all of the elements from this collection. Empties the collection.
	 */
	@Override
	public void clear() {
		// would update when iter is implemented
		while ( head.next != null ) {
			// head would soon become the only element in the list
			head.next = head.next.next;
		}
		// set head to null to completely clear the list
		head = null; // this.remove(head);
		this.size = 0;  // set size to 0
		
	}
	/**
	 * @param Object o to check for this collection.
	 * @return true if this collection contains the specified element.
	 *         false otherwise.
	 */
	@Override
	public boolean contains(Object o) {
		// check if list is empty
		if ( this.isEmpty() ) { return false; }
		// else
		Node<E> pointer = head;
		// check each node
		while ( pointer.next != null ) {
			if ( pointer.data.equals(o) ) 
			{	return true;	}
			pointer = pointer.next;
		}
		// if not found, return false
		return false;
	}
	/**
	 * If the size of this list is less than the size of c, this list cannot contain all of c's elements.
	 * @return true if this collection contains all of the elements in the specified collection. 
	 *         false otherwise.
	 */
	@Override
	public boolean containsAll(Collection<?> c) {
		// check to see if c is equal to this list 
		if ( this.equals(c) ) 
		{	return true;	}
		// if the size of this list is less than the size of c,
		// this list cannot contain all of c's elements.
		if ( this.size() < c.size() ) {
			return false;
		}
		// check each element of c 
		Object [] cArr = c.toArray();
		int checker = 0;
		// iterate over c to check if each element in this list
		for ( int i = 0; i < c.size(); i++ ) {
			if ( this.indexOf(cArr[i]) != -1 ) 
			{	checker++;	}
			else {	return false;	}
		}
		// return true if all elements in c are in this list
		if ( checker == c.size() ) 
			return true;
		return false;
	}
	/**
	 * Compares the specified object with this collection for equality. 
	 * @param Object o to compare
	 */
	@Override
	public boolean equals (Object that) {
		// if this is equal to that, return true immediately
		if ( this == that ) {	return true;	}
		// if this is not null and that is null
		if ( that == null ) {	return false;	}
		// if that is a TestLinkedList
		if ( !(that instanceof TestLinkedList) )
			return false;
		// now check if each node in this is equal to each node in that 
		// and if there are in the same order
		LinkedList<E> object = (LinkedList<E>) that;
		int checker = 0;	// would be used for returned boolean
		if ( this.size() == object.size() ) {
			for ( int i  = 0; i < size; i++ ) {
				if ( this.get(i).equals(object.get(i)) ) {
					checker++;
				}
			}
			// if checker == size, we know that each node is equal and in the same order
			if ( checker == size ) {	return true;	}
		}
		return false;
	}
	/**
	 * Must be consistent with equals().
	 * @return the hash code value for this collection.
	 */
	public int hashCode() {
		// TODO Auto-generated method stub
		return 0;
	}
	/**
	 * This collection is empty if the first element (Node head) is null.
	 * @return true if this collection contains no elements
	 */
	@Override
	public boolean isEmpty() {
		// if head is null, the collection is empty.
		if ( head == null ) 
			return true;
		// otherwise the collection is not empty.
		return false;
	}
	/**
	 * An iterator over this collection.
	 * @return Iterator<E> over the elements in this collection.
	 */
	@Override
	public Iterator<E> iterator() {
		// return an instance of the LinkedList.Iter
		return new Iter<E>();
	}
	/**
	 * Removes a single instance of the specified element from this collection, if it is present.
	 * The instance can be removed in two cases, either at the head of the list or the middle or the 
	 * tail of the list.
	 */
	@Override
	public boolean remove(Object o) {
		// if this list is empty or if the object is not in this list
		if ( this.indexOf(o) == -1 || this.isEmpty() ) {
			return false;			
		}
		// otherwise, deal with two cases
		else {
			// case 1: Object o is the head node of the list
			if ( this.head.data.equals(o) ) {
				this.head = head.next;
				// decrement size
				size--;
				return true;
			}
			// case 2: Object o is in the middle or tail of list
			Node<E> pointer = head, predecessor = null;
			// keep track of the predecessor for when the key is found
			while ( pointer.next != null && !( pointer.data.equals(o) ) ) {
				predecessor = pointer;    pointer = pointer.next;
			}
			// the predecessor now points to the node after Object o
			// so Object o is not tracked and is no longer part of the list
			predecessor.next = pointer.next;
			// decrement size
			size--;
			return true;	
		}
	}
	/**
	 * The size of this collection is modified in the {@link add()} 
	 * and {@link remove()} functions. 
	 * @return the number of elements in this collection.
	 */
	@Override
	public int size() {
		return size;
	}
	/**
	 * @return an array containing all of the elements in this collection.
	 */
	@Override
	public Object[] toArray() {
		Object[] array = (Object[]) new Object[size];
		// iterate over list and add data of each node
		Node<E> pointer = head;
		int i = 0;
		while ( pointer != null ) {
			array[i] = pointer.data;
			pointer = pointer.next;
			i++;
		}
		return array;
	}
	/**
	 * @return an array containing all of the elements in this collection. 
	 * The runtime type of the returned array is that of the specified array. 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> T[] toArray(T[] a) {
		// 
		a = (T []) new Object[size];
		// iterate over list and add data of each node
		Node<E> pointer = head;
		int i = 0;
		while ( pointer != null ) {
			a[i] = (T) pointer.data;
			pointer = pointer.next;
			i++;
		}
		return a;
	}
		
		
	/**
	 * The Node class is the basis of the linked list. Each node holds the data of that node and a reference 
	 * to the next element in the list. Its type is generic which means it can be used for any type of element.
	 */
	private static class Node <E> {
		// Node data
		E data;
		// reference to the next node beside it
		Node <E> next;
		/**
		 * Constructs a Node instance with the data and a reference to the next Node.
		 * @param e the Node data
		 */
		Node (E e) {
			data = e;
			next = null;
		}
	}
	
	/**
	 * The Iter<E> class is responsible for iterating over this collection. It implements
	 * the Iterator<E> and has the {@link next()} and {@link hasNext()} methods. The remove()  method is
	 * not implemented because its functionality are not needed.
	 */
	private class Iter<E> implements Iterator<E> {
		Node<E> pointer = null;
		/**
		 * @return true if the iteration has more elements. In other words, 
		 * returns true if {@link next()} would return an element rather than throwing
		 * an exception.
		 */
		@Override
		public boolean hasNext() {
			// pointer is null when iterator is first instantiated
			if ( pointer == null && head != null ) 
			{	return true;	}
			// if head has been reached, pointer is not null 
			else if ( pointer != null ) 
			{	return pointer.next != null;	}
			//  false if end of collection is reached
			return false;
		}

		/**
		 * Returns the next element in the iteration. 
		 * @return the next Object element in the iteration.
		 * @throws NoSuchElementException if the iteration has no more elements.
		 */
		@Override
		public E next() throws NoSuchElementException {
			// at the start of the list, the pointer is null
			if ( pointer == null && head != null ) {
				pointer = (Node<E>) head;
				// return head
				return (E) head.data;
			}
			// 
			else if ( pointer != null ) {
				// return the next element every time 
				pointer = pointer.next;
				return pointer.data;
			}
			// otherwise -> if end of collection is reached
			throw new NoSuchElementException();
		}
	}
	
	//////////////////////////////////////////////////////////////////
	
	// The implementation of these functions are not necessary.
	@Override
	public boolean addAll(Collection<? extends E> c) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean removeAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean retainAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}

}
