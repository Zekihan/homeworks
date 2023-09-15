package main;

public final class Item {
	
	private final String name;
	private final double price;

	public Item(String itemname, double itemprice) {
	
	name = itemname;
	price = itemprice;
	
	}

	public String getName() {
		return name;
	}
	
	public double getPrice() {
		return price;
	}
}