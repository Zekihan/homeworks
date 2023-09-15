package tube;

public interface IFormatter<T>{

    /* Converts the given object of type T to a format string.
    * @param object     Object of type T to be formatted.
    * @return           Desired format as String
    */
    public String toFormat(T object);

    /* Converts the given format String back to oject of type T
    * @param formattedObject    String that contains the formatted object
    * return                    Desired object from formatted String
    */
    public T toObject(String formattedObject);
}
