package bomberman.connection.demo;

import bomberman.connection.util.FunctionArgs.NewPlayerArg;
import bomberman.util.Direction;
import bomberman.util.EventArgs.PlayerIDEventArg;
import bomberman.util.EventHandler;
import bomberman.util.IView;
import org.glassfish.tyrus.client.ClientManager;


import javax.websocket.DeploymentException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.CountDownLatch;
import bomberman.connection.ClientEndPoint;

/**
 * Created by Attila on 2016. 03. 14..
 */


public class DemoClient implements IView{
    private ClientEndPoint clientEndpoint;
    private EventHandler playerID;
    private EventHandler gameOver;
    private EventHandler gameAdvanced;
    private EventHandler gameCreated;


    /**
     * Constructor
     * Initialites the eventhandlers and client endpoint
     * Binds the handlers to the events
     */
    public DemoClient() {
        CountDownLatch latch = new CountDownLatch(1);
        clientEndpoint = new ClientEndPoint();

        playerID = new EventHandler(){
            @Override
            public void actionPerformed(Object sender, Object eventargs){
                System.out.println("playerID event arrived at the client "+((PlayerIDEventArg)eventargs).getID());
            }
        };
        gameOver = new EventHandler(){
            @Override
            public void actionPerformed(Object sender, Object eventargs){
                System.out.println("gameOver event arrived at the client");
            }
        };
        gameAdvanced = new EventHandler(){
            @Override
            public void actionPerformed(Object sender, Object eventargs){
                System.out.println("gameAdvanced event arrived at the client");
            }
        };
        gameCreated = new EventHandler(){
            @Override
            public void actionPerformed(Object sender, Object eventargs){
                System.out.println("gameCreated event arrived at the client");
            }
        };

        clientEndpoint.getGameAdvanced().addListener(getGameAdvancedHandler());
        clientEndpoint.getGameCreated().addListener(getGameCreatedHandler());
        clientEndpoint.getGameOver().addListener(getGameOverHandler());
        clientEndpoint.getPlayerID().addListener(getPlayerIDHandler());
    }

    /**
     * Connects the client to the given address and starts it
     * @param whereToConnect Needs a String host:port/room for example: "ws://localhost:3000/bomberman/game"
     */
    public void runClient(String whereToConnect) {
        ClientManager client = ClientManager.createClient();
        try {
            client.connectToServer(clientEndpoint, new URI(whereToConnect));
            //latch.await();

        } catch (DeploymentException | URISyntaxException  e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * Getter of the playerID events handler.
     *
     * @returns The event handler of playerID.
     */
    @Override
    public EventHandler getPlayerIDHandler() {
        return playerID ;
    }

    /**
     * Getter for GameOverHandler.
     *
     * @returns Handler for models GameOver event.
     */
    @Override
    public EventHandler getGameOverHandler() {
        return gameOver;
    }

    /**
     * @returns Handler for models GameAdvanced event.
     */
    @Override
    public EventHandler getGameAdvancedHandler() {
        return gameAdvanced;
    }

    /**
     * Getter for GameCreatedHandler.
     *
     * @returns Handler for models GameCreated event.
     */
    @Override
    public EventHandler getGameCreatedHandler() {
        return gameCreated;
    }

/******************************************This functions where created for the demo**********************************/
    public void newPlayer(){
        clientEndpoint.newPlayer();
    }

    public void movePlayer(){
        clientEndpoint.movePlayer(3, Direction.DOWN);
    }
    public void leavePlayer(){
        clientEndpoint.leavePlayer(3);
    }
    public void placeBomb(){
        clientEndpoint.placeBomb(3);
    }



}
