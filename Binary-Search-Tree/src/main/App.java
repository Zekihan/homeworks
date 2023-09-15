package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {

	public static void main(String[] args) {

		List<String> keyList = new ArrayList<String>();
		List<Integer> valueList = new ArrayList<Integer>();
		keyList.add("tea");
		valueList.add(17);
		keyList.add("towel");
		valueList.add(24);
		keyList.add("juice");
		valueList.add(5);
		keyList.add("glass");
		valueList.add(36);
		BST<String,Integer> stocklist = new VanillaBST<String,Integer>(keyList,valueList);
		String key = "a";
		Integer value;
		Scanner in = new Scanner(System.in);

		System.out.println("When you are done use quit command to quit.");
		while (true) {
			
			System.out.println("Enter the command.");
	        String command = in.nextLine();
	        
			if (command.equals("add")) {
		        System.out.println("Enter name of the product ");
		        key = in.nextLine();
		        System.out.println("Enter price of the product ");
		        value = Integer.parseInt(in.nextLine());
		        stocklist.add(key, value);
			}
	        if (command.equals("remove")) {
	        	System.out.println("Enter name of the product ");
	 	        key = in.nextLine();
	 	        stocklist.remove(key);
	        }
	        if (command.equals("update")) {
	        	System.out.println("Enter name of the product ");
		        key = in.nextLine();
		        System.out.println("Enter price of the product ");
		        value = Integer.parseInt(in.nextLine());
		        stocklist.update(key, value);
	        }
	        if (command.equals("min")) {
	        	System.out.println(stocklist.min());
		        
	        }
	        if (command.equals("max")) {
	        	System.out.println(stocklist.max());
		        
	        }
	        if (command.equals("lessthan")) {
	        	System.out.println("Enter name of the product ");
	 	        key = in.nextLine();
	        	System.out.println(stocklist.lessThan(key));
		        
	        }if (command.equals("greaterthan")) {
	        	System.out.println("Enter name of the product ");
	 	        key = in.nextLine();
	        	System.out.println(stocklist.greaterThan(key));
		        
	        }
	        if (command.equals("get")) {
	        	System.out.println("Enter name of the product ");
	 	        key = in.nextLine();
	        	System.out.println(stocklist.get(key));
		        
	        }
	        if (command.equals("keys")) {
	        	System.out.println(stocklist.keys());
		        
	        }
	        if (command.equals("values")) {
	        	System.out.println(stocklist.values());
	        	
	        }
	        
	        if (command.equals("display")) {
	        	stocklist.display();
	        }
	        
	        if (command.equals("help")) {
	        	System.out.println("Commands");
	        	System.out.println("get");
	        	System.out.println("values");
	        	System.out.println("keys");
	        	System.out.println("greaterthan");
	        	System.out.println("lessthan");
	        	System.out.println("max");
	        	System.out.println("min");
	        	System.out.println("update");
	        	System.out.println("add");
	        	System.out.println("remove");
	        	System.out.println("quit");
	        	System.out.println("display");
	        }
	        if (command.equals("quit")) {
	        	break;
	        }
		}
		in.close();	
		
	}
	
}

