package hu.elte.tools.game.utils;

import java.util.List;
import java.util.LinkedList;

import hu.elte.tools.game.utils.EventHandler;

/**
 * A simple class to help handling any kind of event.
 */
public class Event
{
	/**
	 * List of all connected handlers
	 *
	 */
	private List<EventHandler> listeners;
	
	/**
	 * Constructor for Event.
	 *
	 */
	public Event()
	{
		listeners = new LinkedList<EventHandler>();
	}
	
	/**
	 * Connect a new handler for the event.
	 *
	 * @param listener the new handler
	 */
	public void addListener(EventHandler listener)
	{
		listeners.add(listener);
	}
	
	/**
	 * Remove a connected handler.
	 *
	 * @param listener the handler to be removed.
	 */
	public void removeListener(EventHandler listener)
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
		for(EventHandler listener : listeners)
		{
			listener.actionPerformed(this, eventargs);
		}
	}
}