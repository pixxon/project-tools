package bomberman.persistance;

/**
 *
 * @author Imre
 */
public class Player extends Actor{
    
    private static int PLAYER_ID;
    
    private final int player_id;
    private boolean alive;
    private boolean bombInQue;
    private Bomb bomb;

    public Player(int posX, int posY) {
        super(posX, posY);
        this.player_id = PLAYER_ID;
        PLAYER_ID++;
        alive = true;
        destroyable = true;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public boolean isAlive() {
        return alive;
    }

    public int getPlayer_id() {
        return player_id;
    }
    
    public void PlaceBomb(){
        bomb = new Bomb(posX, posY);
        bombInQue = true;
    }

    public void setBombInQue(boolean bombInQue) {
        this.bombInQue = bombInQue;
    }

    public void setBomb(Bomb bomb) {
        this.bomb = bomb;
    }

    public boolean isBombInQue() {
        return bombInQue;
    }

    public Bomb getBomb() {
        return bomb;
    }
}
