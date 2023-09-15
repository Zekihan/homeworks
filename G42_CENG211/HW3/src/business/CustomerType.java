package business;

public enum CustomerType {
	REGULAR(0),
	SILVER(10),
	GOLD(15),
	PREMIUM(20);
	
	private final int discount;
	
	CustomerType(int discount) {
		this.discount = discount;
	}

	public int getDiscount() {
		return discount;
	}
}
