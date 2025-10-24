import javax.swing.*;
import java.awt.*;
import javax.sound.sampled.*;
import java.io.File;

public class main {
    private static volatile boolean gameRunning = true;
    private static Clip backgroundMusic;
    private static Clip eatingSound;
    private static Clip gameStartSound;

    public static void main(String[] args) {
        loadSounds();
        showMenu();
    }

    private static void loadSounds() {
        try {
            File backgroundFile = new File("view\\musica.waw\\MUSICA_DE_FUNDO.wav");
            File eatingFile = new File("view\\musica.waw\\SOM-MAÇA.wav");
            File startFile = new File("view\\musica.waw\\INICIO_DE_JOGO.wav");
            
            System.out.println("Carregando arquivos de áudio...");
            System.out.println("Música fundo existe: " + backgroundFile.exists());
            System.out.println("Som maçã existe: " + eatingFile.exists());
            System.out.println("Som início existe: " + startFile.exists());
            
            if (backgroundFile.exists()) {
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(backgroundFile);
                backgroundMusic = AudioSystem.getClip();
                backgroundMusic.open(audioStream);
                System.out.println("Música de fundo carregada!");
            }
            
            if (eatingFile.exists()) {
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(eatingFile);
                eatingSound = AudioSystem.getClip();
                eatingSound.open(audioStream);
                System.out.println("Som da maçã carregado!");
            }
            
            if (startFile.exists()) {
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(startFile);
                gameStartSound = AudioSystem.getClip();
                gameStartSound.open(audioStream);
                System.out.println("Som de início carregado!");
            }
            
        } catch (Exception e) {
            System.err.println("Erro ao carregar arquivos de áudio: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void playBackgroundMusic() {
        if (backgroundMusic != null) {
            try {
                if (!backgroundMusic.isRunning()) {
                    backgroundMusic.setFramePosition(0);
                    backgroundMusic.loop(Clip.LOOP_CONTINUOUSLY);
                    backgroundMusic.start();
                    System.out.println("Música de fundo iniciada!");
                }
            } catch (Exception e) {
                System.err.println("Erro ao tocar música de fundo: " + e.getMessage());
            }
        } else {
            System.out.println("Música de fundo não carregada!");
        }
    }

    public static void stopBackgroundMusic() {
        if (backgroundMusic != null && backgroundMusic.isRunning()) {
            backgroundMusic.stop();
            backgroundMusic.setFramePosition(0);
            System.out.println("Música de fundo parada!");
        }
    }

    public static void playEatingSound() {
        if (eatingSound != null) {
            try {
                eatingSound.setFramePosition(0);
                eatingSound.start();
                System.out.println("Som da maçã tocado!");
            } catch (Exception e) {
                System.err.println("Erro ao tocar som da maçã: " + e.getMessage());
            }
        } else {
            System.out.println("Som da maçã não carregado!");
        }
    }

    public static void playGameStartSound() {
        if (gameStartSound != null) {
            try {
                gameStartSound.setFramePosition(0);
                gameStartSound.start();
                System.out.println("Som de início tocado!");
            } catch (Exception e) {
                System.err.println("Erro ao tocar som de início: " + e.getMessage());
            }
        } else {
            System.out.println("Som de início não carregado!");
        }
    }

    public static void showMenu() {
        SwingUtilities.invokeLater(() -> {
            JFrame menuFrame = new JFrame("SnakeDash - Menu");
            menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            menuFrame.setResizable(false);
            
            MenuPanel menuPanel = new MenuPanel();
            menuFrame.add(menuPanel);
            
            menuFrame.pack();
            menuFrame.setLocationRelativeTo(null);
            menuFrame.setVisible(true);
        });
    }

    public static void showSkinsMenu(JFrame parentFrame) {
        SwingUtilities.invokeLater(() -> {
            JFrame skinsFrame = new JFrame("SnakeDash - Personalizar");
            skinsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            skinsFrame.setResizable(false);
            
            JPanel skinsPanel = new JPanel();
            skinsPanel.setLayout(new BoxLayout(skinsPanel, BoxLayout.Y_AXIS));
            skinsPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
            skinsPanel.setBackground(new Color(34, 139, 34));
            
            JLabel title = new JLabel("ESCOLHA SUA SKIN");
            title.setFont(new Font("Arial", Font.BOLD, 16));
            title.setForeground(Color.WHITE);
            title.setAlignmentX(Component.CENTER_ALIGNMENT);
            skinsPanel.add(title);
            skinsPanel.add(Box.createRigidArea(new Dimension(0, 30)));
            
            String[] skins = {"Azul", "Vermelho", "Verde"};
            Color[] colors = {Color.BLUE, Color.RED, Color.GREEN};
            
            ButtonGroup skinGroup = new ButtonGroup();
            
            for (int i = 0; i < skins.length; i++) {
                JPanel skinPanel = new JPanel();
                skinPanel.setBackground(new Color(34, 139, 34));
                skinPanel.setLayout(new FlowLayout());
                
                JRadioButton radioButton = new JRadioButton(skins[i]);
                radioButton.setFont(new Font("Arial", Font.BOLD, 14));
                radioButton.setForeground(Color.WHITE);
                radioButton.setBackground(new Color(34, 139, 34));
                
                JLabel colorLabel = new JLabel("■");
                colorLabel.setFont(new Font("Arial", Font.BOLD, 20));
                colorLabel.setForeground(colors[i]);
                
                skinGroup.add(radioButton);
                skinPanel.add(colorLabel);
                skinPanel.add(radioButton);
                skinsPanel.add(skinPanel);
                skinsPanel.add(Box.createRigidArea(new Dimension(0, 10)));
                
                if (skins[i].equalsIgnoreCase(SkinManager.getSelectedSkin().replace(".png", ""))) {
                    radioButton.setSelected(true);
                }
                
                final String skinName = skins[i];
                radioButton.addActionListener(evt -> {
                    SkinManager.setSelectedSkin(skinName + ".png");
                    JOptionPane.showMessageDialog(skinsFrame, "Skin alterada para: " + skinName, "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                });
            }
            
            JButton backButton = new JButton("VOLTAR");
            backButton.setFont(new Font("Arial", Font.BOLD, 12));
            backButton.setForeground(Color.WHITE);
            backButton.setBackground(new Color(139, 0, 0));
            backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            backButton.setMaximumSize(new Dimension(200, 40));
            backButton.setFocusPainted(false);
            skinsPanel.add(Box.createRigidArea(new Dimension(0, 20)));
            skinsPanel.add(backButton);
            
            backButton.addActionListener(evt -> {
                skinsFrame.dispose();
                parentFrame.setVisible(true);
            });
            
            skinsFrame.add(skinsPanel);
            skinsFrame.pack();
            skinsFrame.setLocationRelativeTo(parentFrame);
            skinsFrame.setVisible(true);
            parentFrame.setVisible(false);
        });
    }

    public static void runGameLoop() {
        gameRunning = true;
        SwingUtilities.invokeLater(() -> {
            Renderer renderer = new SwingRenderer();
            SnakeGame game = new SnakeGame(renderer) {
                @Override
                protected void handleInput() {
                }

                @Override
                protected void render() {
                }

                @Override
                protected boolean isGameOver() {
                    return getSnake().checkCollision();
                }

                @Override
                protected void gameOver() {
                }
            };
            game.setup();
            GamePanel panel = new GamePanel(game, renderer);
            panel.setSnake(game.getSnake());

            JFrame frame = new JFrame("SnakeDash");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setResizable(false);
            frame.add(panel);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

            playBackgroundMusic();

            new Thread(() -> {
                while (gameRunning) {
                    game.update();
                    if (game.isGameOver()) {
                        gameRunning = false;
                        final int finalScore = game.getScore();
                        final int snakeSize = game.getSnake().getBody().size();
                        SwingUtilities.invokeLater(() -> {
                            stopBackgroundMusic();
                            int choice = JOptionPane.showConfirmDialog(frame, 
                                "GAME OVER!\nPontuação: " + finalScore + "\nTamanho: " + snakeSize + "\n\nJogar Novamente?", 
                                "Fim de Jogo", 
                                JOptionPane.YES_NO_OPTION);
                            if (choice == JOptionPane.YES_OPTION) {
                                frame.dispose();
                                runGameLoop();
                            } else {
                                showMenu();
                            }
                        });
                        break;
                    }
                    panel.repaint();
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException ex) {
                        Thread.currentThread().interrupt();
                    }
                }
            }).start();
        });
    }
}

class MenuPanel extends JPanel {
    public MenuPanel() {
        setPreferredSize(new Dimension(600, 500));
        setBackground(new Color(34, 139, 34));
        setLayout(new BorderLayout());
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 50, 0));
        
        JLabel title = new JLabel("SNAKEDASH");
        title.setFont(new Font("Arial", Font.BOLD, 36));
        title.setForeground(Color.YELLOW);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JButton startButton = createMenuButton("INICIAR JOGO");
        JButton skinsButton = createMenuButton("PERSONALIZAR");
        JButton exitButton = createMenuButton("SAIR");
        
        startButton.addActionListener(evt -> {
            SwingUtilities.getWindowAncestor(this).dispose();
            main.playGameStartSound();
            main.runGameLoop();
        });
        
        skinsButton.addActionListener(evt -> {
            JFrame parent = (JFrame) SwingUtilities.getWindowAncestor(this);
            main.showSkinsMenu(parent);
        });
        
        exitButton.addActionListener(evt -> {
            main.stopBackgroundMusic();
            System.exit(0);
        });
        
        buttonPanel.add(Box.createVerticalGlue());
        buttonPanel.add(title);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 50)));
        buttonPanel.add(startButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        buttonPanel.add(skinsButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        buttonPanel.add(exitButton);
        buttonPanel.add(Box.createVerticalGlue());
        
        add(buttonPanel, BorderLayout.CENTER);
    }
    
    private JButton createMenuButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(139, 0, 0));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(250, 50));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createRaisedBevelBorder());
        
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(178, 34, 34));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(139, 0, 0));
            }
        });
        
        return button;
    }
}