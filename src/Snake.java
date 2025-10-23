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

    public void shrink(int segments) {
        for (int i = 0; i < segments; i++) {
            if (body.size() > 1) { // Garante que a cobra não desapareça completamente
                body.remove(body.size() - 1);
            }
        }
    }

    public Point getHead() {
        return body.get(0);
    }

    public List<Point> getBody() {
        return body;
    }

    public void setDirection(Direction newDirection) {
        if ((direction == Direction.RIGHT && newDirection != Direction.LEFT) ||
            (direction == Direction.LEFT && newDirection != Direction.RIGHT) ||
            (direction == Direction.UP && newDirection != Direction.DOWN) ||
            (direction == Direction.DOWN && newDirection != Direction.UP)) {
            this.direction = newDirection;
        }
    }
    
    // Verifica se a cobra colidiu com ela mesma
    public boolean checkSelfCollision() {
        Point head = getHead();
        // Verifica se a cabeça está na mesma posição de algum segmento do corpo
        for (int i = 1; i < body.size(); i++) {
            if (head.equals(body.get(i))) {
                return true;
            }
        }
        return false;
    }
    
    // Verifica se a cobra colidiu com as paredes
    public boolean checkWallCollision() {
        Point head = getHead();
        // As paredes estão nas bordas: x=0, x=19, y=0, y=19
        return head.x <= 0 || head.x >= 19 || head.y <= 0 || head.y >= 19;
    }
    
    // Verifica qualquer tipo de colisão (parede ou próprio corpo)
    public boolean checkCollision() {
        return checkWallCollision() || checkSelfCollision();
    }
}

