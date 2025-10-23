import java.awt.Point;
import java.util.Random;

public abstract class Food {
    protected Point position;
    protected Random random = new Random();

    public Food() {
        respawn();
    }

    public void respawn() {
        int x = random.nextInt(18) + 1;
        int y = random.nextInt(18) + 1;
        position = new Point(x, y);
    }

    public Point getPosition() {
        return position;
    }

    public abstract void applyEffect(Snake snake, SnakeGame game);
}

