package apartmentbillingsystem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

public class ApartmentBillingManager {
	
	private String lastUpdateDate;
	private String[][] billInputArr;
	private String[][] flatInputArr;
	private Apartment apartment;
    ReadCsv billInput;
    ReadCsv flatInput;
	
	public ApartmentBillingManager() {

	}
	
	public void start() {
		StoredDate storedDate = new StoredDate();
		String date = storedDate.readDate();
		createApartment(date);
		startConsole();
		
	}

	public Apartment getApartment() {
		return apartment;
	}
	
	private void createApartment(String lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;

        billInput = new ReadCsv("BillingInfo-" + lastUpdateDate + ".csv");
        flatInput = new ReadCsv("ApartmentInfo.csv");
		billInputArr = billInput.readCsv();
		flatInputArr = flatInput.readCsv();
		
		int flatNum =Integer.parseInt(flatInputArr[flatInputArr.length-1][0]) ;
		int floorNum = Integer.parseInt(flatInputArr[flatInputArr.length-1][1]);
		int flatPerFloor = flatNum/floorNum;
		
		Flat[][] apartmentArr = new Flat[floorNum][flatPerFloor];
		int flatNo = 0;
		for(int i=0; i<floorNum; i++) {
			for(int j=0; j<flatPerFloor; j++) {
				apartmentArr[i][j] = new Flat(flatInputArr[flatNo],searchBill(flatNo+1));
				flatNo++;
			}
		}
		this.apartment = new Apartment(apartmentArr);
	}
	
	private void startConsole() {
		ConsoleView view = new ConsoleView(getApartment());
		while(true) {
			String[] paramsToChangePaymentInfo = view.menu();
			if(paramsToChangePaymentInfo != null) {
				int billId = Integer.parseInt(paramsToChangePaymentInfo[0]);
				boolean isPaid = Boolean.parseBoolean(paramsToChangePaymentInfo[1]);
				changePaymentInfo(billId, isPaid);
				view = new ConsoleView(getApartment());
			}
			view.menu();
		}
	}

	private void changePaymentInfo(int billId, boolean isPaid) {
		for(int i = 0; i < billInputArr.length; i++) {
			if(Integer.parseInt(billInputArr[i][0]) == billId) {
				String date = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
				if (isPaid) {
					billInputArr[i][4] = "true";
				} else {
					billInputArr[i][4] = "false";
				}
				billInputArr[i][6] = date;
				
				UpdateCsv up = new UpdateCsv(billInputArr, lastUpdateDate);
				up.updateCsv();
				StoredDate storeDate = new StoredDate();
				storeDate.updateDate(date);
				createApartment(date);

				billInputArr = billInput.readCsv();
				flatInputArr = flatInput.readCsv();

			}
		}
	}
	private ArrayList<Bill> searchBill(int flatNo) {
		ArrayList<Bill> billList = new ArrayList<Bill>();
		for(int i=0; i< billInputArr.length; i++) {
			int flatId = Integer.parseInt(billInputArr[i][1]);
			if(flatId == flatNo) {
				billList.add(new Bill(billInputArr[i]));
			}
		}
		return billList;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ApartmentBillingManager [lastUpdateDate=").append(lastUpdateDate).append(", billInputArr=")
				.append(Arrays.toString(billInputArr)).append(", flatInputArr=").append(Arrays.toString(flatInputArr))
				.append(", apartment=").append(apartment).append("]");
		return builder.toString();
	}
	
}