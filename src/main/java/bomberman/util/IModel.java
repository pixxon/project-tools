package bomberman.util;
 
import bomberman.util.Event;
 
/**
 * Abstract class for easier communication between modules
 */
public abstract class IModel
{
	/**
	 * Invoked when game ended
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
	 * Called when a new game is started
	 *
	 * @param size size of the new game
	 */
	abstract public void newGame(int size);
	
	/**
	 * Adds a new player to the game
	 *
	 * @param ID the identifier of the player
	 */
	abstract public void newPlayer(int ID);
	
	/**
	 * Called upon player movement
	 *
	 * @param ID the identifier of the player
	 * @param dir the direction of move
	 */
	abstract public void movePlayer(int ID, int dir);
	
	/**
	 * Placing a bomb at the given coordinates
	 *
	 * @param x first coordinate of the bomb
	 * @param y second coordinate of the bomb
	 */
	abstract public void placeBomb(int x, int y);
}