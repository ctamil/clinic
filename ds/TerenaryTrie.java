package ds;


public class TerenaryTrie implements Container, PrefixSearch{

	private int size = 0;
	private Node head;

	private class Node{
		private boolean isPresent = false;
		private Character data =  null;
		private Object userData = null; 
		private Node left, middle, right;
		private Node(){
		}
	}
	
	public TerenaryTrie() {
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
		LinkedList list = new LinkedList();
		prefixSearchAndAdd(list, head.middle);
		return list;
	}

	private void prefixSearchAndAdd(LinkedList list, Node node) {
		if(node == null || node.data == null) return;
		
		prefixSearchAndAdd(list, node.left);
		if(node.isPresent) list.add(node.userData); //data added to linked list.
		prefixSearchAndAdd(list, node.middle);// move to next node
		prefixSearchAndAdd(list, node.right);
	}

	@Override
	public void add(Object obj, String key) {
		if(head.middle == null) head.middle = new Node();
		add(key, 0, head.middle, obj);
	}

	@Override	
	public void add(Object obj) {
		add(obj, obj.toString());
	}

	private void add(String string, int index, Node node, Object obj) {
		if(index == string.length()-1){
			if(node.data == null) node.data = string.charAt(index);
			if(node.data == string.charAt(index)){
				size++;
				node.isPresent = true;
				node.userData = obj;
				return;
			}
		}
		if(node.data == null) node.data = string.charAt(index);
		if(node.data == string.charAt(index)){ //middle
			if(node.middle == null) node.middle = new Node();
			add(string, index+1, node.middle, obj);
		}else if(string.charAt(index) < node.data){ //left
			if(node.left == null) node.left = new Node();
			add(string, index, node.left, obj);
		}else{ //right
			if(node.right == null) node.right = new Node();
			add(string, index, node.right, obj);
		}
	}

	@Override
	public void remove(Object obj) {
		remove(obj.toString());
	}
	
	@Override
	public void remove(String key) {
		if(contains(key)) remove(key, 0, head.middle);
	}

	private void remove(String string, int index, Node node) {
		if(string.length()-1 == index){//in last char
			if(node.data == string.charAt(index) && node.isPresent){
				node.isPresent = false;
				node.userData = null;
				size--;
				return;
			}
		}
		if(index < string.length()){
			if(node.data == string.charAt(index)) remove(string, index+1, node.middle);
			else if(string.charAt(index) < node.data) remove(string, index, node.left);
			else remove(string, index, node.right);
		}
	}
	
	@Override
	public Object get(Object obj) {
		if(contains(obj)) return get(obj.toString(), 0, head.middle);
		else return null;
	}

	private Object get(String string, int index, Node node) {
		if(node == null || node.data == null) return null;
		if(string.length() == index+1)
			if(node.data == string.charAt(index)) return node.userData;
		if(node.data == string.charAt(index)) return get(string, index+1, node.middle);
		else if(string.charAt(index) < node.data) return get(string, index, node.left);
		else return get(string, index, node.right);
	}


	@Override
	public boolean contains(Object o) {
		return contains(o.toString(), 0, head.middle);
	}

	private boolean contains(String string, int index, Node node) {
		if(node == null || node.data == null) return false;
		if(string.length() == index+1){
			if(node.data == string.charAt(index)) return node.isPresent;
		}
		if(node.data == string.charAt(index)) return contains(string, index+1, node.middle);
		else if(string.charAt(index) < node.data) return contains(string, index, node.left);
		else return contains(string, index, node.right);
	}

	@Override
	public void clear() {
		head = new Node();
		size = 0;
	}

	@Override
	public Traveller prefix(String number) {
		LinkedList list = new LinkedList();
		Object value = get(number);
		if(value!=null) list.add(value);
		prefixSearch(list, findNode(0, number, head.middle));
		return list.traveller();
	}
	
	private void prefixSearch(LinkedList linkedList, Node node) {
		if(node == null || node.data == null) return;
		
		prefixSearch(linkedList, node.left);
		if(node.isPresent) linkedList.add(node.userData);
		prefixSearch(linkedList, node.middle);
		prefixSearch(linkedList, node.right);
	}

	private Node findNode(int index, String number, Node node) {
		if(node == null || node.data == null) return null;
		
		if(index == number.length()-1){
			if(node.data == number.charAt(index)) 
				return node.middle;
		}
		
		if(node.data == number.charAt(index)) return findNode(index+1, number, node.middle);
		else if(number.charAt(index) < node.data) return findNode(index, number, node.left);
		else return findNode(index, number, node.right);
	}

	

		

}
