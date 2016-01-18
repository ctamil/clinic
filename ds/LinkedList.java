package ds;


public class LinkedList implements Container, Traveller{

	private int size;
	private Node head;
	private Node tail;
	private Node iteratorHead;
	
	public LinkedList(){
		head = new Node();
		tail = head;
		iteratorHead = head;
	}
	
	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		if(size == 0) return true;
		else return false;
	}

	@Override
	public Traveller traveller() {
		iteratorHead = head.next;
		return this;
	}

	@Override
	public void add(Object e) {
		if(e == null) return;
		Node newNode = new Node(e);
		newNode.prev = tail;
		tail.next = newNode;
		tail = tail.next;  //tail updated to new node
		size++;
	}

	@Override
	public void remove(Object o) {
		Node traveller = head.next;
		while(traveller != null){
			if(traveller.data.equals(o)){
				Node tPrev = traveller.prev;
				Node tNext = traveller.next;
				if(tNext != null) tNext.prev = tPrev;
				if(tPrev != null) tPrev.next = tNext;
				size--;
				return;
			}
			traveller = traveller.next;
		}
	}

	@Override
	public boolean contains(Object o) {
		Traveller trav = traveller();
		while(trav.hasNext()) if(trav.next().equals(o)) return true;
		return false;
	}

	@Override
	public void clear() {
		size = 0;
		head.next = null;
		tail = head;
	}

	class Node{
		public Node(){}
		public Node(Object e) {
			this.data = e;
		}
		private Node next;
		private Node prev;
		private Object data;
	}

	@Override
	public boolean hasNext() {
		if(iteratorHead != null) return true;
		else return false;
	}

	@Override
	public Object next() {
		Object nextData = iteratorHead.data;
		iteratorHead = iteratorHead.next;
		return nextData;
	}
	
	@Override
	public String toString() {
		StringBuilder toString = new StringBuilder();
		toString.append("size = "+size+"\n");
		toString.append("items : \n");
		Traveller trav = traveller();
		while(trav.hasNext()) toString.append(trav.next()+"\n");
		return toString.toString();
	}

	@Override
	public Object get(Object obj) {
		return obj;
	}

}
