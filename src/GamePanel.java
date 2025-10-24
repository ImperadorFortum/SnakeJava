import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class GamePanel extends JPanel {
    private Snake snake;
    private Renderer renderer;
    private SnakeGame game;
    private static final int GRID_SIZE = 25;
    private static final int TILE_SIZE = 25;

    public GamePanel(SnakeGame game, Renderer renderer) {
        this.game = game;
        this.renderer = renderer;
        setPreferredSize(new Dimension(GRID_SIZE * TILE_SIZE, GRID_SIZE * TILE_SIZE));
        setBackground(Color.BLACK);
        setupKeyBindings();
    }

    public void setSnake(Snake snake) {
        this.snake = snake;
    }
    
    private void setupKeyBindings() {
        InputMap inputMap = getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = getActionMap();
        
        inputMap.put(KeyStroke.getKeyStroke("UP"), "up");
        inputMap.put(KeyStroke.getKeyStroke("DOWN"), "down");
        inputMap.put(KeyStroke.getKeyStroke("LEFT"), "left");
        inputMap.put(KeyStroke.getKeyStroke("RIGHT"), "right");
        inputMap.put(KeyStroke.getKeyStroke("W"), "up");
        inputMap.put(KeyStroke.getKeyStroke("S"), "down");
        inputMap.put(KeyStroke.getKeyStroke("A"), "left");
        inputMap.put(KeyStroke.getKeyStroke("D"), "right");
        
        actionMap.put("up", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if (snake != null && snake.getDirection() != Direction.DOWN) {
                    snake.setDirection(Direction.UP);
                }
            }
        });
        
        actionMap.put("down", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if (snake != null && snake.getDirection() != Direction.UP) {
                    snake.setDirection(Direction.DOWN);
                }
            }
        });
        
        actionMap.put("left", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if (snake != null && snake.getDirection() != Direction.RIGHT) {
                    snake.setDirection(Direction.LEFT);
                }
            }
        });
        
        actionMap.put("right", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if (snake != null && snake.getDirection() != Direction.LEFT) {
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