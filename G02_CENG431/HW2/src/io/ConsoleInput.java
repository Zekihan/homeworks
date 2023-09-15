package io;

import java.util.Scanner;

public class ConsoleInput implements Input{

    private Scanner sc;

    public ConsoleInput() {
        this.sc = new Scanner(System.in);
    }

    public String readString() {
        return sc.nextLine();
    }

    public void close(){
        sc.close();
    }

}
