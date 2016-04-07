package bomberman.model;
import bomberman.persistence;
import java.util.*;

public class Model{
	
	#region PRIVATE_FIELDS
	private Table gameTable=null;
	private HashSet<Bomb> placedBombs = null;
	private HashMap<Player, boolean> hasPlayerPlacedBomb = null;
	private ArrayList<Player> players = null;
	
	
	private final int BOMB_RANGE = 2;
	#endregion
	
	#region CONSTRUCTORS
	public Model(){
		gameTable = new Table();
		Initialize();
	}
	
	public Model(Table t){
		gameTable = t;
		Initialize();
	}
	#endregion

	#region PUBLIC_METHODS
	public void newGame() throws NullPointerException{
		if(gameTable==null){
			throw new NullPointerException();
		}
		gameTable.generateTable();
	}

	public void movePlayer(Player p, Direction d){
		if(!p.isAlive()){
			return;
		}
		int newX=0, newY=0;
		switch(d){
			case Up : newX = 0; newY = -1; break;
			case Right : newX = 1; newY = 0; break;
			case Down : newX = 0; newY = 1; break;
			case Left : newX = -1; newY = 0; break;
		}
		if(gameTable.getField(newX, newY) == Field.Empty){
			p.setX(p.getX()+newX);
			p.setY(p.getY()+newY);
		}
	}
	
	public void placeBomb(Player p){
		if(hasPlayerPlacedBomb.get(p) || !p.isAlive()){
			return;
		}		
		Bomb b = new Bomb(p.getX(), p.getY());
		gameTable.setField(p.getX(), p.getY(), Field.Bomb);
		hasPlayerPlacedBomb.set(p,true);
		placedBombs.add(b);
		
		//b.timeout += this.bombTimeOut(b);
		
		b.startTick();
	}
	
	
	#endregion
	
	
	#region PRIVATE_METHODS	
	
	private void Initialize(){
		gameTable = new Table();
		placedBombs = new HashSet<Bomb>();
		players = new ArrayList<Player>();
		hasPlayerPlacedBomb = new HashMap<Player, boolean>();
	}
	
	
	private void bombTimeOut(Bomb b){
		int x = b.getX();
		int y = b.getY();
		
		hasPlayerPlacedBomb.set(p,false);
		placedBombs.remove(b);
		
		bombExplode(x,y);
		for(int i = 1;i<= BOMB_RANGE; ++i){
			bombExplode(x-i,y);
			bombExplode(x+i,y);
			bombExplode(x,y-i);
			bombExplode(x,y+i);			
		}
	}
	
	private void bombExplode(int x, int y){
		if(inRange(x,y)){
			for(Player p : players){
				if(p.getX()==x && p.getY()==y){
					p.invokeDeath();
				}
			}
			if(gameTable.getField(x,y)==Field.Wall){
				gameTable.setField(x,y,Field.Empty);
			}
		}
	}

	private boolean inRange(int x, int y){
		return (x>=0 && y>=0 && x<gameTable.getWidth() && y<gameTable.getHeight());
	}
	#endregion
}