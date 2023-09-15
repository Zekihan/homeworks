package business;

public abstract class RentableItem implements IRentable,IStorable,ISearchable {

	private String name;
	private int itemNo;
	private boolean isRented;
	private Policy policy;
	
	public RentableItem(String name, int itemNo, boolean isRented) {
		setName(name);
		setItemNo(itemNo);
		setRented(isRented);
		setPolicy(Policy.NewReleasePolicy);
	}
	
	@Override
	public void rent() {
		setRented(true);
	}

	@Override
	public void turnIn() {
		setRented(false);
	}
	
	public String getName() {
		return name;
	}

	private void setName(String name) {
		this.name = name;
	}

	public int getItemNo() {
		return itemNo;
	}

	private void setItemNo(int itemNo) {
		this.itemNo = itemNo;
	}

	public boolean isRented() {
		return isRented;
	}

	private void setRented(boolean isRented) {
		this.isRented = isRented;
	}

	public Policy getPolicy() {
		return policy;
	}

	public void setPolicy(Policy policy) {
		this.policy = policy;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RentableItem [name=").append(name).append(", itemNo=").append(itemNo).append(", isRented=")
				.append(isRented).append(", policy=").append(policy).append("]");
		return builder.toString();
	}
	
	
}
