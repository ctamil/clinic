package test;


import ds.Traveller;
import ds.Trie;

public class TestClass {

	public static void main(String[] args) {
		Trie trie = new Trie();
		trie.add("Object 1", "323");
		trie.add("Object 2", "243");
		trie.add("Object 3", "786");
		trie.add("Object 4", "312");
		
		
		Traveller trav = trie.traveller();
		while(trav.hasNext()) System.out.println(trav);
		
		trie.add("542");
		trie.add("831");
		trie.add("556");
		
		trav = trie.prefix("5");
		while(trav.hasNext()) System.out.println(trav);
		
		System.out.println(trie.size());
		System.out.println(trie.contains("43"));
	}
	

}
