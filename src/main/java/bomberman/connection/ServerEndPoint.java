package bomberman.connection;

import bomberman.connection.util.*;
import bomberman.connection.util.EncodeException;
import bomberman.connection.util.FunctionArgs.LeavePlayerArg;
import bomberman.connection.util.FunctionArgs.MovePlayerArg;
import bomberman.connection.util.FunctionArgs.PlaceBombArg;
import bomberman.util.GameEventHandler;
import bomberman.util.IModel;
import bomberman.util.IView;
import bomberman.model.Model;
import com.google.gson.Gson;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.Stack;

/**
 * Created by Attila on 2016. 03. 13..
 */

/**
 * ServerEndPoint extends the abstract IView class
 * Implements the websocket annotations
 */
@ServerEndpoint(value = "/game")
public class ServerEndPoint implements IView  {

    private SessionHandler sessionHandler = new SessionHandler();

    private IModel model;

    private GameEventHandler playerID;
    private GameEventHandler gameOver;
    private GameEventHandler gameAdvanced;
    private GameEventHandler gameCreated;

    private Stack<Session> stack;

/********************************** Constructor *********************************************************/


    /**
     * Constructor
     * Defines the eventHanglers, and connect them to the model
     */
    public ServerEndPoint() {
        model = new Model();
        stack = new Stack<>();

        /**
         * Handler for models GameOver event.
         */
        gameOver = new GameEventHandler(){
            /**
             * Converts the eventargs into string and sends to the sessions
             * @param sender the object, who created the event.
             * @param eventargs the arguments of the event.
             */
            @Override
            public void actionPerformed(Object sender, Object eventargs)
            {
                try {
                    sessionHandler.sendToAllConnectedSessions(objectToJsonString(eventargs));
                } catch (EncodeException e) {
                    e.printStackTrace();
                }
            }
        };
        /**
         * Handler for models GameAdvanced event.
         */
        gameAdvanced = new GameEventHandler(){
            /**
             * Converts the eventargs into string and sends to the sessions
             * @param sender the object, who created the event.
             * @param eventargs the arguments of the event.
             */
            @Override
            public void actionPerformed(Object sender, Object eventargs)
            {
                try {
                    sessionHandler.sendToAllConnectedSessions(objectToJsonString(eventargs));
                } catch (EncodeException e) {
                    e.printStackTrace();
                }
            }
        };
        /**
         * Handler for models GameCreated event.
         */
        gameCreated = new GameEventHandler(){
            /**
             * Converts the eventargs into string and sends to the sessions
             * @param sender the object, who created the event.
             * @param eventargs the arguments of the event.
             */
            @Override
            public void actionPerformed(Object sender, Object eventargs)
            {
                try {
                    sessionHandler.sendToAllConnectedSessions(objectToJsonString(eventargs));
                } catch (EncodeException e) {
                    e.printStackTrace();
                }
            }
        };
        /**
         * Handler for models PlayerID event.
         */
        playerID = new GameEventHandler(){
            @Override
            public void actionPerformed(Object sender, Object eventargs) {
                try {
                    sessionHandler.sendToOneSession(stack.pop(),objectToJsonString(eventargs));
                } catch (EncodeException e) {
                    e.printStackTrace();
                }
            }
        };
        /**
         * Binding the eventListeners to the models events
         */
        model.getGameAdvanced().addListener(getGameAdvancedHandler());
        model.getGameCreated().addListener(getGameCreatedHandler());
        model.getGameOver().addListener(getGameOverHandler());
        model.getPlayerID().addListener(getPlayerIDHandler());
    }




/********************************** Socket server override functions *********************************************************/

    /**
     * Listening for a new connection
     * Adds the new connection to the sessionlist
     * Calls the models newPlayer function
     * @param session The connected session
     */
    @OnOpen
    public void onOpen(Session session) {
        sessionHandler.addSession(session);
    }

    /**
     * Listens for the new messages
     * Decodes the new messaeges
     * Calls the suitable function of the model
     * @param message The received message
     * @param session The sender session
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        Gson gson = new Gson();
        MessageWrapper messageWrapper = gson.fromJson(message,MessageWrapper.class);
        try{

            Object messageObject = gson.fromJson(messageWrapper.getObject(),Class.forName(messageWrapper.getName()));
            if(Class.forName(messageWrapper.getName()).getSimpleName().equals("NewPlayerArg")){
                stack.push(session);
                model.newPlayer();
            }else if(Class.forName(messageWrapper.getName()).getSimpleName().equals("LeavePlayerArg")){
                LeavePlayerArg leavePlayerArg = (LeavePlayerArg)messageObject;
                model.leavePlayer(leavePlayerArg.getID());
            }else if(Class.forName(messageWrapper.getName()).getSimpleName().equals("MovePlayerArg")){
                MovePlayerArg movePlayerArg = (MovePlayerArg)messageObject;
                model.movePlayer(movePlayerArg.getID(),movePlayerArg.getDirection());
            }else if(Class.forName(messageWrapper.getName()).getSimpleName().equals("PlaceBombArg")){
                PlaceBombArg placeBombArg = (PlaceBombArg)messageObject;
                model.placeBomb(placeBombArg.getID());
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @OnError
    public void onError(Session session, Throwable t) {
        t.printStackTrace();
    }


    /**
     * Removes the disconnected session
     * @param session The disconnected session
     * @param closeReason The close reason
     */
    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        sessionHandler.removeSession(session);
    }

/******************************************Helper function*****************************************************************/

    /**
     * Converts the event argument to Json string
     * @param eventargs The events argument
     * @return The converted string, which will be send to the clients
     */
    private String objectToJsonString(Object eventargs)throws EncodeException{
        String message = null;
        String objectString = null;
        Gson gson = new Gson();

        try{
            objectString = gson.toJson(eventargs);
            MessageWrapper messageWrapper = new MessageWrapper(eventargs.getClass().getCanonicalName(),objectString);
            message = gson.toJson(messageWrapper);
        }catch (Exception e)
        {
            throw new EncodeException("Didn't manage to encode class " + eventargs.getClass().getName());
        }


        return message;
    }

/**************************** Override methods from the IView *********************************************************/

    /**
     * Getter of the playerID events handler.
     *
     * @returns The event handler of playerID.
     */
    @Override
    public GameEventHandler getPlayerIDHandler() {
        return playerID;
    }

    /**
     * Getter for GameOverHandler.
     *
     * @returns Handler for models GameOver event.
     */
    @Override
    public GameEventHandler getGameOverHandler() {
        return gameOver;
    }

    /**
     * @returns Handler for models GameAdvanced event.
     */
    @Override
    public GameEventHandler getGameAdvancedHandler() {
        return gameAdvanced;
    }

    /**
     * Getter for GameCreatedHandler.
     *
     * @returns Handler for models GameCreated event.
     */
    @Override
    public GameEventHandler getGameCreatedHandler() {
        return gameCreated;
    }
}
