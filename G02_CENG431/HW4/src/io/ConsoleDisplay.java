package io;

public class ConsoleDisplay implements IDisplay {

    @Override
    public void displayMenu(String menuText, String requestMessage) {

        System.out.println("\n");
        String barrierText = "**********************************";
        System.out.println(barrierText);
        System.out.println(menuText);
        System.out.println(barrierText);
        System.out.println("\n");
        System.out.print(requestMessage);
    }


    @Override
    public void displayLoadouts(String player1Loadout, String player2Loadout) {

        System.out.println("-----------------");
        System.out.println("Player1's loadout");
        System.out.println("-----------------");

        System.out.println(player1Loadout + "\n");

        System.out.println("-----------------");
        System.out.println("Player2's loadout");
        System.out.println("-----------------");

        System.out.println(player2Loadout);


    }

    @Override
    public void displayWarning(String warningText) {
        System.out.println("\n\n********************************");
        System.out.println(warningText + "!");
        System.out.println("********************************\n\n");
    }


    @Override
    public void displayGameReport(int player1Score, int player2Score) {
        System.out.println("\n\n*******************");
        System.out.println("Simulation Results");
        System.out.println("*******************");
        System.out.println("Player1's score: " + player1Score + " points");
        System.out.println("Player2's score: " + player2Score + " points");


        if (player1Score > player2Score) {

            System.out.println("Player1 wins by " + (player1Score - player2Score) + " points");

        } else if (player1Score < player2Score) {

            System.out.println("Player2 wins by " + (player2Score - player1Score) + " points");

        } else {

            System.out.println("Draw!!");
        }
    }

    public void displayErrorMessage(String message) {

        System.err.print(message);
    }
}
