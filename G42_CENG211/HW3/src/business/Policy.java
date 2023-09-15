package business;

public enum Policy {
	NewReleasePolicy(18.50, 24.50),
	OldReleasePolicy(9.99, 15.75),
	BargainPolicy(13, 24.50);
	
	private final double price;
	private final double overDuePrice;
	
	Policy(double price, double overDuePrice){
		this.price = price;
		this.overDuePrice = overDuePrice;
	}

	public double getPrice() {
		return price;
	}

	public double getOverDuePrice() {
		return overDuePrice;
	}
	
	
	
}
