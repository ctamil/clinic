package ds;

public class Trie implements Container, PrefixSearch{
	private Node head = null;
	private int size;
	private static int R = 256;
	
	private class Node{
		private boolean isPresent = false;
		private Object data; //to store user data.
		private Node next[] = new Node[R];
	}
	
	public Trie() {
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
		return toLinkedList().traveller();
	}

	public LinkedList toLinkedList() {
		LinkedList elements = new LinkedList();
		traverseAndAdd(elements, head, new StringBuilder());
		return elements;
	}

	private void traverseAndAdd(LinkedList elements, Node next,
			StringBuilder stringBuilder) {
		if(next == null) return;
		for(int i = 0; i<R; i++){
			if(next.next[i] != null){
				stringBuilder.append((char)i);
				if(next.next[i].isPresent == true) elements.add(stringBuilder.toString());
				traverseAndAdd(elements, next.next[i], stringBuilder);
				stringBuilder.deleteCharAt(stringBuilder.length()-1);
			}
		}
	}
	
	@Override
	public void add(Object val, String key) {	
		add(key, 0, head, val);
	}
	
	@Override
	public void add(Object obj) {
		add(obj, obj.toString());
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
		return string.charAt(stringIndex);
	}

	@Override
	public void remove(String key) {
		if(contains(key)) remove(key, 0, head);
	}
	
	@Override
	public void remove(Object obj) {
		remove(obj.toString());
	}

	private void remove(String string, int stringIndex, Node next) {
		if(next == null) return;
		int nodeIndex = getIndex(string, stringIndex);
		if(string.length() == stringIndex+1){
			if(next.next[nodeIndex] != null && next.next[nodeIndex].isPresent == true){
				next.next[nodeIndex].isPresent = false;
				next.next[nodeIndex].data = null;
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
		}else return get(string, stringIndex+1, next.next[nodeIndex]);
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
	public Traveller prefix(String number) {
		LinkedList list = new LinkedList();
		prefixSearch(list, findNode(0, number, head));
		return list.traveller();
	}

	private void prefixSearch(LinkedList list,
			Node next) {
		if(next == null) return;
		if(next.isPresent) list.add(next.data);
		for(int nodeIndex = 0; nodeIndex < R; nodeIndex++){
			prefixSearch(list, next.next[nodeIndex]);
		}
	}

	private Node findNode(int stringIndex, String number, Node next){
		if(next == null) return null;
		int nodeIndex = getIndex(number, stringIndex);
		if(number.length() == stringIndex+1){
			if(next.next[nodeIndex] != null ) return next.next[nodeIndex];
			else return null;
		}else return findNode(stringIndex+1, number, next.next[nodeIndex]);
	}

}
