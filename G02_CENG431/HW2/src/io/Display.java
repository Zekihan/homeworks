package io;

public interface Display {

    /* Displays the notes given content along with its title, date and state.
    * @param1 title     Title of the note as String
    * @param2 content   Content of the note as String
    * @param3 date      Date of the note as String
    * @param4 state     State of the note as String.*/
    public void displayNote(String title, String Content, String date, String state);

    /* Displays the title and the id of an INote.
    * @param1 id      Identifier of the INote object.
    * @param2 title   Title of the INote object. */
    public void displayTitle(int id, String title);

    /* Displays the given message.
    * @param message:   message String to be displayed. */
    public void displayMessage(String message);

}
