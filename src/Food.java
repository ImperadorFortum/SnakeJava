import java.util.Random;
import java.awt.Point;

public class Food {
    private Point position;
    private Random random = new Random();

    public Food() {
        respawn();
    }

    public void respawn() {
        // Gerar posição aleatória dentro da área jogável (evitando as paredes)
        // As paredes estão em x=0, x=19, y=0, y=19
        // Área jogável: x=1 a 18, y=1 a 18
        int x = random.nextInt(18) + 1; // 1 a 18
        int y = random.nextInt(18) + 1; // 1 a 18
        position = new Point(x, y);
    }

    public Point getPosition() {
        return position;
    }
}

