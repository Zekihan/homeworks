package dataaccess;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import business.Book;
import business.RentableItem;

public class BookReader {

	
	public RentableItem readerJson(int itemNo) {
		JSONParser parser = new JSONParser();

        try {

            Object obj = parser.parse(new FileReader("Files/"+itemNo+".json"));

            JSONObject jsonObject = (JSONObject) obj;

            return JsonToBook(jsonObject);
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Book();
	}

	public ArrayList<RentableItem> readerManyJson() {
		
		JSONParser parser = new JSONParser();

        try {

        	FileReader newFile = new FileReader("Files/books.json");
        	if(newFile.ready()) {
        		Object obj = parser.parse(newFile);

                JSONObject jsonObject = (JSONObject) obj;

                JSONArray books = (JSONArray) jsonObject.get("books");
                ArrayList<RentableItem> booksArray = new ArrayList<>();
                for (Object object : books) {
					booksArray.add(JsonToBook((JSONObject)object));
				}
                return booksArray;
        	}
        	newFile.close();
            
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
			e.printStackTrace();
		}
        return null;	
	}
	private Book JsonToBook(JSONObject jsonObject) {
		
        int id = (int) (long) jsonObject.get("id");

        String name = (String) jsonObject.get("name");
        
        boolean isRented = (boolean) jsonObject.get("isRented");
        
        String author = (String) jsonObject.get("author");

        String publisher = (String) jsonObject.get("publisher");
        
		return new Book(name,id, isRented, author,publisher);
	}
}
