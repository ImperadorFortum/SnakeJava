import javax.swing.*;
<<<<<<< HEAD
=======

>>>>>>> 17d71ef8e30d69f281b2293c5bf3db3eaccd58c5

public class main {
    private static volatile boolean gameRunning = true;

    public static void main(String[] args) {
        startGame();
    }

    private static void startGame() {
        gameRunning = true;
        SwingUtilities.invokeLater(() -> {
            Renderer renderer = new SwingRenderer();
            SnakeGame game = new SnakeGame(renderer) {
                @Override
                protected void handleInput() {
                    // Lógica de input tratada pelo GamePanel
                }

                @Override
                protected void render() {
                    // Renderização tratada pelo GamePanel.repaint()
                }

                @Override
                protected boolean isGameOver() {
                    return getSnake().checkCollision();
                }

                @Override
                protected void gameOver() {
                    // Lógica de Game Over tratada no loop da thread principal
                }
            };
            game.setup();
            GamePanel panel = new GamePanel(game, renderer);
            panel.setSnake(game.getSnake());

            JFrame frame = new JFrame("Snake Game");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setResizable(false);
            frame.add(panel);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

            // Loop principal do jogo em uma thread separada
            new Thread(() -> {
                while (gameRunning) {
                    game.update();
                    if (game.isGameOver()) {
                        gameRunning = false;
                        final int finalScore = game.getScore();
                        final int snakeSize = game.getSnake().getBody().size();
                        SwingUtilities.invokeLater(() -> {
                            int choice = JOptionPane.showConfirmDialog(frame, 
                                "Game Over! Sua pontuação: " + finalScore + ". Tamanho da cobra: " + snakeSize + "\nQuer jogar novamente?", 
                                "Fim de Jogo", 
                                JOptionPane.YES_NO_OPTION);
                            if (choice == JOptionPane.YES_OPTION) {
                                frame.dispose(); // Fecha a janela atual
                                startGame(); // Reinicia o jogo
                            } else {
                                System.exit(0);
                            }
                        });
                        break;
                    }
                    panel.repaint();
                }
            }).start();
        });
    }
}

