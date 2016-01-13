package test;

import ds.NumberTrie;
import ds.Traveller;

public class NumberTrieTester {

	public static void main(String[] args) {
		NumberTrie numbers = new NumberTrie();
		numbers.add("1234");
		numbers.add("3567");
		numbers.add("1233");
		
		System.out.println("contains : 1234: " + numbers.contains("1234"));
		System.out.println("contains : 7614: " + numbers.contains("7614"));
		
		
		numbers.add("7614");
		numbers.add("4564");
		numbers.remove("1234");
		
		System.out.println("contains : 1234: " + numbers.contains("1234"));
		System.out.println("contains : 3546: " + numbers.contains("3546"));
		
		System.out.println("size: "+numbers.size());
		
		Traveller trav = numbers.traveller();
		System.out.println("Traveller running");
		while(trav.hasNext()){
			System.out.println(trav.next());
		}
		
		numbers.clear();
		System.out.println("contains : 1234: " + numbers.contains("1234"));
		
	}

}
