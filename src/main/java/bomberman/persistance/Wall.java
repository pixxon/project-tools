package bomberman.persistance;

/**
 *
 * @author Imre
 */
public class Wall extends Actor{

    public Wall(int posX, int posY) {
        super(posX, posY);
        destroyable = false; 
    }
    
    @Override
    public String toString(){
	return "Wall";
    }
}
