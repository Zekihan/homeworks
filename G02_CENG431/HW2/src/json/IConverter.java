package json;

import note.INote;

public interface IConverter {

    /* Returns the desired format of the given INote object.
    * @param note  INote object to be converted to the desired format.
    * @return implemented format of the INote object.
    */
    public String convertToFormat(INote note);


    /* Returns an INote object from the given format.
     * @param note: String format of an INote object to be converted.
     * @return INote object from the given format.
     */
    public INote toNote(String note);

}
