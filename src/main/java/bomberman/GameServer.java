package bomberman;

import bomberman.connection.ServerEndPoint;
import org.glassfish.tyrus.server.Server;

/**
 * GameServer class creates a server where GameClients can connect.
 *
 * <p>
 *     Starts and stops the server with the given methods.
 *     Incoming messages are transmitted to the {@link Model}.
 * </p>
 */
public class GameServer{
    private Server server;

    /**
     * Starts up a server with the given parameters.
     *
     * <p>
     *      Registers a socket on the given ip with the given port.
     *      Using the {@link bomberman.connection.ServerEndPoint} class connects to a {@link bomberman.model.Model}.
     * </p>
     *
     * @param ip The IP address of the server.
     * @param port The port of the socket.
     */
    public void runServer(String ip, int port) {
        server = new Server(ip, port, "/bomberman",ServerEndPoint.class);

        try {
            server.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Stops the server.
     *
     * <p>
     *     Shuts down the server and cleans up everything.
     * </p>
     */
    public void stopServer(){
        server.stop();
    }
}
