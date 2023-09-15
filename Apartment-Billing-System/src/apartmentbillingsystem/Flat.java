package apartmentbillingsystem;

import java.util.ArrayList;

public class Flat {
	private int flatId;
	private int floorNo;
	private int flatNo;
	private int noOfRooms;
	private int sqrMeter;
	private ArrayList<Bill> billList;
	
	public Flat(String[] flatInfoArr, ArrayList<Bill> BillList) {
		this.flatId = Integer.parseInt(flatInfoArr[0]);
		this.floorNo = Integer.parseInt(flatInfoArr[1]);
		this.flatNo = Integer.parseInt(flatInfoArr[2]);
		this.noOfRooms = Integer.parseInt(flatInfoArr[3]);
		this.sqrMeter = Integer.parseInt(flatInfoArr[4]);
		this.billList = BillList;
	}
	
	public ArrayList<Bill> getBillList() {
		return billList;
	}

	public int getFlatId() {
		return flatId;
	}

	public int getFloorNo() {
		return floorNo;
	}

	public int getFlatNo() {
		return flatNo;
	}

	public int getNoOfRooms() {
		return noOfRooms;
	}

	public int getSqrMeter() {
		return sqrMeter;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Flat [flatId=").append(flatId).append(", floorNo=").append(floorNo).append(", flatNo=")
				.append(flatNo).append(", noOfRooms=").append(noOfRooms).append(", sqrMeter=").append(sqrMeter)
				.append("]");
		return builder.toString();
	}
}
