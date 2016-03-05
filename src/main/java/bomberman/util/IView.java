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
	 * Handler for models GameOver event.
	 */
	public EventHandler GameOverHandler;
	
	/**
	 * Handler for models GameAdvanced event.
	 */
	public EventHandler GameAdvancedHandler;
	
	/**
	 * Handler for models GameCreated event.
	 */
	public EventHandler GameCreatedHandler;
}