import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.ImageIO;
import java.net.URL;

public class SwingRenderer implements Renderer {
    
    private BufferedImage corpoSprite;
    private BufferedImage macaSprite;
    private BufferedImage paredeSprite;
    private BufferedImage fundoSprite;

    public SwingRenderer() {
        try {
            // Tentar múltiplas estratégias para encontrar os sprites
            corpoSprite = loadImage("Corpo.png");
            macaSprite = loadImage("Maçã.png");
            paredeSprite = loadImage("Parede.png");
            fundoSprite = loadImage("Fundo.png");
            
            if (corpoSprite != null && macaSprite != null) {
                System.out.println("✓ Sprites carregados com sucesso!");
            } else {
                System.err.println("✗ Falha ao carregar sprites. Usando fallback visual.");
            }
            
        } catch (Exception e) {
            System.err.println("Erro ao carregar sprites: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private BufferedImage loadImage(String fileName) {
        // Estratégia 1: Tentar carregar como recurso da classe
        try {
            URL resourceUrl = getClass().getResource("/view/" + fileName);
            if (resourceUrl != null) {
                System.out.println("Carregando " + fileName + " via getResource: " + resourceUrl);
                return ImageIO.read(resourceUrl);
            }
        } catch (Exception e) {
            // Continuar para próxima estratégia
        }
        
        // Estratégia 2: Caminho relativo à raiz do projeto
        try {
            File file = new File("view/" + fileName);
            if (file.exists()) {
                System.out.println("Carregando " + fileName + " via caminho relativo: " + file.getAbsolutePath());
                return ImageIO.read(file);
            }
        } catch (Exception e) {
            // Continuar para próxima estratégia
        }
        
        // Estratégia 3: Subir um nível do diretório atual
        try {
            File file = new File("../view/" + fileName);
            if (file.exists()) {
                System.out.println("Carregando " + fileName + " via ../ : " + file.getAbsolutePath());
                return ImageIO.read(file);
            }
        } catch (Exception e) {
            // Continuar para próxima estratégia
        }
        
        // Estratégia 4: Buscar a partir do diretório do código fonte
        try {
            // Obter o diretório onde está o arquivo .class
            String classPath = getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
            File classDir = new File(classPath).getParentFile();
            File projectRoot = classDir.getParentFile(); // Subir para raiz do projeto
            File file = new File(projectRoot, "view/" + fileName);
            
            if (file.exists()) {
                System.out.println("Carregando " + fileName + " via classPath: " + file.getAbsolutePath());
                return ImageIO.read(file);
            }
        } catch (Exception e) {
            // Continuar para próxima estratégia
        }
        
        // Estratégia 5: Buscar recursivamente a partir do diretório atual
        try {
            File currentDir = new File(System.getProperty("user.dir"));
            File viewDir = findViewDirectory(currentDir);
            if (viewDir != null) {
                File file = new File(viewDir, fileName);
                if (file.exists()) {
                    System.out.println("Carregando " + fileName + " via busca recursiva: " + file.getAbsolutePath());
                    return ImageIO.read(file);
                }
            }
        } catch (Exception e) {
            // Falhou
        }
        
        System.err.println("✗ Não foi possível encontrar: " + fileName);
        System.err.println("  Diretório atual: " + System.getProperty("user.dir"));
        return null;
    }
    
    private File findViewDirectory(File dir) {
        // Buscar pasta "view" no diretório atual
        File viewDir = new File(dir, "view");
        if (viewDir.exists() && viewDir.isDirectory()) {
            return viewDir;
        }
        
        // Buscar no diretório pai
        File parent = dir.getParentFile();
        if (parent != null) {
            viewDir = new File(parent, "view");
            if (viewDir.exists() && viewDir.isDirectory()) {
                return viewDir;
            }
        }
        
        return null;
    }

    @Override
    public void draw(Snake snake, Food food, Graphics g) {
        // Desenhar fundo
        if (fundoSprite != null) {
            for (int x = 0; x < 20; x++) {
                for (int y = 0; y < 20; y++) {
                    g.drawImage(fundoSprite, x * 20, y * 20, 20, 20, null);
                }
            }
        } else {
            g.setColor(new Color(34, 139, 34)); // Verde escuro
            g.fillRect(0, 0, 400, 400);
        }
        
        // Desenhar paredes ao redor do mapa
        if (paredeSprite != null) {
            // Parede superior
            for (int x = 0; x < 20; x++) {
                g.drawImage(paredeSprite, x * 20, 0, 20, 20, null);
            }
            // Parede inferior
            for (int x = 0; x < 20; x++) {
                g.drawImage(paredeSprite, x * 20, 19 * 20, 20, 20, null);
            }
            // Parede esquerda
            for (int y = 0; y < 20; y++) {
                g.drawImage(paredeSprite, 0, y * 20, 20, 20, null);
            }
            // Parede direita
            for (int y = 0; y < 20; y++) {
                g.drawImage(paredeSprite, 19 * 20, y * 20, 20, 20, null);
            }
        } else {
            // Fallback: desenhar paredes cinzas
            g.setColor(Color.DARK_GRAY);
            g.fillRect(0, 0, 400, 20); // Superior
            g.fillRect(0, 380, 400, 20); // Inferior
            g.fillRect(0, 0, 20, 400); // Esquerda
            g.fillRect(380, 0, 20, 400); // Direita
        }
        
        // Desenha a maçã
        Point foodPos = food.getPosition();
        if (macaSprite != null) {
            g.drawImage(macaSprite, foodPos.x * 20, foodPos.y * 20, 20, 20, null);
        } else {
            // Fallback: desenhar círculo vermelho se a imagem não foi carregada
            g.setColor(Color.RED);
            g.fillOval(foodPos.x * 20, foodPos.y * 20, 20, 20);
        }
        
        // Desenha cada segmento da cobra
        for (Point pos : snake.getBody()) {
            if (corpoSprite != null) {
                g.drawImage(corpoSprite, pos.x * 20, pos.y * 20, 20, 20, null);
            } else {
                // Fallback: desenhar retângulo verde se a imagem não foi carregada
                g.setColor(Color.GREEN);
                g.fillRect(pos.x * 20, pos.y * 20, 20, 20);
                g.setColor(Color.DARK_GRAY);
                g.drawRect(pos.x * 20, pos.y * 20, 20, 20);
            }
        }
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

