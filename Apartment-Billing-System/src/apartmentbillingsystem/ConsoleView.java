package apartmentbillingsystem;

import java.util.Scanner;

public class ConsoleView {
	
	private Apartment a;
	private BillQuery bquery;
	
	
	public ConsoleView(Apartment a) {
		this.a = a;
		this.bquery = new BillQuery(a);
	}

	@SuppressWarnings("resource")
	public String[] menu() {
		Scanner keyboard = new Scanner(System.in);
		System.out.println("You can choose from the following menu");
		System.out.println("1) For changing the PaymentInfo of a bill\n"
				+ "2) List all bills\n"
				+ "3) List all flats\n"
				+ "4) For executing queries\n"
				+ "5) Exit");
		System.out.println("Please enter a number from list above(press enter at the end) :");
		String a = keyboard.next();
		switch (a) {
		case "1":
			System.out.println("Please enter ID of the bill that you want change payment info (At the end press enter) :");
			String billId = keyboard.next();
			System.out.println("Enter payment info (true or false) :");
			String isPaid = keyboard.next();
			String[] params = {billId, isPaid};
			return params;
		case "2":
			listAllBills();
			break;
		case "3":
			listAllFlats();
			break;
		case "4":
			queryMethods();
			query();
			break;
		case "5":
			keyboard.close();
			System.exit(1);
			break;
		default:
			break;
		}
		System.out.println("------------------------------------------------------------------------------------------------------------------");
		return null;
	}
	
	private void listAllBills() {
		for (Bill bill: a.getAllBills()) {
			System.out.println(bill.toString());
		}
	}

	private void listAllFlats() {
		for (Flat flat: a.getAllFlats()) {
			System.out.println(flat.toString());
		}
	}

	@SuppressWarnings("resource")
	public void query() {
		Scanner keyboard = new Scanner(System.in);
		System.out.println("Please select a query :");
		String query = keyboard.next();
		switch (query) {
		case "1":
			totalUnpaidBill();
			break;
		case "2":
			System.out.println("Please enter a type(At the end press enter) :");
			types();
			String type = keyboard.next();
			totalUnpaidCertainTypeBill(type);
			break;
		case "3":
			System.out.println("Please enter a floor(At the end press enter) :");
			int floor = keyboard.nextInt();
			totalFloorBill(floor);
			break;
		case "4":
			unpaidRemainingTime();
			break;
		case "5":
			System.out.println("Please enter a date(At the end press enter and date format shoul be (yyyy-MM-dd) :");
			String date = keyboard.next();
			paidBefore(date);
			break;
		case "6":
			System.out.println("Please enter a type(At the end press enter) :");
			types();
			String type1 = keyboard.next();
			unpaidPassedType(type1);
			break;
		case "7":
			System.out.println("Please enter a room number(At the end press enter) :");
			int room = keyboard.nextInt();
			avgRoomBill(room);
			break;
		case "8":
			System.out.println("Please enter a square meter(At the end press enter) :");
			int sq = keyboard.nextInt();
			avgSQBill(sq);
			break;
		default:
			break;
		}
	}

	private void queryMethods() {
		System.out.println("The following items are the list of all queries");
		System.out.println("1) Total amount of unpaid bills\n"
				+"2) Total amount of unpaid bills of a certain bill type\n"
				+"3) Total bill amount of a certain floor\n"
				+"4) List of the unpaid bills with the information of the remaining time (in days) before their deadlines\n"
				+"5) Total amount and number of paid bills before a certain date\n"
				+"6) Total amount and number of unpaid bills of a certain type that passed deadline\n"
				+"7) Average total amount of bills of N room flats\n"
				+"8) Average total amount of bills of flats with square meter greater than N\n");
	}
	private void types() {
		System.out.println("The following items are the list of all types");
		System.out.println("cleaning\n"
				+"electric\n"
				+"heating\n"
				+ "water\n");
	}
	private void totalUnpaidBill() {
		double total = bquery.totalUnpaidBill();
		System.out.println("Total amount of unpaid bills is "+String.format("%.2f",total));
	}
	private void totalUnpaidCertainTypeBill(String type) {
		double total = bquery.totalUnpaidCertainTypeBill(type);
		System.out.println("Total amount of unpaid bills of a certain bill type is "+String.format("%.2f",total));
	}
	private void totalFloorBill(int floor) {
		double total = bquery.totalFloorBill(floor);
		System.out.println("Total bill amount of a certain floor is "+String.format("%.2f",total));
	}
	private void unpaidRemainingTime() {
		System.out.println("List of the unpaid bills with the information of the remaining time (in days) before their deadlines are as following:");
		bquery.unpaidRemainingTime();
	}
	private void paidBefore(String date) {
		double[] aa = bquery.paidBefore(date);
		System.out.println("Number of paid bills before a certain date is "+(int)aa[1]);
		System.out.println("Total amount of paid bills before a certain date is "+String.format("%.2f",aa[0]));
	}
	private void unpaidPassedType(String type){
		double[] aa = bquery.unpaidPassedType(type);
		System.out.println("Number of unpaid bills of a certain type that passed deadline is "+(int)aa[1]);
		System.out.println("Total amount of unpaid bills of a certain type that passed deadline is "+String.format("%.2f",aa[0]));
	}
	private void avgRoomBill(int room) {
		double avg = bquery.avgRoomBill(room);
		System.out.println("Average total amount of bills of "+ room +" room flats is "+String.format("%.2f",avg));
	}
	private void avgSQBill(int sq) {
		double avg = bquery.avgSQBill(sq);
		System.out.println("Average total amount of bills of flats with square meter greater than "+ sq +" is "+String.format("%.2f",avg));
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ConsoleView [a=").append(a).append(", bquery=").append(bquery).append("]");
		return builder.toString();
	}
	
}
