import javax.swing.SwingUtilities;

public class SnakeGameApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Renderer renderer = new SwingRenderer();
            SnakeGame game = new SnakeGame(renderer, 120); // 120 = tempo de atualização em ms
            game.start();
        });
    }
}