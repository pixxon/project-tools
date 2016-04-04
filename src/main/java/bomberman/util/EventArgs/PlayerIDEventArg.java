package bomberman.util.EventArgs;

/**
 * Created by Attila on 2016. 03. 14..
 */

import javax.websocket.Session;

/**
 * New player event argumentum
 *
 */
public class PlayerIDEventArg {


    public int getID() {
        return ID;
    }


    private Integer ID;

    public PlayerIDEventArg(int ID) {
        this.ID = ID;
    }
    public PlayerIDEventArg(){}
    //TODO Customize for your own purposes/needs
}
