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
	
	
	/**
	*Private newsted class, which helps when a bomb is exploding
	*
	*
	*/
	private class BombTimeOutEventArgs extends GameAdvancedEventArg{
		private Player p;
		public BombTimeOutEventArgs(int x, int y, Player p, Bomb b)
		{
			super(x,y,b);
			this.p=p;
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

	//region PUBLIC_GameEvent_GETTERS
	
	@Override
	public GameEvent getGameAdvanced(){
		return gameAdvanced;
	}
	
	@Override
	public GameEvent getGameOver(){
		return gameOver;
	}
	
	@Override
	public GameEvent getGameCreated(){
		return gameCreated;
	}
	
	@Override
	public GameEvent getPlayerID(){
		return playerIdGameEvent;
	}
	
	//endregion
	
	//region PRIVATE_GameEvents
	
	private GameEvent gameAdvanced;
	private GameEvent gameOver;
	private GameEvent gameCreated;
	private GameEvent playerIdGameEvent;
	private GameEvent bombTimeOutGameEvent;

	private GameEventHandler bombTimeOutHandler = new GameEventHandler()
	{
		@Override
		public void actionPerformed(Object sender, Object GameEventArgs)
		{
			if (!(GameEventArgs instanceof BombTimeOutEventArgs)) throw new IllegalArgumentException();
			BombTimeOutEventArgs args = (BombTimeOutEventArgs)GameEventArgs;
			bombTimeOut(args);
		}
	};
	//endregion
	
	
	
	//region PUBLIC_METHODS
	/**
	*Called upon starting the game
	*Airs the gameCreated GameEvent
	*
	*/
	public void newGame() throws NullPointerException{
		if(gameTable==null){
			throw new NullPointerException();
		}
		
		int size = gameTable.GetSize();
		GameCreatedEventArg args = new GameCreatedEventArg(size,size);
		for(int i=0;i<size;++i)
			for(int j=0;j<size;++j){
				args.setField(i,j,gameTable.getField(i,j));
			}
		gameCreated.notifyListeners(args);
	}

	
	/**
	 * Adds a new player to the game
	 * Notifies the view about the change
	 */
	public void newPlayer(){
		Player newPlayer = gameTable.AddPlayer();
		players.add(newPlayer);
		hasPlayerPlacedBomb.put(newPlayer,false);
		playerIdGameEvent.notifyListeners(new PlayerIDEventArg(newPlayer.getPlayer_id()));
		gameAdvanced.notifyListeners(new GameAdvancedEventArg(newPlayer.getPosX(), newPlayer.getPosY(), newPlayer));
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
			Actor a = FieldToActor(p.getPosX(), p.getPosY(), Field.EMPTY);
			gameTable.setField(p.getPosX(), p.getPosY(), a);
			gameAdvanced.notifyListeners(new GameAdvancedEventArg(p.getPosX(), p.getPosY(), a));
			p.setPosX(p.getPosX()+newX);
			p.setPosY(p.getPosY()+newY);
			gameAdvanced.notifyListeners(new GameAdvancedEventArg(p.getPosX(), p.getPosY(), p));
		}
	}
	
	
	/**
	*Called upon bomb placement
	*@param id the unique identifier of the player
	*/
	public void placeBomb(int id){
		final Player p = getPlayerById(id);
		if (p==null){
			throw new NoSuchElementException("Invalid player ID!");
		}
		if(hasPlayerPlacedBomb.get(p) || !p.isAlive()){
			return;
		}		
		final Bomb b = new Bomb(p.getPosX(), p.getPosY());
		Actor a = FieldToActor(p.getPosX(), p.getPosY(), Field.BOMB);
		gameTable.setField(p.getPosX(), p.getPosY(), a);
		gameAdvanced.notifyListeners(new GameAdvancedEventArg(p.getPosX(), p.getPosY(), a));
		hasPlayerPlacedBomb.put(p,true);
		placedBombs.add(b);
		
		Timer t = new Timer();
		t.schedule(new TimerTask(){
			@Override
			public void run(){
				bombTimeOutGameEvent.notifyListeners(new BombTimeOutEventArgs(p.getPosX(), p.getPosY(),p,b));
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
		Actor a = FieldToActor(p.getPosX(), p.getPosY(), Field.EMPTY);
		gameTable.setField(p.getPosX(), p.getPosY(), a);
		gameAdvanced.notifyListeners(new GameAdvancedEventArg(p.getPosX(), p.getPosY(), a));
			
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
		bombTimeOutGameEvent = new GameEvent();
		gameAdvanced = new GameEvent();
		gameOver = new GameEvent();
		gameCreated = new GameEvent();
		playerIdGameEvent = new GameEvent();
		bombTimeOutGameEvent.addListener(bombTimeOutHandler);
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
			case EMPTY: return new Flor(x,y);
			case WALL: return new Wall(x,y);
			case OBSTACLE: return new Obst(x,y);
			case BOMB: return new Bomb(x,y);
			default: return null;
		}
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
			if(p.getPosX()==x && p.getPosY()==y) return false;
		}
		return (gameTable.getField(x, y) instanceof Flor);
	}

	
	/**
	*Helper method, called by the GameEventhandler of the explosion
	*@param b Bomb exploding
	*
	*/
	private void bombTimeOut(BombTimeOutEventArgs args){
		Bomb b = (Bomb)args.getType();
		Player pp = args.getPlayer();
		int x = args.getX();
		int y = args.getY();
		
		hasPlayerPlacedBomb.put(pp,false);
		placedBombs.remove(b);
		
		bombExplode(x,y);
		Actor a = FieldToActor(x,y, Field.EMPTY);
		gameTable.setField(x,y,a);
		gameAdvanced.notifyListeners(new GameAdvancedEventArg(x,y,a));
		
		for(int i = 1;i<= BOMB_RANGE; ++i){
			bombExplode(x-i,y);
			bombExplode(x+i,y);
			bombExplode(x,y-i);
			bombExplode(x,y+i);			
		}
		
		int alive=0;
		for(Player p : players){
			if(p.isAlive()){
				++alive;
			}
		}
		if(alive==1){
			//gameOver.notifyListeners(new GameOverEventArg(p.getPlayer_id()));
		}
	}
	/**
	*Called upon bomb explosion GameEvent
	*Executes the explosions horizontally and vertically from the params
	
	*@param x the X coordinate of the field
	*@param y the Y coordinate of the field
	*
	*/
	private void bombExplode(int x, int y){
		if(inRange(x,y)){
			for(Player p : players){
				if(p.getPosX()==x && p.getPosY()==y){
					p.setAlive(false);
				}
			}
			if(gameTable.getField(x,y).isDestroyable()){
				Actor a = FieldToActor(x,y, Field.EMPTY);
				gameTable.setField(x,y,a);
				gameAdvanced.notifyListeners(new GameAdvancedEventArg(x,y,a));
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
		return (x>=0 && y>=0 && x<gameTable.GetSize() && y<gameTable.GetSize());
	}
	//endregion
	
	
}
