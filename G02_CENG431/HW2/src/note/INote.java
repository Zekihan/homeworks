package note;

public interface INote {

    /* Displays the title of the INote object.*/
    public void displayTitle();

    /* Displays the content of the INote object.*/
    public void displayContent();

    /* Returns the id of the INote object.
    * @return: Id of the callee INote object as int.*/
    public int getId();

    /* Displays the title of the INote object.
    * @return: Title of the callee INote object as String.*/
    public String getTitle();

}
