package ds;

public class TerenaryTrie implements Container{

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
		prefixSearchAndAdd(list, new StringBuilder(), head.middle);
		return list;
	}
	

	private void prefixSearchAndAdd(LinkedList list,
			StringBuilder stringBuilder, Node node) {
		if(node == null || node.data == null) return;
		prefixSearchAndAdd(list, stringBuilder, node.left);
		
		//only the middle node has datas.
		stringBuilder.append(node.data); //data added to string
		if(node.isPresent) list.add(stringBuilder.toString()); //data added to linked list.
		prefixSearchAndAdd(list, stringBuilder, node.middle);// move to next node
		stringBuilder.deleteCharAt(stringBuilder.length()-1); //removed the data.
		
		prefixSearchAndAdd(list, stringBuilder, node.right);
	}

	
	@Override	
	public void add(Object obj) {
		if(head.middle == null) head.middle = new Node();
		add(obj.toString(), 0, head.middle, obj);
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
		}else if(node.data > string.charAt(index)){ //left
			if(node.left == null) node.left = new Node();
			add(string, index, node.left, obj);
		}else{ //right
			if(node.right == null) node.right = new Node();
			add(string, index, node.right, obj);
		}
	}

	@Override
	public void remove(Object o) {
		if(contains(o)) remove(o.toString(), 0, head.middle);
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
			else if(node.data < string.charAt(index)) remove(string, index, node.left);
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
		if(string.length() == index+1){
			if(node.data == string.charAt(index)) return node.userData;
			else return null;
		}else{
			if(node.data == string.charAt(index)) return contains(string, index+1, node.middle);
			else if(node.data < string.charAt(index)) return contains(string, index, node.left);
			else return contains(string, index, node.right);
		}	
	}


	@Override
	public boolean contains(Object o) {
		return contains(o.toString(), 0, head.middle);
	}

	private boolean contains(String string, int index, Node node) {
		if(node == null || node.data == null) return false;
		if(string.length() == index+1){
			if(node.data == string.charAt(index)) return node.isPresent;
			else return false;
		}else{
			if(node.data == string.charAt(index)) return contains(string, index+1, node.middle);
			else if(node.data < string.charAt(index)) return contains(string, index, node.left);
			else return contains(string, index, node.right);
		}
				
	}

	@Override
	public void clear() {
		head = new Node();
		size = 0;
	}

	

}
