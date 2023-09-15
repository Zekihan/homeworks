package apartmentbillingsystem;

public class Bill {
	
	private int billId;
	private int flatId;
	private int amount;
	private String type;
	private boolean isPaid;
	private String deadlineDate;
	private String lastUpdateDate;
	
	public Bill(String[] BillInfoArr) {
		this.billId = Integer.parseInt(BillInfoArr[0]);
		this.flatId = Integer.parseInt(BillInfoArr[1]);
		this.amount = Integer.parseInt(BillInfoArr[2]);
		this.type = BillInfoArr[3];
		this.isPaid = Boolean.parseBoolean(BillInfoArr[4]);
		this.deadlineDate = BillInfoArr[5];
		this.lastUpdateDate = BillInfoArr[6];
	}

	public int getBillId() {
		return billId;
	}

	public int getFlatId() {
		return flatId;
	}

	public int getAmount() {
		return amount;
	}

	public String getType() {
		return type;
	}

	public boolean isPaid() {
		return isPaid;
	}

	public String getDeadlineDate() {
		return deadlineDate;
	}

	public String getLastUpdateDate() {
		return lastUpdateDate;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Bill [billId=").append(billId).append(", flatId=").append(flatId).append(", amount=")
				.append(amount).append(", type=").append(type).append(", isPaid=").append(isPaid)
				.append(", deadlineDate=").append(deadlineDate).append(", lastUpdateDate=").append(lastUpdateDate)
				.append("]");
		return builder.toString();
	}
}
