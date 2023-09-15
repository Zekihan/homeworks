package main;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Stream;

public class App {

	public static void main(String[] args) 
	{
		String ItemFile = readLineByLine("src/ShoppingList.txt");
		String[] itemFileLines = ItemFile.split("\n");
		
		int ItemNum = itemFileLines.length;
		
		ListInterface<Item> itemList = new DLList<Item>();
		
		for (int i = 0; i < ItemNum; i++) 
		{
			String[] itemString = itemFileLines[i].split(",");
			itemList.add(new Item(itemString[0],Double.parseDouble(itemString[1]))); 
		}
		
		ListSorter.sort(itemList);
		
		String priorityFile = readLineByLine("src/ShoppingPriorities.txt");
		String[] priorityFileLines = priorityFile.split("\n");
		int n = priorityFileLines.length;
		
		
		Map<String, Integer> priorityMap = new HashMap<String, Integer>();
		
		for (int i = 1; i < n+1; i++) 
		{
			String[] priorityString = priorityFileLines[i-1].split(",");
			String itemName = priorityString[0];
			
			int priority;
			
			if (priorityString.length == 2)
			{
				priority = Integer.parseInt(priorityString[1]);
				
				if(priority > 10 && 1 > priority)
				{
					throw new IllegalArgumentException("Wrong file query");
				}
				
			}
			else
			{
				throw new IllegalArgumentException("Wrong file query");
			}
			
			priorityMap.put(itemName, priority);
		
		}
		
		PriorityQueueInterface<Item> shoppingList = new LinkedPQ<Item>();
		
		for (int i = 1; i < itemList.size()+1; i++)
		{
			Item item = itemList.getEntry(i);
			String itemName = item.getName();
			Integer priority;
			
			if (priorityMap.containsKey(itemName))
			{
				priority = priorityMap.get(itemName);
				shoppingList.add(item, priority);
			}
			else
			{
				priority = 1;
				shoppingList.add(item, priority);
			}
		}
			
		Scanner keyboard = new Scanner(System.in);
	    System.out.print("Please enter a budget : ");
	    String input = keyboard.nextLine();
	    keyboard.close();
		
	    double budget = Double.parseDouble(input);
	    
	    int shoppingListSize = shoppingList.getSize();
	    for (int i = 0; i < shoppingListSize; i++) {
			
	    	double itemPrice = shoppingList.peek().getPrice();
	    	if (budget - itemPrice > 0)
	    	{
	    		budget-= itemPrice;
	    		System.out.println(shoppingList.remove().getName());
	    	}
	    	else
	    	{
	    		break;
	    	}
	    }
	    
	    System.out.println("Remaining budget:" + budget);

	}

	private static String readLineByLine(String filePath)
	{	
	    StringBuilder contentBuilder = new StringBuilder();
	    try (Stream<String> stream = Files.lines( Paths.get(filePath), StandardCharsets.UTF_8))
	    {
	        stream.forEach(s -> contentBuilder.append(s).append("\n"));
	    }
	    catch (IOException e)
	    {
	        e.printStackTrace();
	    }
	    return contentBuilder.toString();
	
	}
}
