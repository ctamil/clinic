package ds;

public class NumberTrie implements Container, Traveller{
	private Node head = null;
	private int size;
	private Traveller trieTraveller;
	
	private class Node{
		private boolean isPresent = false;
		private Object data;
		private Node next[] = new Node[10];
	}
	
	public NumberTrie() {
		head = new Node();
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
		trieTraveller = toLinkedList().traveller();
		return this;
	}

	private LinkedList toLinkedList() {
		LinkedList elements = new LinkedList();
		traverseAndAdd(elements, head, new StringBuilder());
		return elements;
	}

	private void traverseAndAdd(LinkedList elements, Node next,
			StringBuilder stringBuilder) {
		if(next == null) return;
		for(int i = 0; i<10; i++){
			if(next.next[i] != null){
				stringBuilder.append(i);
				if(next.next[i].isPresent == true) elements.add(stringBuilder.toString());
				traverseAndAdd(elements, next.next[i], stringBuilder);
				stringBuilder.deleteCharAt(stringBuilder.length()-1);
			}
		}
	}

	@Override
	public void add(Object o) {
		add(o.toString(), 0, head, o);
	}

	private void add(String string, int stringIndex, Node next, Object data) {
		int nodeIndex = getIndex(string, stringIndex);
		createNode(next, nodeIndex);
		if(string.length() == stringIndex+1){
			next.next[nodeIndex].isPresent = true;
			next.next[nodeIndex].data = data;
			size++;
		}else{
			add(string, stringIndex+1, next.next[nodeIndex], data);
		}
		
	}
	
	/**
	 * to create a node on specified index.
	 * @param next
	 * @param index
	 */
	private void createNode(Node next, int nodeIndex) {
		if(next.next[nodeIndex] == null) next.next[nodeIndex] = new Node();
	}

	/**
	 * gets the index for trie pointing location.
	 * @param string
	 * @param i
	 * @return
	 */
	private int getIndex(String string, int stringIndex) {
		return string.charAt(stringIndex)-'0';
	}

	@Override
	public void remove(Object o) {
		if(contains(o))
		remove(o.toString(), 0, head);
	}

	private void remove(String string, int stringIndex, Node next) {
		if(next == null) return;
		int nodeIndex = getIndex(string, stringIndex);
		if(string.length() == stringIndex+1){
			if(next.next[nodeIndex] != null && next.next[nodeIndex].isPresent == true){
				next.next[nodeIndex].isPresent = false;
				size--;
			}
		}else remove(string, stringIndex+1, next.next[nodeIndex]);
	}

	@Override
	public Object get(Object obj) {
		if(contains(obj))  return get(obj.toString(), 0, head);
		else return null;
	}
	
	private Object get(String string, int stringIndex, Node next) {
		if(next == null) return null;
		int nodeIndex = getIndex(string, stringIndex);
		if(string.length() == stringIndex+1){
			if(next.next[nodeIndex] != null) return next.next[nodeIndex].data;
			else return null;
		}else return contains(string, stringIndex+1, next.next[nodeIndex]);
	}

	@Override
	public boolean contains(Object obj) {
		return contains(obj.toString(), 0, head);
	}

	private boolean contains(String string, int stringIndex, Node next) {
		if(next == null) return false;
		int nodeIndex = getIndex(string, stringIndex);
		if(string.length() == stringIndex+1){
			if(next.next[nodeIndex] != null) return next.next[nodeIndex].isPresent;
			else return false;
		}else return contains(string, stringIndex+1, next.next[nodeIndex]);
	}
	
	

	@Override
	public void clear() {
		size = 0;
		head = new Node();
	}

	@Override
	public boolean hasNext() {
		return trieTraveller.hasNext();
	}

	@Override
	public Object next() {
		return trieTraveller.next();
	}

	
}
