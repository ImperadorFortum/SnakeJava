import javax.swing.SwingUtilities;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.awt.Point;

public class main {
    private static volatile boolean gameRunning = true;
    
    public static void main(String[] args) {
        startGame();
    }
    
    private static void startGame() {
        gameRunning = true; // Resetar flag ao iniciar novo jogo
        
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
                // Usar array para permitir modificação dentro da lambda
                final int[] score = {0};
                
                while (gameRunning) {
                    // Mover a cobra
                    snake.move();
                    
                    // Verificar colisões (parede ou próprio corpo)
                    if (snake.checkCollision()) {
                        gameRunning = false;
                        
                        // Capturar valores finais para uso na lambda
                        final int finalScore = score[0];
                        final int snakeSize = snake.getBody().size();
                        
                        // Mostrar tela de Game Over
                        SwingUtilities.invokeLater(() -> {
                            String message = "Game Over!\n\n" +
                                           "Pontuação: " + finalScore + "\n" +
                                           "Tamanho da cobra: " + snakeSize;
                            
                            int option = JOptionPane.showOptionDialog(
                                frame,
                                message,
                                "Game Over",
                                JOptionPane.YES_NO_OPTION,
                                JOptionPane.INFORMATION_MESSAGE,
                                null,
                                new String[]{"Jogar Novamente", "Sair"},
                                "Jogar Novamente"
                            );
                            
                            if (option == JOptionPane.YES_OPTION) {
                                // Reiniciar o jogo
                                frame.dispose();
                                startGame();
                            } else {
                                // Sair do jogo
                                System.exit(0);
                            }
                        });
                        
                        break;
                    }
                    
                    // Verificar se a cobra comeu a maçã
                    Point head = snake.getHead();
                    Point foodPos = food.getPosition();
                    
                    if (head.equals(foodPos)) {
                        // Cobra comeu a maçã
                        snake.grow();
                        food.respawn();
                        score[0] += 10;
                        System.out.println("Maçã comida! Pontuação: " + score[0] + " | Tamanho: " + snake.getBody().size());
                    }
                    
                    // Atualizar o painel
                    panel.repaint();
                    
                    try {
                        Thread.sleep(150); // Velocidade do jogo
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                }
            }).start();
        });
    }
}

