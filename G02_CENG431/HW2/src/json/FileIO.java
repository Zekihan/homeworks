package json;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class FileIO{

    private String filePath;

    public FileIO(String filePath) {
        setFilePath(filePath);
    }

    //Write the given string to the file
    public boolean save(String content){
        boolean saved = false;

        try {
            FileWriter writer = new FileWriter(filePath);
            writer.write(content);
            writer.close();
            saved = true;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return saved;
    }

    //Read string from the file
    public String load(){
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

    public Boolean checkSave(){
        File f = new File(filePath);
        return f.exists() && !f.isDirectory();
    }

    public Boolean deleteSave(){
        File f = new File(filePath);
        return f.delete();
    }

    public void setFilePath(String filePath){
        if(filePath == null || filePath == ""){
            throw new IllegalArgumentException("File path cannot be empty or null.");
        }
        this.filePath = filePath;
    }

}
