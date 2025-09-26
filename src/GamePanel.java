import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    private Snake snake;
    private Food food;
    private SwingRenderer renderer;

    public GamePanel(Snake snake, Food food, SwingRenderer renderer) {
        this.snake = snake;
        this.food = food;
        this.renderer = renderer;
        setPreferredSize(new Dimension(400, 400));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        renderer.draw(snake, food, g);
    }
}