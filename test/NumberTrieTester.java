package test;

import ds.*;

public class NumberTrieTester {

	public static void main(String[] args) {
		TerenaryTrie numbers = new TerenaryTrie();
		numbers.add("1234");
		numbers.add("3567");
		numbers.add("1333");
		numbers.add("1222");
		numbers.add("1235");
		numbers.add("1323");
		
		System.out.println("size: "+numbers.size());
		
		Traveller trav = numbers.prefix("1222");
		System.out.println("Traveller running");
		while(trav.hasNext()){
			System.out.println(trav.next());
		}
		
	}

}
