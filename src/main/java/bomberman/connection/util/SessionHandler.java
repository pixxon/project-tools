package bomberman.connection.util;

import javax.enterprise.context.ApplicationScoped;
import javax.websocket.Session;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Attila on 2016. 03. 13..
 */

/**
 * Class in order to make easier the session handling
 */
@ApplicationScoped
public class SessionHandler {


    /**
     *Container for sessions
     */
    private static Set<Session> sessions = new HashSet<>();

    /**
     * Add a session to the session container
     * @param session The session you would like to add
     */
    public void addSession(Session session) {
        sessions.add(session);
    }

    /**
     * Remove a session from the container
     * @param session The session you would like to remove
     */
    public void removeSession(Session session) {
        sessions.remove(session);
    }

    /**
     * Send the string message for all connected sessions exept one
     * @param exeptSession Session which will not receive the message, the exept session
     * @param message The message you would like to send
     */
    public void sendToAllConnectedSessionsExeptOne(Session exeptSession,String message) {
        for (Session session : sessions) {
            if(!session.equals(exeptSession))sendToSession(session, message);
        }
    }

    /**
     * Send a string message for all connected sessions
     * @param message The message you would like to send
     */
    public void sendToAllConnectedSessions(String message) {
        for (Session session : sessions) {
            sendToSession(session, message);
        }
    }

    /**
     * Send a string message for a session
     * @param session The session which will receive the message
     * @param message The message you would like to send for that @session
     */
    public void sendToOneSession(Session session,String message){
        sendToSession(session,message);
    }

    /**
     * Helper function: Sends a message for a session, if the session is not accesssable then will remove from the sessions container
     * @param session The session which will receive the information
     * @param message The message you would like to send
     */
    private void sendToSession(Session session, String message) {
        try {
            session.getBasicRemote().sendText(message);
        } catch (Exception ex) {
            removeSession(session);
        }
    }
}
