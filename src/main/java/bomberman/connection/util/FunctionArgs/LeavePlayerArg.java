package bomberman.connection.util.FunctionArgs;

/**
 * Created by Attila on 2016. 03. 14..
 */


/**
 * Wrapper class for the leavePlayer function arguments
 *
 */
public class LeavePlayerArg {
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
     * @param ID The players ID you want to kick
     */
    public LeavePlayerArg(int ID) {
        this.ID = ID;
    }
}
