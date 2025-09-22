import java.util.Random;

public class Food {
    private Point position;
    private Random random = new Random();

    public Food() {
        respawn();
    }

    public void respawn() {
        position = new Point(random.nextInt(20), random.nextInt(20));
    }

    public Point getPosition() {
        return position;
    }
}