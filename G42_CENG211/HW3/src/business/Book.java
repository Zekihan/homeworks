package business;

import dataaccess.BookSaver;

public class Book extends RentableItem {

	private String author;
	private String publisher;
	
	public Book() {
		this("no name", -1, false, "no author", "no publisher");
	}
	
	public Book(String name, int itemNo, boolean isRented, String author, String publisher) {
		super(name,itemNo, isRented);
		setAuthor(author);
		setPublisher(publisher);
	}

	@Override
	public void store(String format) {
		BookSaver bs = new BookSaver();
		if(format.equals("json")) {
			bs.saverManyJson(this);
		}else {
			bs.saverXml(this);
		}
		
	}

	public String getAuthor() {
		return author;
	}

	public String getPublisher() {
		return publisher;
	}

	private void setAuthor(String author) {
		this.author = author;
	}

	private void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	@Override
	public String getTextToSearchOn(String attribute) {
		String text;
		switch(attribute) {
			case "author":
				text = getAuthor();
				break;
			case "publisher":
				text =  getPublisher();
				break;
			case "name":
				text = getName();
				break;
			default:
				text = null;
				break;
		}
		return text;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Book [author=").append(author).append(", publisher=").append(publisher).append(", toString()=")
				.append(super.toString()).append("]");
		return builder.toString();
	}
	
}
