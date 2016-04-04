package bomberman.connection.demo;

import bomberman.util.Direction;
import bomberman.util.Event;
import bomberman.util.EventArgs.GameAdvancedEventArg;
import bomberman.util.EventArgs.GameCreatedEventArg;
import bomberman.util.EventArgs.GameOverEventArg;
import bomberman.util.EventArgs.PlayerIDEventArg;
import bomberman.util.IModel;

import javax.websocket.Session;

/**
 * Created by Attila on 2016. 03. 14..
 */
public class DemoModel implements IModel {
    private Event GameAdvanced;
    private Event GameCreated;
    private Event PlayerId;
    private Event GameOver;
    private static int ID=0;
    private static int connectedClienst=0;

    public DemoModel(){
        GameAdvanced = new Event();
        GameCreated = new Event();
        GameOver = new Event();
        PlayerId = new Event();
    }
    public int getNextID(){
        ID++;
        return ID;
    }

    /**
     * Adds a new player to the game
     *
     * @returns the identifier of the player
     */
    @Override
    public void newPlayer() {
        System.out.println("Request for newPlayer has been arrived!");
        PlayerIDEventArg playerIDEventArg = new PlayerIDEventArg(getNextID());
        PlayerId.notifyListeners(playerIDEventArg);
        connectedClienst++;

        if(connectedClienst==2){
            GameCreatedEventArg gameCreatedEventArg= new GameCreatedEventArg();
            GameCreated.notifyListeners(gameCreatedEventArg);
            GameOverEventArg gameOverEventArg=new GameOverEventArg();
            GameOver.notifyListeners(gameOverEventArg);
            connectedClienst=0;
        }
    }


    /**
     * Disconnects player from the game
     *
     * @param ID the identifier of the player
     */
    @Override
    public void leavePlayer(int ID) {
        System.out.println("Request for kick player has been arrived!Player ID: "+ID);
    }
    /**
     * Called upon player movement
     *
     * @param ID the identifier of the player
     * @param dir the direction of move
     */
    @Override
    public void movePlayer(int ID, Direction dir) {
        System.out.println("Request for move player has been arrived!Player ID: "+ID+"Direction: "+dir);
        GameAdvancedEventArg gameAdvancedEventArg=new GameAdvancedEventArg();
        GameAdvanced.notifyListeners(gameAdvancedEventArg);

    }
    /**
     * Placing a bomb where the player stands
     *
     * @param ID the identifier of the player
     */
    @Override
    public void placeBomb(int ID) {
        System.out.println("Request for place bomb has been arrived!Player ID is: "+ID);
    }

    /**
     * Getter for PlayerIDEventArg event
     *
     * @returns the event which stores the playerID
     */
    @Override
    public Event getPlayerID() {
        return PlayerId;
    }

    /**
     * Getter for GameCreated event
     *
     * @returns The event which gets invoked when game ends
     */
    @Override
    public Event getGameCreated() {
        return GameCreated;
    }

    /**
     * Getter for GameAdvanced event*
     *
     * @returns The event which gets invoked when game ends
     */
    @Override
    public Event getGameAdvanced() {
        return GameAdvanced;
    }

    /**
     * Getter for GameOver event
     *
     * @returns The event which gets invoked when game ends
     */
    @Override
    public Event getGameOver() {
        return GameOver;
    }
}
