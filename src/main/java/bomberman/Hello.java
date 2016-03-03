package bomberman;

import bomberman.models.Actor;
import bomberman.models.Table;

public class Hello{
    public static void main(String[] args) {
        Table f = new Table();
        Actor[][] a = f.getField();
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                System.out.print("[" + a[i][j].getClass() + "]");
            }
            System.out.println("\n");
        }
    }
}