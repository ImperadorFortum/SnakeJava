import javax.swing.SwingUtilities;
import javax.swing.JFrame;
import java.awt.Point;

public class main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Renderer renderer = new SwingRenderer();
            Snake snake = new Snake();
            Food food = new Food();
            GamePanel panel = new GamePanel(snake, food, renderer);

            JFrame frame = new JFrame("Snake Game");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(panel);
            frame.pack();
            frame.setLocationRelativeTo(null); // Centralizar janela
            frame.setVisible(true);

            // Loop do jogo com lógica de colisão e crescimento
            new Thread(() -> {
                while (true) {
                    // Mover a cobra
                    snake.move();
                    
                    // Verificar se a cobra comeu a maçã
                    Point head = snake.getHead();
                    Point foodPos = food.getPosition();
                    
                    if (head.equals(foodPos)) {
                        // Cobra comeu a maçã
                        snake.grow();
                        food.respawn();
                        System.out.println("Maçã comida! Tamanho da cobra: " + snake.getBody().size());
                    }
                    
                    // Atualizar o painel
                    panel.repaint();
                    
                    try {
                        Thread.sleep(200); // Velocidade do jogo (200ms = mais lento, mais fácil de testar)
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                }
            }).start();
        });
    }
}

