package bomberman.model;
import bomberman.util.*;
import bomberman.persistance.*;
import bomberman.util.EventArgs.*;
import java.util.*;

public class Model implements IModel{
	
	//region PRIVATE_FIELDS
	private Table gameTable=null;
	private HashSet<Bomb> placedBombs = null;
	private HashMap<Player, Boolean> hasPlayerPlacedBomb = null;
	private ArrayList<Player> players = null;
	
	private enum Field{
		WALL, EMPTY, PLAYER, OBSTACLE, BOMB;
		private Field(){}
	};
	
	private final int BOMB_RANGE = 2;
	
	private class BombTimeOutEventArgs extends GameAdvancedEventArg{
		private Bomb b;
		private Player p;
		public BombTimeOutEventArgs(Player p, Bomb b)
		{
			this.p=p;
			this.b=b;
		}
		public Bomb getBomb(){
			return b;
		}
		public Player getPlayer(){
			return p;
		}
	};
	
	//endregion
	
	//region CONSTRUCTORS
	public Model(){
		gameTable = new Table();
		Initialize();
	}
	
	public Model(Table t){
		gameTable = t;
		Initialize();
	}
	//endregion

	//region PUBLIC_EVENT_GETTERS
	
	@Override
	public event getGameAdvanced(){
		return gameAdvanced;
	}
	
	@Override
	public Event getGameOver(){
		return gameOver;
	}
	
	@Override
	public Event getGameCreated(){
		return gameCreated;
	}
	
	@Override
	public Event getPlayerID(){
		return playerIdEvent;
	}
	
	//endregion
	
	//region PRIVATE_EVENTS
	

	
	private Event gameAdvanced;
	private Event gameOver;
	private Event gameCreated;
	private Event playerIdEvent;
	private Event bombTimeOutEvent;

	private EventHandler bombTimeOutHandler = new EventHandler()
	{
		@Override
		public void actionPerformed(Object sender, Object eventArgs)
		{
			if (!(eventArgs instanceof BombTimeOutEventArgs)) throw new IllegalArgumentException();
			BombTimeOutEventArgs args = (BombTimeOutEventArgs)eventArgs;
			bombTimeOut(args);
		}
	};
	//endregion
	
	
	
	//region PUBLIC_METHODS
	/**
	*Called upon starting the game
	*Airs the gameCreated event
	*
	*/
	public void newGame() throws NullPointerException{
		if(gameTable==null){
			throw new NullPointerException();
		}
		gameTable.generateTable();
		GameCreatedEventArg args = new GameCreatedEventArg()
			{
				private Table t;
				public GameCreatedEventArg init(Table gt){
					t=gt;
					return this;
				}
				
				public Actor[][] getFields(){
					return t.getPlayField();
				}
			};
		gameCreated.notifyListeners(args.init(gameTable));
	}

	
	/**
	 * Adds a new player to the game
	 *
	 */
	public void newPlayer(){
		Player newPlayer = gameTable.addPlayer();
		players.push(newPlayer);
		hasPlayerPlacedBomb.put(newPlayer,false);
		
		playerIdEvent.notifyListeners(new PlayerIDEventArg(newPlayer.getPlayer_id()));
	}
	
	
	/**
	*Called upon Player movement
	*
	*@param id the unique identifier of the player
	*@param dir the direction of the movement
	*/
	public void movePlayer(int id, Direction dir){
		Player p = getPlayerById(id);
		if (p==null){
			throw new NoSuchElementException("Invalid player ID!");
		}
		if(!p.isAlive()){
			return;
		}
		int newX=0, newY=0;
		switch(dir){
			case UP : newX = 0; newY = -1; break;
			case RIGHT : newX = 1; newY = 0; break;
			case DOWN : newX = 0; newY = 1; break;
			case LEFT : newX = -1; newY = 0; break;
		}
		if(isFieldEmpty(newX, newY)){
			Actor a = FieldToActor(p.getX(), p.getY(), Field.EMPTY);
			gameTable.setField(p.getX(), p.getY(), a);
			notifyFieldChanged(p.getX(), p.getY(), a);
			p.setX(p.getX()+newX);
			p.setY(p.getY()+newY);
			notifyFieldChanged(p.getX(), p.getY(), p);
		}
	}
	
	
	/**
	*Called upon bomb placement
	*@param id the unique identifier of the player
	*/
	public void placeBomb(int id){
		Player p = getPlayerById(id);
		if (p==null){
			throw new NoSuchElementException("Invalid player ID!");
		}
		if(hasPlayerPlacedBomb.get(p) || !p.isAlive()){
			return;
		}		
		Bomb b = new Bomb(p.getX(), p.getY());
		Actor a = FieldToActor(p.getX(), p.getY(), Field.BOMB);
		gameTable.setField(p.getX(), p.getY(), a);
		notifyFieldChanged(p.getX(), p.getY(), a);
		hasPlayerPlacedBomb.set(p,true);
		placedBombs.add(b);
		
		Timer t = new Timer();
		t.schedule(new TimerTask(){
			@Override
			public void run(){
				bombTimeOutEvent.notifyListeners(new BombTimeOutEventArgs(p,b));
			}
		}, 2000);
	}
	
