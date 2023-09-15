package dataaccess;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import business.Movie;
import business.RentableItem;

public class MovieReader {

	public RentableItem readerJson(int itemNo) {
		JSONParser parser = new JSONParser();

        try {

            Object obj = parser.parse(new FileReader("Files/"+itemNo+".json"));

            JSONObject jsonObject = (JSONObject) obj;
            
            return JsonToMovie(jsonObject);
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
	}

	public ArrayList<RentableItem> readerManyJson() {
		
		JSONParser parser = new JSONParser();

        try {

        	FileReader newFile = new FileReader("Files/movies.json");
        	if(newFile.ready()) {
        		Object obj = parser.parse(newFile);

                JSONObject jsonObject = (JSONObject) obj;

                JSONArray books = (JSONArray) jsonObject.get("movies");
            	ArrayList<RentableItem> booksArray = new ArrayList<>();

                for (Object object : books) {
                	booksArray.add(JsonToMovie((JSONObject) object));
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
	@SuppressWarnings("unchecked")
	private Movie JsonToMovie(JSONObject jsonObject) {
		
        int id = (int) (long) jsonObject.get("id");

        String name = (String) jsonObject.get("name");
        
        boolean isRented = (boolean) jsonObject.get("isRented");
        
        String genre = (String) jsonObject.get("genre");

        String producer = (String) jsonObject.get("producer");
        
        JSONArray actors = (JSONArray) jsonObject.get("actors");

		return new Movie(name,id, isRented, genre,producer,actors);
	}
}
