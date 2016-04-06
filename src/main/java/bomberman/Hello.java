package bomberman;

import bomberman.models.Actor;
import bomberman.models.Table;

public class Hello{
    public static void main(String[] args) {
        Table f = new Table(5);
        Actor[][] a = f.getPlayField();
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a.length; j++) {
                System.out.print("[" + a[i][j].getClass().getSimpleName() + "]");
            }
            System.out.println("\n");
        }
    }
}