package main;

import java.awt.EventQueue;

import frames.LoginPage;

public class Main {

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				new LoginPage().setVisible(true);
			}
		});
		
	}

}

