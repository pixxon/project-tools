package bomberman.models;

/**
 *
 * @author Imre
 */
public class Wall extends Actor{

    public Wall(int posX, int posY) {
        super(posX, posY);
        destroyable = false; 
    }
    
}
