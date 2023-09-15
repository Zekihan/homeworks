package dataaccess;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import business.Invoice;

public class InvoiceSaver {
	
	@SuppressWarnings("unchecked")
	public void saverManyJson(Invoice invoice) {
		
		InvoiceReader reader = new InvoiceReader();
		ArrayList<Invoice> invoices = reader.readerManyJson();
		if (invoices == null) {
			invoices = new ArrayList<Invoice>();
		}
		JSONObject invoiceJ = InvoicesToJson(invoice);

		if(invoices.size()!=0) {
			boolean flag = false;
			for (Invoice object : invoices) {
				JSONObject obj = InvoicesToJson(object);
				if(!(obj.toJSONString().equals(invoiceJ.toJSONString()))) {
					flag = true;
				}
			}
			if(flag) {
				invoices.add(invoice);
			}
		}else {
			invoices.add(invoice);
		}
		
		JSONArray invoicesArray = new JSONArray();
		for (Invoice object : invoices) {
			JSONObject invoiceJson = InvoicesToJson(object);
			invoicesArray.add(invoiceJson);
		}
		JSONObject filer = new JSONObject();
		filer.put("invoices",invoicesArray);
		try {

			FileWriter file = new FileWriter("Files/invoices.json");
			file.write(filer.toJSONString());;
			file.flush();
			file.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void saverManyJson(ArrayList<Invoice> invoices) {
		 File file = new File("Files/invoices.json");
		 file.delete();
		 for (Invoice invoice : invoices) {
			saverManyJson(invoice);
		}
	}
	
	
	@SuppressWarnings("unchecked")
	private JSONObject InvoicesToJson(Invoice invoice) {
		
		JSONObject invoiceJ = new JSONObject();
		
		invoiceJ.put("cost",invoice.getCost());
		invoiceJ.put("rentDate",dateParser(invoice.getRentDate()));
		invoiceJ.put("itemType",invoice.getItemType());
		invoiceJ.put("itemNo",invoice.getItemNo());
		
		return invoiceJ;
	}
	
	private String dateParser(Date date) {
		
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		String strDate = dateFormat.format(date);
		return strDate;
	}
}
