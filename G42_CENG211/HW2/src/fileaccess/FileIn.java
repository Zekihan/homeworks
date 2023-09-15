package fileaccess;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class FileIn {

	
	public FileIn() {
		
	}
	
	public String[][] readPiece(String fileName) {

		ArrayList<String[]> partList = new ArrayList<String[]>();
		boolean done = false;
		try {
			BufferedReader fileInStream = new BufferedReader(new FileReader(fileName));
			while(done == false) {
				String line = fileInStream.readLine();
				if(line != null) {
					StringTokenizer st = new StringTokenizer(line, " ");
					ArrayList<String> tokenList = new ArrayList<String>();
					while(st.hasMoreTokens()){
						tokenList.add(st.nextToken());
					}
					String[] tokenArr = tokenList.toArray(new String[tokenList.size()]);
					partList.add(tokenArr);
				} else {
					done = true;
					fileInStream.close();
				}
			}	
		} catch(FileNotFoundException e) {
			System.out.println("File" + fileName + "not found");
			System.out.println("Exception: " + e.getMessage());
			System.exit(-1);
		} catch(IOException e) {
			System.out.println("Error reading from file" + fileName);
			System.out.println("Exception: " + e.getMessage());
			System.exit(-1);
		}
		
		String[][] pieceArr = partList.toArray(new String[partList.size()][partList.get(0).length]);
		return pieceArr;
		
	}
}
