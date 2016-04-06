package bomberman.models;

/**
 *
 * @author Imre
 */
public class Bomb extends Actor{
    
    public static int BOMB_ID = 0;
    
    private final int local_bomb_id;
    
    public Bomb(int posX, int posY) {
        super(posX, posY);
        destroyable = true;
        local_bomb_id = BOMB_ID;
        BOMB_ID++;
    }

    public int getLocal_bomb_id() {
        return local_bomb_id;
    }
    
    
}
