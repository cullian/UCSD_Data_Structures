package textgen;

import java.util.AbstractList;


/** A class that implements a doubly linked list
 * 
 * @author UC San Diego Intermediate Programming MOOC team
 *
 * @param <E> The type of the elements stored in the list
 */
public class MyLinkedList<E> extends AbstractList<E> {
	LLNode<E> head;
	LLNode<E> tail;
	int size;

	/** Create a new empty LinkedList */
	public MyLinkedList() {
		head = new LLNode<E>(null);
		tail = new LLNode<E>(null);
		size = 0;
		head.next = tail;
		tail.prev = head;
	}

	/**
	 * Appends an element to the end of the list
	 * @param element The element to add
	 * @throws NullPointerException if the element is null
	 */
	public boolean add(E element ) 
	{
		// check for null
		if (element == null) {
			throw new NullPointerException("MyLinkedList object cannot store null pointers.");
		}
		// create new node
		LLNode<E> newElement = new LLNode<E>(element, tail.prev, tail);
		// Link node in at end of list
		newElement.prev.next = newElement;
		tail.prev = newElement;
		// increase size
		size++;
		System.out.println(toString());
		return true;
	}

	/** Get the element at position index 
	 * @throws IndexOutOfBoundsException if the index is out of bounds. */
	public E get(int index) 
	{
		// validate input
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException("MyLinkedList Out of bounds exception.");
		}
		// pointer to current node
		LLNode<E> n = head.next;
		// iterate thru list
		for (int i = 0; i < index; i++) {
			n = n.next;
		}
		return n.data;
	}

	/**
	 * Add an element to the list at the specified index
	 * @param The index where the element should be added
	 * @param element The element to add
	 * @throws IndexOutOfBoundsException if the index is out of bounds.
	 * @throws NullPointerException if the element is null
	 */
	public void add(int index, E element ) 
	{
		// validate input
		if (index < 0 || index > size) {
			throw new IndexOutOfBoundsException("MyLinkedList Out of bounds exception.");
		}
		// check for null
		if (element == null) {
			throw new NullPointerException("MyLinkedList object cannot store null pointers.");
		}
		
		// get pointer to node after index
		LLNode<E> n = head.next;
		// iterate thru list
		for (int i = 0; i < index; i++) {
			n = n.next;
		}
		
		// create new node
		LLNode<E> newElement = new LLNode<E>(element, n.prev, n);
		// Link node in at end of list
		newElement.prev.next = newElement;
		n.prev = newElement;
		// increase size
		size++;
		System.out.println(toString());
		
	}


	/** Return the size of the list */
	public int size() 
	{
		return size;
	}

	/** Remove a node at the specified index and return its data element.
	 * @param index The index of the element to remove
	 * @return The data element removed
	 * @throws IndexOutOfBoundsException If index is outside the bounds of the list
	 */
	public E remove(int index) 
	{
		// validate input
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException("MyLinkedList Out of bounds exception.");
		}

		// get pointer to node to remove
		LLNode<E> n = head.next;
		// iterate thru list
		for (int i = 0; i < index; i++) {
			n = n.next;
		}
		n.toString();
		// Remove node in at index
		n.next.prev = n.prev;
		n.prev.next = n.next;
		// decrease size
		size--;
		return n.data;
	}

	/**
	 * Set an index position in the list to a new element
	 * @param index The index of the element to change
	 * @param element The new element
	 * @return The element that was replaced
	 * @throws IndexOutOfBoundsException if the index is out of bounds.
	 * @throws NullPointerException if the element is null
	 */
	public E set(int index, E element) 
	{
		// validate input
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException("MyLinkedList Out of bounds exception.");
		}
		// check for null
		if (element == null) {
			throw new NullPointerException("MyLinkedList object cannot store null pointers.");
		}
		
		// get pointer to node
		LLNode<E> n = head.next;
		// iterate thru list
		for (int i = 0; i < index; i++) {
			n = n.next;
		}
		
		// change data in node
		E rtn = n.data;
		n.data = element;

		return rtn;
	}   
	
	@Override
	public String toString() {
		if (size < 1) {
			return "";
		}
		LLNode<E> current = head.next;
		String output = "";
		while(current.data != null)
		{
			output += "[" + current.data.toString() + "]";
			current = current.next;
		}
		return output;
		
	}
}

class LLNode<E> 
{
	LLNode<E> prev;
	LLNode<E> next;
	E data;

	public LLNode(E e) 
	{
		this.data = e;
		this.prev = null;
		this.next = null;
	}

	public LLNode(E e, LLNode<E> p, LLNode<E> n) 
	{
		this.data = e;
		this.prev = p;
		this.next = n;
	}

}
