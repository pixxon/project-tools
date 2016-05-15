package bomberman.util;
 
import bomberman.util.GameEvent;
import bomberman.util.Direction;
 
/**
 * Interface for easier communication between modules
 */
public interface IModel
{	
	/**
	 * Adds a new player to the game
	 *
	 * @returns the identifier of the player
	 */
	public void newPlayer();
	
	/**
	 * Disconnects player from the game
	 *
	 * @param ID the identifier of the player
	 */
	 public void leavePlayer(int ID);
	
	/**
	 * Called upon player movement
	 *
	 * @param ID the identifier of the player
	 * @param dir the direction of move
	 */
	public void movePlayer(int ID, Direction dir);
	
	/**
	 * Placing a bomb where the player stands
	 *
	 * @param ID the identifier of the player
	 */
	public void placeBomb(int ID);
	
	
	/**
	 * Getter for PlayerIDEventArg event
	 *
	 * @returns the event which stores the playerID
	 */
	public GameEvent getPlayerID();
	
	/**
	 * Getter for GameCreated event
	 *
	 * @returns The event which gets invoked when game ends
	 */
	public GameEvent getGameCreated();
	
	/**
	 * Getter for GameAdvanced event*
	 *
	 * @returns The event which gets invoked when game ends
	 */
	public GameEvent getGameAdvanced();
	
	/**
	 * Getter for GameOver event
	 *
	 * @returns The event which gets invoked when game ends
	 */
	public GameEvent getGameOver();
}
