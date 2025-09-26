import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.ImageIO;

public class SwingRenderer  {
    
    private BufferedImage corpoSprite;
    private BufferedImage macaSprite;

    public SwingRenderer() {
        try {
            corpoSprite = ImageIO.read(new File("view/Corpo.png"));
            macaSprite = ImageIO.read(new File("view/Maçã.png"));
        } catch (IOException e) {
            System.out.println("Erro ao carregar sprites: " + e.getMessage());
        }
    }

    public void draw(Snake snake, Food food, Graphics g) {
        // Desenha cada segmento da cobra
        for (Point pos : snake.getBody()) {
            g.drawImage(corpoSprite, pos.x * 20, pos.y * 20, 20, 20, null);
        }
        // Desenha a maçã
        Point foodPos = food.getPosition();
        g.drawImage(macaSprite, foodPos.x * 20, foodPos.y * 20, 20, 20, null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SwingRenderer renderer = new SwingRenderer();
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