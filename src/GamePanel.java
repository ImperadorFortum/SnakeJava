import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class GamePanel extends JPanel {
    private Snake snake;
    private Renderer renderer;
    private SnakeGame game;

    public GamePanel(SnakeGame game, Renderer renderer) {
        this.game = game;
        this.renderer = renderer;
        setPreferredSize(new Dimension(400, 400));
        setupKeyBindings();
    }

    public void setSnake(Snake snake) {
        this.snake = snake;
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
                if (snake != null) {
                    snake.setDirection(Direction.UP);
                }
            }
        });
        
        actionMap.put("down", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (snake != null) {
                    snake.setDirection(Direction.DOWN);
                }
            }
        });
        
        actionMap.put("left", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (snake != null) {
                    snake.setDirection(Direction.LEFT);
                }
            }
        });
        
        actionMap.put("right", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (snake != null) {
                    snake.setDirection(Direction.RIGHT);
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (game != null && game.getSnake() != null && game.getFood() != null) {
            renderer.draw(game.getSnake(), game.getFood(), g);
        }
    }
}

