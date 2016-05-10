package bomberman;

import bomberman.view.View;
import org.glassfish.tyrus.client.ClientManager;


import javax.websocket.DeploymentException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.CountDownLatch;
import bomberman.connection.ClientEndPoint;

/**
 * Created by Attila on 2016. 03. 14..
 */


public class GameClient{
    private ClientEndPoint clientEndpoint;
	private View view;


    /**
     * Constructor
     * Initialites the eventhandlers and client endpoint
     * Binds the handlers to the events
     */
    public DemoClient() {
        CountDownLatch latch = new CountDownLatch(1);
        clientEndpoint = new ClientEndPoint();
	view = new View();

        clientEndpoint.getGameAdvanced().addListener(view.getGameAdvancedHandler());
        clientEndpoint.getGameCreated().addListener(view.getGameCreatedHandler());
        clientEndpoint.getGameOver().addListener(view.getGameOverHandler());
        clientEndpoint.getPlayerID().addListener(view.getPlayerIDHandler());
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
}
