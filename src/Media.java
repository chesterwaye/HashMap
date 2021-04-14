/**
 * Class: Media
 *
 * @author Chester Waye
 * @version 1.0 * Course : ITEC 3150, Spring 2021 Written: 4/13/2021
 * Written: January 18, 2012
 * <p>
 * <p>
 * This class –now describe what the class does
 * <p>
 * Purpose: –Describe the purpose of this class
 */
public class Media {

    private String idNumber;
    private String itemName;
    private String type;

    /**
     * Method:Media ()
     *
     * Constructor method that accepts values for all the attributes and sets
     * them.
     *
     * @param idNumber
     * @param itemName
     * @param type
     */
    public Media(String idNumber, String itemName, String type)
    {
        this.idNumber = idNumber;
        this.itemName = itemName;
        this.type = type;
    }

    /**
     * Method:getIdNumber() Getter method for the idNumber attribute
     *
     * @return the idNumber
     */
    public String getIdNumber()
    {
        return idNumber;
    }

    /**
     * Method:getItemName() Getter method for the itemName attribute
     *
     * @return the itemName
     */
    public String getItemName()
    {
        return itemName;
    }

    /*
     * Method:toString() Converts the attributes of the class to a text readable
     * format.
     *
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return "Media [idNumber=" + idNumber + ", itemName=" + itemName + ", type=" + type + "]";
    }

    /**
     * Method:getItemName() Getter method for the itemName attribute
     *
     * @return the type
     */
    public String getType()
    {
        return type;
    }
}
