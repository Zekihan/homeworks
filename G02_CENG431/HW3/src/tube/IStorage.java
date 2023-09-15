package tube;

public interface IStorage {

    /* Saves the given data.
    * @param data   Data to be saved in String.
    */
    public void save(String data);

    /* Reads all the data
    * @return       Read data
    */
    public String read();


    /* Adds the given data (Not overwrite).
    * @param    Data to be saved in String
    */
    public void append(String data);

}
