package bomberman.util;
 
import bomberman.util.Event;
import bomberman.util.Direction;
 
/**
 * Abstract class for easier communication between modules
 */
public abstract class IModel
{
	/**
	 * Invoked when game ended or player died
	 */
	public Event GameOver;
	
	/**
	 * Invoked when game advanced
	 */
	public Event GameAdvanced;
	
	/**
	 * Invoked when new game created
	 */
	public Event GameCreated;
	
	
	/**
	 * Adds a new player to the game
	 *
	 * @returns the identifier of the player
	 */
	abstract public int newPlayer();
	
	/**
	 * Disconnects player from the game
	 *
	 * @param ID the identifier of the player
	 */
	 abstract public int leavePlayer(int ID);
	
	/**
	 * Called upon player movement
	 *
	 * @param ID the identifier of the player
	 * @param dir the direction of move
	 */
	abstract public void movePlayer(int ID, Direction dir);
	
	/**
	 * Placing a bomb where the player stands
	 *
	 * @param ID the identifier of the player
	 */
	abstract public void placeBomb(int ID);
}