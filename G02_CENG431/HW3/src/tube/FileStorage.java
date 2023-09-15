package tube;

import java.io.*;
import java.util.Scanner;

public class FileStorage implements IStorage{

    String filePath;

    public FileStorage(String filePath) { this.filePath = filePath; }

    //Save string to the file
    public void save(String data){
        File file = new File(filePath);
        FileWriter fr = null;
        BufferedWriter br = null;
        String dataWithNewLine=data+System.getProperty("line.separator");
        try{
            fr = new FileWriter(file);
            br = new BufferedWriter(fr);
            br.write(dataWithNewLine);

        } catch (IOException e) {
            e.printStackTrace();

        }finally{
            try {
                if(br != null){
                    br.close();
                    fr.close();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //Read string from the file
    public String read(){
        StringBuilder content = new StringBuilder();

        try {
            File f = new File(filePath);
            Scanner scanner = new Scanner(f);
            while (scanner.hasNextLine()) {
                content.append(scanner.nextLine());
            }
            scanner.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return content.toString();
    }

    //Add string at the end of the file
    public void append(String data){
        File file = new File(filePath);
        FileWriter fr = null;
        BufferedWriter br = null;
        String dataWithNewLine=data+System.getProperty("line.separator");
        try{
            fr = new FileWriter(file, true);
            br = new BufferedWriter(fr);
            br.write(dataWithNewLine);

        } catch (IOException e) {
            e.printStackTrace();

        }finally{
            try {
                if(br != null){
                    br.close();
                    fr.close();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
