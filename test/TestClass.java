package test;


import java.io.File;

import billing.BillGenerator;
import ds.Traveller;
import dto.Bill;
import dto.Item;
import dto.Patient;
import dto.User;

public class TestClass {
	
	public static void main(String ...a){
		Patient logesh = new Patient("logesh", 999995798, 641006, "ganapthy", null, null, null, 6, 6, 1994, true, null);
		Item item1 = new Item("item1", 25, 2);
		Item item2 = new Item("item2", 10, 3);
		Item item3 = new Item("item2", 30, 1);
		User user = new User("Tamil", "1234");
		
		Bill bill = new Bill(logesh, user, 1);
		bill.addItem(item1);
		bill.addItem(item2);
		bill.addItem(item3);

		System.out.println(bill);
		File billDoc = BillGenerator.write(bill, "C:\\workspace\\ClincManagement", 
				new File("C:\\workspace\\ClincManagement\\bill_format.docx"));
		System.out.println(billDoc);
		
		Traveller itemTrav = bill.getItems().traveller();
		while(itemTrav.hasNext()){
			System.out.println(itemTrav.next());
		}
	}
}
