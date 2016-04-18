package bomberman.connection.util.FunctionArgs;

/**
 * Created by Attila on 2016. 03. 14..
 */


/**
 * Wrapper class for the leavePlayer function arguments
 *
 */
public class PlaceBombArg {
    /**
     * Getter
     * @return Returns the players ID
     */
    public int getID() {
        return ID;
    }


    /**
     * Setter
     * @param ID The players ID
     */
    public void setID(int ID) {
        this.ID = ID;
    }

    private int ID;

    /**
     * Constructor
     * @param ID The players ID who placed the bomb
     */
    public PlaceBombArg(int ID) {
        this.ID = ID;
    }
}
