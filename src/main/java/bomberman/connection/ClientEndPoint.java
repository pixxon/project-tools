package bomberman.connection;

import bomberman.connection.util.DecodeException;
import bomberman.connection.util.EncodeException;
import bomberman.connection.util.FunctionArgs.NewPlayerArg;
import bomberman.connection.util.FunctionArgs.LeavePlayerArg;
import bomberman.connection.util.FunctionArgs.MovePlayerArg;
import bomberman.connection.util.FunctionArgs.PlaceBombArg;
import bomberman.connection.util.MessageWrapper;
import bomberman.util.Direction;
import bomberman.util.GameEvent;
import bomberman.util.IModel;
import com.google.gson.Gson;

import javax.websocket.*;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * Created by Attila on 2016. 03. 14..
 */

/**
 * ClientEndPoint implements the IModel interface
 * Implements the websocket annotations
 */
@ClientEndpoint
public class ClientEndPoint implements IModel{
    private Session server;

    private static CountDownLatch latch;


    private GameEvent GameAdvanced;
    private GameEvent GameCreated;
    private GameEvent PlayerId;
    private GameEvent GameOver;

/********************************** Constructor *********************************************************/


    /**
     * Contructor
     * Initializes the GameEvents
     */
    public ClientEndPoint() {
        GameOver = new GameEvent();
        GameAdvanced = new GameEvent();
        GameCreated = new GameEvent();
        PlayerId = new GameEvent();
        latch = new CountDownLatch(1);
    }


/********************************** Socket server override functions *********************************************************/

    /**
     * Listening for a new connection
     * Saves the server
     * @param session The connected session
     */
    @OnOpen
    public void OnOpen(Session session){
        server = session;
    }

    /**
     * Listens for the new messages
     * Decodes the new messaeges
     * Raises the suitable GameEvent with the argument
     * @param message The received message
     * @param session The sender session/should be the server
     */
    @OnMessage
    public void OnMessage(String message,Session session){
        Gson gson = new Gson();
        MessageWrapper messageWrapper = gson.fromJson(message,MessageWrapper.class);
        try{

            Object messageObject = gson.fromJson(messageWrapper.getObject(),Class.forName(messageWrapper.getName()));
            if(Class.forName(messageWrapper.getName()).getSimpleName().equals("PlayerIDEventArg")){
                PlayerId.notifyListeners(messageObject);
            }else if(Class.forName(messageWrapper.getName()).getSimpleName().equals("GameAdvancedEventArg")){
                GameAdvanced.notifyListeners(messageObject);
            }else if(Class.forName(messageWrapper.getName()).getSimpleName().equals("GameCreatedEventArg")){
                GameCreated.notifyListeners(messageObject);
            }else if(Class.forName(messageWrapper.getName()).getSimpleName().equals("GameOverEventArg")){
                GameOver.notifyListeners(messageObject);
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Countdowns the latch
     * @param session The disconnected session
     * @param closeReason The close reason
     */
    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        latch.countDown();
    }

/********************************** Helper function ********************************************************/

    /**
     * Converts the GameEvent argument to Json string
     * @param GameEventargs The GameEvents argument
     * @return The converted string, which will be send to the clients
     */
    private String objectToJsonString(Object Eventargs)throws EncodeException{
        String message = null;
        Gson gson = new Gson();
        String objectString = null;

        try{
            objectString = gson.toJson(Eventargs);
            MessageWrapper messageWrapper = new MessageWrapper(Eventargs.getClass().getCanonicalName(),objectString);
            message = gson.toJson(messageWrapper);
        }catch (Exception e)
        {
            throw new EncodeException("Didn't manage to encode class  " + Eventargs.getClass().getName());
        }


        return message;
    }

/********************************** Override methods from the IModel *********************************************************/

    /**
     * Adds a new player to the game
     *
     */
    @Override
    public void newPlayer() {
        NewPlayerArg newPlayerArg = new NewPlayerArg();
        try {
            server.getBasicRemote().sendObject(objectToJsonString(newPlayerArg));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Disconnects player from the game
     */
    @Override
    public void leavePlayer(int ID) {
        LeavePlayerArg leavePlayerArg = new LeavePlayerArg(ID);
        try {

            server.getBasicRemote().sendObject(objectToJsonString(leavePlayerArg));
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Called upon player movement
     *
     * @param ID the identifier of the player
     * @param dir the direction of move
     */
    @Override
    public void movePlayer(int ID, Direction dir) {
        MovePlayerArg movePlayerArg = new MovePlayerArg(ID,dir);
        try {
            server.getBasicRemote().sendObject(objectToJsonString(movePlayerArg));
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    /**
     * Placing a bomb where the player stands
     *
     * @param ID the identifier of the player
     */
    @Override
    public void placeBomb(int ID) {
        PlaceBombArg placeBombArg = new PlaceBombArg(ID);
        try {
            server.getBasicRemote().sendObject(objectToJsonString(placeBombArg));
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Getter for PlayerID GameEvent
     *
     * @returns the GameEvent which stores the playerID
     */
    @Override
    public GameEvent getPlayerID() {
        return PlayerId;
    }

    /**
     * Getter for GameCreated GameEvent
     *
     * @returns The GameEvent which gets invoked when game ends
     */
    @Override
    public GameEvent getGameCreated() {
        return GameCreated;
    }

    /**
     * Getter for GameAdvanced GameEvent
     *
     * @returns The GameEvent which gets invoked when game ends
     */
    @Override
    public GameEvent getGameAdvanced() {
        return GameAdvanced;
    }

    /**
     * Getter for GameOver GameEvent
     *
     * @returns The GameEvent which gets invoked when game ends
     */
    @Override
    public GameEvent getGameOver() {
        return GameOver;
    }


}
