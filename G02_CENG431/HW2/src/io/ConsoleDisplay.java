package io;

public class ConsoleDisplay implements Display {


    public ConsoleDisplay(){}

    public void displayNote(String title, String content, String date, String state){
        if(title == null || content == null || date == null || state == null){
            throw new IllegalArgumentException("Neither of the display arguments can be null.");
        }
        String note =  "Title: " + title +"\n"+ "Content: " + content +"\n"+ "Date: " + date +"\n"+ "State: " + state;
        System.out.println(note);
    }

    public void displayTitle(int id, String title){
        if(title == null){
            throw new IllegalArgumentException("Title argument cannot be null.");
        }
        String noteItem = "(" + id + ") " + title;
        System.out.println(noteItem);
    }

    public void displayMessage(String message){
        if(message == null){
            throw new IllegalArgumentException("Message argument cannot be null.");
        }
        System.out.println(message);
    }

}

