package dataaccess;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import business.Book;
import business.RentableItem;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class BookSaver {

	public void saverJson(Book book) {
		
		JSONObject books = BookToJson(book);
		
		try {

			FileWriter file = new FileWriter("Files/"+book.getItemNo()+".json");
			file.write(books.toJSONString());;
			file.flush();
			file.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@SuppressWarnings("unchecked")
	public void saverManyJson(Book book) {
		
		BookReader reader = new BookReader();
		ArrayList<RentableItem> books = reader.readerManyJson();
		if (books == null) {
			books = new ArrayList<RentableItem>();
		}
		JSONObject bookJ = BookToJson(book);

		if(books.size()!=0) {
			boolean flag = false;
			for (RentableItem object : books) {
				JSONObject obj = BookToJson((Book) object);
				if(!(obj.toJSONString().equals(bookJ.toJSONString()))) {
					flag = true;
				}
			}
			if(flag) {
				books.add(book);
			}
		}else {
			books.add(book);
		}
		
		JSONArray booksArray = new JSONArray();
		for (RentableItem object : books) {
			JSONObject bookJson = BookToJson((Book) object);
			booksArray.add(bookJson);
		}
		JSONObject filer = new JSONObject();
		filer.put("books",booksArray);
		try {

			FileWriter file = new FileWriter("Files/books.json");
			file.write(filer.toJSONString());;
			file.flush();
			file.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	public void saverManyJson(ArrayList<RentableItem> books) {
		 File file = new File("Files/books.json");
		 file.delete();
		 for (RentableItem book : books) {
			saverManyJson((Book)book);
		}
	}
	public void saverXml(Book book) {
		try {
			DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
			Document document = documentBuilder.newDocument();
			Element root = document.createElement("item");
			document.appendChild(root);
			Element bookItem = document.createElement("book");
			root.appendChild(bookItem);
			Attr attr = document.createAttribute("id");
			attr.setValue(book.getItemNo() + "");
			bookItem.setAttributeNode(attr);
			Element name = document.createElement("name");
			name.appendChild(document.createTextNode(book.getName()));
			bookItem.appendChild(name);
			Element author = document.createElement("author");
			author.appendChild(document.createTextNode(book.getAuthor()));
			bookItem.appendChild(author);
			Element publisher = document.createElement("publisher");
			publisher.appendChild(document.createTextNode(book.getPublisher()));
			bookItem.appendChild(publisher);
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource domSource = new DOMSource(document);
			StreamResult streamResult = new StreamResult(new File("Files/"+book.getItemNo() + ".xml"));
			transformer.transform(domSource, streamResult);
			
		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (TransformerException tfe) {
			tfe.printStackTrace();
		}
	}
	@SuppressWarnings("unchecked")
	private JSONObject BookToJson(Book book) {
		
		JSONObject bookJ = new JSONObject();
		
		bookJ.put("id", book.getItemNo());
		bookJ.put("name", book.getName());
		bookJ.put("isRented", book.isRented());
		bookJ.put("author", book.getAuthor());
		bookJ.put("publisher", book.getPublisher());
		
		return bookJ;
	}
	
}
