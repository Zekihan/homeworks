package dataaccess;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import business.Invoice;

public class InvoiceReader {

public ArrayList<Invoice> readerManyJson() {
		
		JSONParser parser = new JSONParser();

        try {

        	FileReader newFile = new FileReader("Files/invoices.json");
        	if(newFile.ready()) {
        		Object obj = parser.parse(newFile);

                JSONObject jsonObject = (JSONObject) obj;

                JSONArray invoices = (JSONArray) jsonObject.get("invoices");
                ArrayList<Invoice> invoicesArray = new ArrayList<>();
                for (Object object : invoices) {
                	invoicesArray.add(JsonToInvoice((JSONObject)object));
				}
                return invoicesArray;
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
	private Invoice JsonToInvoice(JSONObject jsonObject) {
		
		double cost = (double) (long) jsonObject.get("cost");
	    
	    String rentDate = (String) jsonObject.get("rentDate");
	    
	    String itemType = (String) jsonObject.get("itemType");
	    
	    Date date = dateParser(rentDate);
	    
	    int itemNo = (int) (long) jsonObject.get("itemNo");
	    
		return new Invoice(date,itemType,cost,itemNo);
	}
	private Date dateParser(String dateStr) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		Date date = null;
			try {
				date = sdf.parse(dateStr);
			} catch (java.text.ParseException e) {
				e.printStackTrace();
			}
		return date;
	}
}
