package bomberman.util;
 
import bomberman.util.EventHandler;
import bomberman.util.IModel;
 
/**
 * Interface for easier communication between modules.
 */
public interface IView
{	
	/**
	 * Getter of the playerID events handler.
	 *
	 * @returns The event handler of playerID.
	 */
	public GameEventHandler getPlayerIDHandler();

	
	/**
	 * Getter for GameOverHandler.
	 *
	 * @returns Handler for models GameOver event.
	 */
	public GameEventHandler getGameOverHandler();
	
	/**
	 * @returns Handler for models GameAdvanced event.
	 */
	public GameEventHandler getGameAdvancedHandler();
	
	/**
	 * Getter for GameCreatedHandler.
	 *
	 * @returns Handler for models GameCreated event.
	 */
	public GameEventHandler getGameCreatedHandler();
}