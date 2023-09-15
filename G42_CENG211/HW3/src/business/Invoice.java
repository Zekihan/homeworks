package business;
import java.util.Date;

import dataaccess.InvoiceSaver;

public class Invoice implements IStorable {
	private Date rentDate;
	private Date dueDate;
	private String itemType;
	private double cost;
	private int itemNo;
	
	public Invoice(Date opDate, String itemType, double cost, int itemNo) {
		setRentDate(opDate);
		setCost(cost);
		setItemType(itemType);
		setDueDate(findDueDay(itemType));
		setItemNo(itemNo);
		
		
	}
	
	public Date getRentDate() {
		return rentDate;
	}
	public Date getDueDate() {
		return dueDate;
	}

	public double getCost() {
		return cost;
	}
	private void setRentDate(Date rentDate) {
		this.rentDate = rentDate;
	}
	private void setDueDate(int dayNum) {
		long date = rentDate.getTime();
		this.dueDate = new Date(date + (dayNum * 86400000));
	}
	
	private void setCost(double cost) {
		this.cost = cost;
	}
	private int findDueDay(String itemType) {
		if (itemType.equals("book") ) {
			return 7;
		}else if(itemType.equals("movie")) {
			return 2;
		}else {
			throw new IllegalArgumentException("Invalid item type");
		}
	}

	@Override
	public void store(String format) {
		InvoiceSaver is = new InvoiceSaver();
		is.saverManyJson(this);
	}

	public String getItemType() {
		return itemType;
	}

	private void setItemType(String itemType) {
		this.itemType = itemType;
	}

	public int getItemNo() {
		return itemNo;
	}

	private void setItemNo(int itemNo) {
		this.itemNo = itemNo;
	}


	
}


