package apartmentbillingsystem;

import java.util.ArrayList;
import java.util.Arrays;

public class Apartment {

	private Flat[][] apartment ;
	private int numberOfFloors;
	private int numberOfFlats;

	
	public Apartment(Flat[][] apartment) {
		this.setApartment(apartment);
		this.numberOfFloors = apartment.length;
		this.numberOfFlats = apartment[0].length;
	}

	public Flat[][] getApartment() {
		return apartment;
	}

	private void setApartment(Flat[][] apartment) {
		this.apartment = apartment;
	}

	public int getNumberOfFloors() {
		return numberOfFloors;
	}

	public int getNumberOfFlats() {
		return numberOfFlats;
	}
	
	public ArrayList<Bill> getAllBills() {
		ArrayList<Bill> billList = new ArrayList<Bill>();
		for(Flat flat: getAllFlats()) {
			billList.addAll(flat.getBillList());
		}
		return billList;
	}
	
	public ArrayList<Flat> getAllFlats() {
		ArrayList<Flat> flatList = new ArrayList<Flat>();
		for (int i = 0; i < numberOfFloors; i++) {
			for (int j = 0; j < numberOfFlats; j++) {
				flatList.add(apartment[i][j]);
			}		
		}
		return flatList;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Apartment [apartment=").append(Arrays.toString(apartment)).append(", numberOfFloors=")
				.append(numberOfFloors).append(", numberOfFlats=").append(numberOfFlats).append("]");
		return builder.toString();
	}

	
	
}

