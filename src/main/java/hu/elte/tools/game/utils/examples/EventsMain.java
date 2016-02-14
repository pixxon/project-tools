package hu.elte.tools.game.utils.examples;

import hu.elte.tools.game.utils.*;

public class EventsMain
{
	public static void main(String[] args)
	{
		Sender sender = new Sender();
		Reciever reciever = new Reciever();
		
		sender.event.addListener(reciever.eventHandler);
		sender.doSomething();
	}
}

class Sender
{
	public Event event;
	
	public Sender()
	{
		event = new Event();
	}
	
	public void doSomething()
	{
		event.notifyListeners(null);
	}
}

class Reciever
{
	public Reciever()
	{
		eventHandler = new EventHandler()
		{
			@Override
			public void actionPerformed(Object sender, Object eventargs)
			{
				System.out.println("Event arrived!");
			}
		};
	}
	
	public EventHandler eventHandler;
}