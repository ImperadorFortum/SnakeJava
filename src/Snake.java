import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class Snake {
    private List<Point> body;
    private Direction direction;
    private boolean grow;

    public Snake() {
        body = new ArrayList<>();
        direction = Direction.RIGHT;
        grow = false;
        
        // Inicia a cobra com 3 segmentos de corpo
        int startX = 5;
        int startY = 10;
        
        // Cabeça + 2 segmentos de corpo
        body.add(new Point(startX, startY));        // Cabeça
        body.add(new Point(startX - 1, startY));    // Primeiro segmento do corpo
        body.add(new Point(startX - 2, startY));    // Segundo segmento do corpo
    }

    public void move() {
        Point head = getHead();
        Point newHead = new Point(head.x, head.y);
        
        switch (direction) {
            case UP:
                newHead.y--;
                break;
            case DOWN:
                newHead.y++;
                break;
            case LEFT:
                newHead.x--;
                break;
            case RIGHT:
                newHead.x++;
                break;
        }
        
        body.add(0, newHead);
        
        if (!grow) {
            body.remove(body.size() - 1);
        } else {
            grow = false;
        }
    }

    public void grow() {
        this.grow = true;
    }

    public void resetSize() {
        // Mantém apenas a cabeça e adiciona 2 segmentos do corpo POSICIONADOS
        // ATRÁS da cabeça de acordo com a direção atual. Antes sempre
        // adicionávamos para a esquerda, o que fazia o renderizador
        // inferir que a cobra estava apontando para a direita.
        Point currentHead = getHead();
        Direction dir = this.direction;
        body.clear();

        // Cabeça (mesma posição)
        body.add(new Point(currentHead.x, currentHead.y));

        // Coloca os dois segmentos atrás da cabeça dependendo da direção
        switch (dir) {
            case UP:
                body.add(new Point(currentHead.x, currentHead.y + 1));
                body.add(new Point(currentHead.x, currentHead.y + 2));
                break;
            case DOWN:
                body.add(new Point(currentHead.x, currentHead.y - 1));
                body.add(new Point(currentHead.x, currentHead.y - 2));
                break;
            case LEFT:
                body.add(new Point(currentHead.x + 1, currentHead.y));
                body.add(new Point(currentHead.x + 2, currentHead.y));
                break;
            case RIGHT:
            default:
                body.add(new Point(currentHead.x - 1, currentHead.y));
                body.add(new Point(currentHead.x - 2, currentHead.y));
                break;
        }
    }

    public boolean checkCollision() {
        Point head = getHead();
        
        // Verifica colisão com as paredes
        if (head.x <= 0 || head.x >= 19 || head.y <= 0 || head.y >= 19) {
            return true;
        }
        
        // Verifica colisão com o próprio corpo (ignora a cabeça)
        for (int i = 1; i < body.size(); i++) {
            if (head.equals(body.get(i))) {
                return true;
            }
        }
        
        return false;
    }

    public Point getHead() {
        return body.get(0);
    }

    public List<Point> getBody() {
        return body;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        // Impede movimento inverso
        if ((this.direction == Direction.UP && direction != Direction.DOWN) ||
            (this.direction == Direction.DOWN && direction != Direction.UP) ||
            (this.direction == Direction.LEFT && direction != Direction.RIGHT) ||
            (this.direction == Direction.RIGHT && direction != Direction.LEFT)) {
            this.direction = direction;
        }
    }
}