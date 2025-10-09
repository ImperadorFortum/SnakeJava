import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class GamePanel extends JPanel {
    private Snake snake;
    private Food food;
    private Renderer renderer;

    public GamePanel(Snake snake, Food food, Renderer renderer) {
        this.snake = snake;
        this.food = food;
        this.renderer = renderer;
        setPreferredSize(new Dimension(400, 400));
        setupKeyBindings();
    }
    
    private void setupKeyBindings() {
        // KeyBindings funcionam mesmo quando o componente não tem foco
        InputMap inputMap = getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = getActionMap();
        
        // Mapear teclas
        inputMap.put(KeyStroke.getKeyStroke("UP"), "up");
        inputMap.put(KeyStroke.getKeyStroke("DOWN"), "down");
        inputMap.put(KeyStroke.getKeyStroke("LEFT"), "left");
        inputMap.put(KeyStroke.getKeyStroke("RIGHT"), "right");
        
        // Definir ações
        actionMap.put("up", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                snake.setDirection(Direction.UP);
            }
        });
        
        actionMap.put("down", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                snake.setDirection(Direction.DOWN);
            }
        });
        
        actionMap.put("left", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                snake.setDirection(Direction.LEFT);
            }
        });
        
        actionMap.put("right", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                snake.setDirection(Direction.RIGHT);
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        renderer.draw(snake, food, g);
    }
}