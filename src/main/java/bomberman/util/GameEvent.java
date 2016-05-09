package bomberman.util;

import java.util.List;
import java.util.LinkedList;

import bomberman.util.GameEventHandler;

/**
 * A simple class to help handling any kind of event.
 */
public class GameEvent
{
	/**
	 * List of all connected handlers
	 *
	 */
	private List<GameEventHandler> listeners;
	
	/**
	 * Constructor for Event.
	 *
	 */
	public GameEvent()
	{
		listeners = new LinkedList<GameEventHandler>();
	}
	
	/**
	 * Connect a new handler for the event.
	 *
	 * @param listener the new handler
	 */
	public void addListener(GameEventHandler listener)
	{
		listeners.add(listener);
	}
	
	/**
	 * Remove a connected handler.
	 *
	 * @param listener the handler to be removed.
	 */
	public void removeListener(GameEventHandler listener)
	{
		listeners.remove(listener);
	}
	
	/**
	 * Sends signal to all connected handlers.
	 *
	 * @param eventargs the arguments for the event
	 */
	public void notifyListeners(Object eventargs)
	{
		for(GameEventHandler listener : listeners)
		{
			listener.actionPerformed(this, eventargs);
		}
	}
}
