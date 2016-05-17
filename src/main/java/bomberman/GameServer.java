package bomberman;

import bomberman.connection.ServerEndPoint;
import org.glassfish.tyrus.server.Server;

public class GameServer{
    private Server server;

    public void runServer(int port) {
        server = new Server("localhost", port, "/bomberman",ServerEndPoint.class);

        try {
            server.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stopServer(){
        server.stop();
    }
}
