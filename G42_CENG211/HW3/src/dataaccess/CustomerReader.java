package dataaccess;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import business.Customer;
import business.CustomerType;

public class CustomerReader {

	public ArrayList<Customer> readerManyJson() {
		
		JSONParser parser = new JSONParser();

        try {

        	FileReader newFile = new FileReader("Files/customers.json");
        	if(newFile.ready()) {
        		Object obj = parser.parse(newFile);

                JSONObject jsonObject = (JSONObject) obj;

                JSONArray customers = (JSONArray) jsonObject.get("customers");
                ArrayList<Customer> customersArray = new ArrayList<>();
                for (Object object : customers) {
                	customersArray.add(JsonToCustomer((JSONObject)object));
				}
                return customersArray;
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
	private Customer JsonToCustomer(JSONObject jsonObject) {
			
        int id = (int) (long) jsonObject.get("id");

        String name = (String) jsonObject.get("name");
        
        CustomerType type ;
        
        switch( (String)(jsonObject.get("type"))) {
		case "gold":
			type = CustomerType.GOLD;
			break;
		case "silver":
			type = CustomerType.SILVER;
			break;
		case "premium":
			type = CustomerType.PREMIUM;
			break;
		case "regular":
			type = CustomerType.REGULAR;
			break;
		default:
			type = CustomerType.REGULAR;
			break;
		}
        Customer customer = new Customer(name,id);
        customer.setType(type);
		return customer;
	}

}
