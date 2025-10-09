import java.util.*;
import java.awt.Point;

public class Snake {
    private List<Point> body;
    private Direction direction;

    public Snake() {
        body = new ArrayList<>();
        body.add(new Point(5, 5)); // posição inicial
        direction = Direction.RIGHT;
    }

    public void move() {
        Point head = getHead();
        Point newHead = new Point(head.x + direction.dx, head.y + direction.dy);
        body.add(0, newHead);
        body.remove(body.size() - 1);
    }

    public void grow() {
        Point head = getHead();
        Point newHead = new Point(head.x + direction.dx, head.y + direction.dy);
        body.add(0, newHead);
    }

    public Point getHead() {
        return body.get(0);
    }

    public List<Point> getBody() {
        return body;
    }

    public void setDirection(Direction d) {
        this.direction = d;
    }
}