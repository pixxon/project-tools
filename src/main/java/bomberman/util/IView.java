package bomberman.util;
 
import bomberman.util.EventHandler;
import bomberman.util.IModel;
 
/**
 * Abstract class for easier communication between modules
 */
public abstract class IView
{
	/**
	 * The connected model.
	 */
	protected IModel model;
	
	/**
	 * Constructor to connect the model
	 *
	 * @param model The reference of the model
	 */
	public IView(IModel model)
	{
		this.model = model;
	}
	
	/**
	 * Invoked when game ended
	 */
	public EventHandler GameOverHandler;
	
	/**
	 * Invoked when game advanced
	 */
	public EventHandler GameAdvancedHandler;
	
	/**
	 * Invoked when new game created
	 */
	public EventHandler GameCreatedHandler;
}