	/**
	 * Disconnects player from the game
	 *
	 * @param ID the identifier of the player
	 */
	public void leavePlayer(int id){
		Player p = getPlayerById(id);
		players.remove(p);
		hasPlayerPlacedBomb.remove(p);
	}
	
	//endregion
	
	
	//region PRIVATE_METHODS	
	/**
	*Initializes the neccessary objects used within this class
	*
	*/
	private void Initialize(){
		gameTable = new Table();
		placedBombs = new HashSet<Bomb>();
		players = new ArrayList<Player>();
		hasPlayerPlacedBomb = new HashMap<Player, Boolean>();
		bombTimeOutEvent = new Event();
		gameAdvanced = new Event();
		gameOver = new Event();
		gameCreated = new Event();
		playerIdEvent = new Event();
		bombTimeOutEvent.addListener(bombTimeOutHandler);
	}
	
	
	/**
	*Converts the given field to the specific Actor object
	*
	@param x the X coordinate
	@param y the Y coordinate
	*@param f Fieldtype to convert
	*@returns the given altype object 
	*/
	private Actor FieldToActor(int x, int y, Field f){
		switch(f){
			case EMPTY: return new Flor(x,y); break;
			case WALL: return new Wall(x,y); break;
			case OBSTACLE: return new Obst(x,y); break;
			case BOMB: return new Bomb(x,y); break;
			default: throw new InvalidParameterException();
		}
	}
	
	/**
	*Airs the event of the changed field
	*@param x the X coordinate of the field
	*@param y the Y coordinate of the field
	*@param f the type of the Field
	*
	*/
	private void notifyFieldChanged(int x, int y, Actor a)
	{
		GameAdvancedEventArg args = new GameAdvancedEventArg()
		{
			private int x,y;
			private Actor f;
			public int getX(){
				return x;
			}
			public int getY(){
				return y;
			}
			public Field getActor(){
				return a;
			}
			public GameAdvancedEventArg init(int i, int j, Actor ac){
				x=i;y=j;a=ac;
				return this;
			}
		};
		gameAdvanced.notifyListeners(args.init(x,y,a));
	}

	/**
	*Called in own methods to get the Player object by its ID
	*@param id player identifier
	*@returns the given player or null if it does not exist
	*/
	private Player getPlayerById(int id){
		for(Player p : players){
			if(p.getPlayer_id()==id) return p;
		}
		return null;
	}
	
	/**
	*Called when cheking fields
	*@param x coordinate, y coordinate
	*@returns boolean value of being empty
	*/
	private boolean isFieldEmpty(int x, int y){
		if(!inRange(x, y)) return false;
		for(Player p : players){
			if(p.getX()==x && p.getY()==y) return false;
		}
		return (gameTable.getField(x, y) instanceof Flor);
	}

	
	/**
	*Helper method, called by the eventhandler of the explosion
	*@param b Bomb exploding
	*
	*/
	private void bombTimeOut(BombTimeOutEventArgs args){
		Bomb b = args.getBomb();
		Player pp = args.getPlayer();
		int x = b.getX();
		int y = b.getY();
		
		hasPlayerPlacedBomb.set(pp,false);
		placedBombs.remove(b);
		
		bombExplode(x,y);
		Actor a = FieldToActor(x,y, Field.EMPTY);
		gameTable.setField(x,y,a);
		notifyFieldChanged(x,y,a);
		
		for(int i = 1;i<= BOMB_RANGE; ++i){
			bombExplode(x-i,y);
			bombExplode(x+i,y);
			bombExplode(x,y-i);
			bombExplode(x,y+i);			
		}
		
		int alive=0;
		LinkedList<Player> players_alive = new LinkedList<Player>();
		for(Player p : players){
			if(p.isAlive()){
				++alive;
				players_alive.add(p);
			}
		}
		if(alive==1){
			GameOverEventArg goea = new GameOverEventArg()
			{
				private int winner_id;
				public int getWinnerId(){
					return winner_id;
				}
				public GameOverEventArg init(int id){
					winner_id = id;
					return this;
				}
			};
			gameOver.notifyListeners(goea.init(players_alive.get(0).getPlayer_id()));
		}
	}
	/**
	*Called upon bomb explosion event
	*Executes the explosions horizontally and vertically from the params
	
	*@param x the X coordinate of the field
	*@param y the Y coordinate of the field
	*
	*/
	private void bombExplode(int x, int y){
		if(inRange(x,y)){
			for(Player p : players){
				if(p.getX()==x && p.getY()==y){
					p.setAlive(false);
				}
			}
			if(gameTable.getField(x,y).isDestroyable()){
				Actor a = FieldToActor(x,y, Field.EMPTY);
				gameTable.setField(x,y,a);
				notifyFieldChanged(x,y,a);
			}
		}
	}
	/**
	*Called upon calculating the bomb explosion
	*@param x the X coordinate of the field
	*@param y the Y coordinate of the field
	*
	*@returns the boolean value of the given field being within the table's range
	*/
	private boolean inRange(int x, int y){
		return (x>=0 && y>=0 && x<gameTable.getWidth() && y<gameTable.getHeight());
	}
	//endregion
	
	
}