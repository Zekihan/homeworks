package business;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import dataaccess.BookReader;
import dataaccess.BookSaver;
import dataaccess.CustomerReader;
import dataaccess.InvoiceReader;
import dataaccess.MovieReader;
import dataaccess.MovieSaver;

public class RentalStoreManager {

	private ArrayList<Invoice> invoices;
	private ArrayList<RentableItem> bookStock;
	private ArrayList<RentableItem> movieStock;
	private ArrayList<Customer> customers;
	
	public RentalStoreManager() {
		BookReader br = new BookReader();
		MovieReader mr = new MovieReader();
		InvoiceReader ir = new InvoiceReader();
		CustomerReader cr = new CustomerReader();
		invoices = ir.readerManyJson();
		if(invoices == null) {
			invoices = new ArrayList<>();
		}
		bookStock = br.readerManyJson();
		if(bookStock == null) {
			bookStock = new ArrayList<>();
		}
		movieStock = mr.readerManyJson();
		if(movieStock == null) {
			movieStock = new ArrayList<>();
		}
		customers = cr.readerManyJson();
		if(customers == null) {
			customers = new ArrayList<>();
		}
	}
	
	public void addMovieItem(String name, String genre, String producer, ArrayList<String> actors) {
		RentableItem movieItem = new Movie(name, createItemId(), false, genre, producer, actors);
		movieItem.store("json");
		movieStock.add(movieItem);
	}
	
	public void addBookItem(String name, String author, String publisher) {
		RentableItem bookItem = new Book(name, createItemId(), false, author, publisher);
		bookItem.store("json");
		bookStock.add(bookItem);
	}
	public void removeBookItem(int itemNo) {
		RentableItem item = findItemById("book", itemNo);
		bookStock.remove(item);
		BookSaver bs = new BookSaver();
		bs.saverManyJson(bookStock);
	}
	public void removeMovieItem(int itemNo) {
		RentableItem item = findItemById("movie", itemNo);
		movieStock.remove(item);
		MovieSaver bs = new MovieSaver();
		bs.saverManyJson(bookStock);
	}
	public int invoiceAmountForSpecificDay(String dateStr) {
		Date date = dateParser(dateStr);
		int counter = 0;
		for (Invoice invoice: invoices) {
			if (invoice.getRentDate().equals(date)){
				counter++;
			}
		}
		return counter;
	}
	
	public void addCustomer(String name) {
		int id = createCustId();
		Customer cust = new Customer(name,id);
		customers.add(cust);
		System.out.println("Successfully created customer named: " + name + " and with id: " + id );
		cust.store("json");
	}
	
	public void rentItem(int customerNo, String itemType, int itemNo, String operationDay) {
		RentableItem item = findItemById(itemType, itemNo);
		if (item.isRented()) {
			System.out.println("Item already rented");
		}else {
			rent(customerNo, item, itemType, operationDay);
		}
	}
	
	public void returnItem(int customerNo, String itemType, int itemNo, String operationDay) {
		RentableItem item = findItemById(itemType, itemNo);
		if (!item.isRented()) {
			System.out.println("Item is not rented");
		}else {
			turnIn(customerNo, item, operationDay);
		}
	}
	
	public ArrayList<RentableItem> searchBooks(String searchText, String searchAtt){
		return search(bookStock, searchText, searchAtt);
	}
	public ArrayList<RentableItem> searchMovies(String searchText, String searchAtt){
		return search(movieStock, searchText, searchAtt);
	}
	public ArrayList<RentableItem> searchBooksVanced(String searchText1, String searchText2, String searchAtt1, String searchAtt2){
		return searchVanced(bookStock, searchText1, searchText2, searchAtt1, searchAtt2);
	}
	public ArrayList<RentableItem> searchMoviesVanced(String searchText1, String searchText2, String searchAtt1, String searchAtt2){
		return searchVanced(bookStock, searchText1, searchText2, searchAtt1, searchAtt2);
	}
	
	private ArrayList<RentableItem> search(ArrayList<RentableItem> items, String searchText, String searchAtt){
		ArrayList<RentableItem> result = new ArrayList<RentableItem>();
		if(searchAtt.equals("actor")) {
			for (RentableItem item: items) {
				if (item.getTextToSearchOn(searchAtt).contains(searchText)){
					result.add(item);
				}
			}
		}
		for (RentableItem item: items) {
			if (item.getTextToSearchOn(searchAtt).equals(searchText)){
				result.add(item);
			}
		}		
		return result;
	}
	
