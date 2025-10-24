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
        playBackgroundMusic(); // Música começa assim que o jogo abre
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
            skinsPanel.setBackground(new Color(40, 180, 40));
            
            JLabel title = new JLabel("ESCOLHA SUA SKIN");
            title.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
            title.setForeground(Color.YELLOW);
            title.setAlignmentX(Component.CENTER_ALIGNMENT);
            skinsPanel.add(title);
            skinsPanel.add(Box.createRigidArea(new Dimension(0, 30)));
            
            String[] skins = {"Azul", "Vermelho", "Verde"};
            Color[] colors = {new Color(0, 100, 255), new Color(255, 50, 50), new Color(50, 200, 50)};
            
            ButtonGroup skinGroup = new ButtonGroup();
            
            for (int i = 0; i < skins.length; i++) {
                JPanel skinPanel = new JPanel();
                skinPanel.setBackground(new Color(40, 180, 40));
                skinPanel.setLayout(new FlowLayout());
                
                JRadioButton radioButton = new JRadioButton(skins[i]);
                radioButton.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
                radioButton.setForeground(Color.WHITE);
                radioButton.setBackground(new Color(40, 180, 40));
                
                JLabel colorLabel = new JLabel("■");
                colorLabel.setFont(new Font("Arial", Font.BOLD, 24));
                colorLabel.setForeground(colors[i]);
                
                skinGroup.add(radioButton);
                skinPanel.add(colorLabel);
                skinPanel.add(Box.createRigidArea(new Dimension(10, 0)));
                skinPanel.add(radioButton);
                skinsPanel.add(skinPanel);
                skinsPanel.add(Box.createRigidArea(new Dimension(0, 15)));
                
                if (skins[i].equalsIgnoreCase(SkinManager.getSelectedSkin().replace(".png", ""))) {
                    radioButton.setSelected(true);
                }
                
                final String skinName = skins[i];
                radioButton.addActionListener(evt -> {
                    SkinManager.setSelectedSkin(skinName + ".png");
                    JOptionPane.showMessageDialog(skinsFrame, "Skin alterada para: " + skinName, "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                });
            }
            
            JButton backButton = new JButton("VOLTAR AO MENU");
            backButton.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
            backButton.setForeground(Color.WHITE);
            backButton.setBackground(new Color(255, 50, 50));
            backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            backButton.setMaximumSize(new Dimension(280, 65));
            backButton.setFocusPainted(false);
            backButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
            skinsPanel.add(Box.createRigidArea(new Dimension(0, 25)));
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

            new Thread(() -> {
                while (gameRunning) {
                    game.update();
                    if (game.isGameOver()) {
                        gameRunning = false;
                        final int finalScore = game.getScore();
                        final int snakeSize = game.getSnake().getBody().size();
                        SwingUtilities.invokeLater(() -> {
                            int choice = JOptionPane.showConfirmDialog(frame, 
                                "GAME OVER!\nPontuação: " + finalScore + "\nTamanho: " + snakeSize + "\n\nJogar Novamente?", 
                                "Fim de Jogo", 
                                JOptionPane.YES_NO_OPTION);
                            if (choice == JOptionPane.YES_OPTION) {
                                frame.dispose();
                                runGameLoop();
                            } else {
                                frame.dispose();
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
        setPreferredSize(new Dimension(800, 650));
        setBackground(new Color(40, 180, 40));
        setLayout(new BorderLayout());
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 80, 0));
        
        JLabel title = new JLabel("SNAKEDASH");
        title.setFont(new Font("Comic Sans MS", Font.BOLD, 58));
        title.setForeground(new Color(255, 255, 0));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel subtitle = new JLabel("Jogo da Cobrinha");
        subtitle.setFont(new Font("Comic Sans MS", Font.ITALIC, 22));
        subtitle.setForeground(new Color(255, 255, 200));
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        
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
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        buttonPanel.add(subtitle);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 60)));
        buttonPanel.add(startButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 25)));
        buttonPanel.add(skinsButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 25)));
        buttonPanel.add(exitButton);
        buttonPanel.add(Box.createVerticalGlue());
        
        add(buttonPanel, BorderLayout.CENTER);
        
        JLabel credits = new JLabel("Desenvolvido Por Joedson Nascimento e Felipe Dantas", JLabel.CENTER);
        credits.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
        credits.setForeground(new Color(200, 255, 200));
        credits.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(credits, BorderLayout.SOUTH);
    }
    
    private JButton createMenuButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Comic Sans MS", Font.BOLD, 22));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(255, 50, 50));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(350, 70));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(255, 100, 100));
                button.setForeground(new Color(255, 255, 0));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(255, 50, 50));
                button.setForeground(Color.WHITE);
            }
        });
        
        return button;
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Fundo limpo sem bolas
    }
}