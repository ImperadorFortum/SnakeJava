import javax.swing.*;
import java.awt.*;


public class main {
    private static volatile boolean gameRunning = true;

    public static void main(String[] args) {
        showMenu();
    }

    private static void showMenu() {
        SwingUtilities.invokeLater(() -> {
            JFrame menuFrame = new JFrame("Snake Game - Menu");
            menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            menuFrame.setResizable(false);
            
            JPanel menuPanel = new JPanel();
            menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
            menuPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
            
            JLabel title = new JLabel("Snake Game");
            title.setFont(new Font("Arial", Font.BOLD, 36));
            title.setAlignmentX(Component.CENTER_ALIGNMENT);
            
            JButton startButton = new JButton("Iniciar Jogo");
            startButton.setFont(new Font("Arial", Font.PLAIN, 20));
            startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            startButton.setMaximumSize(new Dimension(200, 50));
            
            JButton skinsButton = new JButton("Personalizar");
            skinsButton.setFont(new Font("Arial", Font.PLAIN, 20));
            skinsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            skinsButton.setMaximumSize(new Dimension(200, 50));
            
            JButton exitButton = new JButton("Sair");
            exitButton.setFont(new Font("Arial", Font.PLAIN, 20));
            exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            exitButton.setMaximumSize(new Dimension(200, 50));
            
            menuPanel.add(title);
            menuPanel.add(Box.createRigidArea(new Dimension(0, 30)));
            menuPanel.add(startButton);
            menuPanel.add(Box.createRigidArea(new Dimension(0, 15)));
            menuPanel.add(skinsButton);
            menuPanel.add(Box.createRigidArea(new Dimension(0, 15)));
            menuPanel.add(exitButton);
            
            startButton.addActionListener(e -> {
                menuFrame.dispose();
                runGameLoop();
            });
            
            skinsButton.addActionListener(e -> {
                showSkinsMenu(menuFrame);
            });
            
            exitButton.addActionListener(e -> {
                System.exit(0);
            });
            
            menuFrame.add(menuPanel);
            menuFrame.pack();
            menuFrame.setLocationRelativeTo(null);
            menuFrame.setVisible(true);
        });
    }

    private static void showSkinsMenu(JFrame parentFrame) {
        SwingUtilities.invokeLater(() -> {
            JFrame skinsFrame = new JFrame("Snake Game - Personalizar ");
            skinsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            skinsFrame.setResizable(false);
            
            JPanel skinsPanel = new JPanel();
            skinsPanel.setLayout(new BoxLayout(skinsPanel, BoxLayout.Y_AXIS));
            skinsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            
            JLabel title = new JLabel("Escolha sua Skin");
            title.setFont(new Font("Arial", Font.BOLD, 24));
            title.setAlignmentX(Component.CENTER_ALIGNMENT);
            skinsPanel.add(title);
            skinsPanel.add(Box.createRigidArea(new Dimension(0, 20)));
            
            // Lista de skins disponíveis (o usuário pode substituir as imagens)
            String[] skins = {"Azul", "Vermelho", "Verde"};
            
            ButtonGroup skinGroup = new ButtonGroup();
            
            for (String skin : skins) {
                JRadioButton radioButton = new JRadioButton(skin);
                radioButton.setFont(new Font("Arial", Font.PLAIN, 16));
                radioButton.setAlignmentX(Component.CENTER_ALIGNMENT);
                skinGroup.add(radioButton);
                skinsPanel.add(radioButton);
                skinsPanel.add(Box.createRigidArea(new Dimension(0, 5)));
                
                // Pré-selecionar a skin atual
                if (skin.startsWith(SkinManager.getSelectedSkin())) {
                    radioButton.setSelected(true);
                }
                
                radioButton.addActionListener(e -> {
                    String selectedSkinFile = radioButton.getText().split(" ")[0];
                    SkinManager.setSelectedSkin(selectedSkinFile);
                    JOptionPane.showMessageDialog(skinsFrame, "Skin alterada para: " + selectedSkinFile, "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                });
            }
            
            JButton backButton = new JButton("Voltar ao Menu Principal");
            backButton.setFont(new Font("Arial", Font.PLAIN, 18));
            backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            backButton.setMaximumSize(new Dimension(250, 40));
            skinsPanel.add(Box.createRigidArea(new Dimension(0, 20)));
            skinsPanel.add(backButton);
            
            backButton.addActionListener(e -> {
                skinsFrame.dispose();
                parentFrame.setVisible(true); // Reabre o menu principal
            });
            
            skinsFrame.add(skinsPanel);
            skinsFrame.pack();
            skinsFrame.setLocationRelativeTo(parentFrame);
            skinsFrame.setVisible(true);
            parentFrame.setVisible(false); // Esconde o menu principal
        });
    }

    private static void runGameLoop() {
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
                                runGameLoop(); // Reinicia o jogo
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

