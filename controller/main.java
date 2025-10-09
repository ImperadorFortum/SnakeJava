import javax.swing.SwingUtilities;
import javax.swing.JFrame;

public class main {
    public main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Renderer renderer = new SwingRenderer();
            Snake snake = new Snake();
            Food food = new Food();
            GamePanel panel = new GamePanel(snake, food, renderer);

            JFrame frame = new JFrame("Snake Game");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(panel);
            frame.pack();
            frame.setVisible(true);

            // Loop simples para atualizar o painel e mover a cobra
            new Thread(() -> {
                while (true) {
                    snake.move();
                    panel.repaint();
                    try {
                        Thread.sleep(120);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }).start();
        });
    }
}