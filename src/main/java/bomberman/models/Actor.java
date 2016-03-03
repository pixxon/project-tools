package bomberman.models;

/**
 *
 * @author Imre
 */
public abstract class  Actor {
    
    protected int posX;
    protected int posY;
    
    protected boolean destroyable;

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public boolean isDestroyable() {
        return destroyable;
    }

    
}
