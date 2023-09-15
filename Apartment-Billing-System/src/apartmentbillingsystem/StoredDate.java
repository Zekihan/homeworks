package apartmentbillingsystem;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

public class StoredDate {
	private String date;
	
	public StoredDate() {
		
	}
	
	public String readDate() {
		try {
			BufferedReader fileInStream = new BufferedReader(new FileReader("LastUpdateDate.txt"));
			try {
				this.date = fileInStream.readLine();
				fileInStream.close();
				return date;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public void updateDate(String newDate) {
		try {
			PrintWriter outFile = new PrintWriter(new File("LastUpdateDate.txt"));
			outFile.println(newDate);
			outFile.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getDate() {
		return date;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("StoredDate [date=").append(date).append("]");
		return builder.toString();
	}
	
}
