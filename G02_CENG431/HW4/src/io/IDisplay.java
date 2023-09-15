package io;

public interface IDisplay {

    /* Displays the given menu text and a request message.
    * @param1 menuText  Menu's available operations as String.
    * @param2 requestMessage    Request (from the menu) message as String.
    */
    void displayMenu(String menuText, String requestMessage);

    /* Displays the loadouts of players.
     * @param1 player1Loadout   Player1's warcrafts and their added parts as String.
     * @param2 player2Loadout   Player2's warcrafts and their added parts as String.
     */
    void displayLoadouts(String player1Loadout, String player2Loadout);


    /* Displays a warning with the given text, to the screen.
     * @param1 warningText  Warning message to be displayed.
     */
    void displayWarning(String warningText);

    /* Displays the scores of players.
     * @param1 player1Score     Score of player1 as int.
     * @param2 player2Score     Score of player2 as int.
     */
    void displayGameReport(int player1Score, int player2Score);


    /* Displays the given error message to the screen.
     * @param1 errorMessage     Error message to be displayed. */
    void displayErrorMessage(String errorMessage);
}
