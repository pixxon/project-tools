package bomberman.connection.util.FunctionArgs;

import bomberman.util.Direction;

/**
 * Created by Attila on 2016. 03. 14..
 */

/**
 * Wrapper class for the movePlayer function arguments
 *
 */
public class MovePlayerArg {
    /**
     * Constructor
     * @param ID The ID of the player
     * @param direction The Direction where the player wants to go
     */
    public MovePlayerArg(int ID, Direction direction) {
        this.ID = ID;
        this.direction = direction;
    }

    /**
     * Getter
     * @return Returns the ID of the player
     */
    public int getID() {
        return ID;
    }

    /**
     * Setter
     * @param ID The if of the player
     */
    public void setID(int ID) {
        this.ID = ID;
    }

    /**
     * Getter
     * @return Returns the Direction of the player
     */
    public Direction getDirection() {
        return direction;
    }

    /**
     * Setter
     * @param direction Sets the direction of the player
     */
    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    private int ID;
    private Direction direction;
}