	private ArrayList<RentableItem> searchVanced(ArrayList<RentableItem> items, String searchText1,
		String searchText2, String searchAtt1, String searchAtt2){
		ArrayList<RentableItem> result = new ArrayList<RentableItem>();
		if(searchAtt1.equals("actor") || searchAtt2.equals("actor")) {
			if(searchAtt1.equals("actor")) {
				for (RentableItem item: items) {
					if (item.getTextToSearchOn(searchAtt1).contains(searchText1) && item.getTextToSearchOn(searchAtt2).equals(searchText2)){
						result.add(item);
					}
				}
			}else {
				for (RentableItem item: items) {
					if (item.getTextToSearchOn(searchAtt2).contains(searchText2) && item.getTextToSearchOn(searchAtt1).equals(searchText1)){
						result.add(item);
					}
				}
			}
		}else {	
			for (RentableItem item: items) {
				if (item.getTextToSearchOn(searchAtt1).equals(searchText1) && item.getTextToSearchOn(searchAtt2).equals(searchText2)){
					result.add(item);
				}
			}
		}
		return result;
	}
	
	public ArrayList<Invoice> getInvoices() {
		return invoices;
	}
	
	
	private void rent(int customerNo, RentableItem item, String itemType, String operationDate) {
		int discountPercentage = findCustById(customerNo).getDiscountPercentge();
		Date date = dateParser(operationDate);
		double price = item.getPolicy().getPrice()*((100-discountPercentage)/100);
		
		Invoice invoice = new Invoice(date, itemType, price, item.getItemNo());
		invoices.add(invoice);
		invoice.store("json");
		item.rent();
	}
	
	private void turnIn(int customerNo, RentableItem item, String operationDate) {
		Date returnDate = dateParser(operationDate);
		Invoice invoice = findInvoiceByItemNo(item.getItemNo());
		Date dueDate = invoice.getDueDate();
		long milisecPassed =  returnDate.getTime() - dueDate.getTime();
		int daysPassed;
		if (item.getClass() == Book.class) {
			if(milisecPassed > 86400000*7) {
				daysPassed = (int) ((milisecPassed/86400000) - 7) ;
				for(int i = 0; i < daysPassed; i++) {
					Invoice newInvoice = new Invoice(dueDate, "book", item.getPolicy().getOverDuePrice(), item.getItemNo());
					invoices.add(newInvoice);
				}
			}
		}
		else {
			if(milisecPassed > 86400000*2) {
				daysPassed = (int) ((milisecPassed/86400000) - 2);
				for(int i = 0; i < daysPassed; i++) {
					Invoice newInvoice = new Invoice(dueDate, "book", item.getPolicy().getOverDuePrice(), item.getItemNo());
					invoices.add(newInvoice);
					invoice.store("json");
					
				}
			}
		}
		
		System.out.println("Customer ");
		item.turnIn();
	}	
	
	public RentableItem findItemById(String itemType, int itemNo) {
		ArrayList<RentableItem> items;
		if(itemType.equals("book")) {
			items = bookStock;
		}else if(itemType.equals("movie")){
			items = movieStock;
		}else {
			throw new IllegalArgumentException("Invalid item type");
		}
		for (RentableItem item: items) {
			if (item.getItemNo() == itemNo) {
				return item;
			}		
		}
		return null;
	}
	
	public Customer findCustById(int id) {
		for (Customer cust: customers) {
			if (cust.getId() == id) {
				return cust;
			}
		}
		return null;
	}
	
	public Invoice findInvoiceByItemNo(int itemNo) {
		for (Invoice invoice: invoices) {
			if (invoice.getItemNo() == itemNo) {
				return invoice;
			}
		}return null;
	}

	
	private int createItemId() {
		int id =  bookStock.size() + 1;
		return id;
	}
	private int createCustId() {
		if(customers == null) {
			return 1;
		}
		int id =  customers.size() + 1;
		return id;
	}

	
	private Date dateParser(String dateStr) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		Date date = null;
		try {
			date = sdf.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
}
