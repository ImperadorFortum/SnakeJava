import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.ImageIO;
import java.net.URL;

public class SwingRenderer implements Renderer {
    
    private BufferedImage corpoSprite;
    private BufferedImage macaSprite;
    private BufferedImage goldenMacaSprite;
    private BufferedImage greenMacaSprite;
    private BufferedImage paredeSprite;
    private BufferedImage fundoSprite;

    public SwingRenderer() {
        try {
            // Tentar múltiplas estratégias para encontrar os sprites
            corpoSprite = loadImage("Corpo.png");
            macaSprite = loadImage("Maçã.png"); // Maçã normal
            goldenMacaSprite = loadImage("MacaDourada.png"); // Nova maçã dourada
            greenMacaSprite = loadImage("MacaVerde.png"); // Nova maçã verde
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
                System.out.println("✓ Carregando " + fileName + " via getResource");
                return ImageIO.read(resourceUrl);
            }
        } catch (Exception e) {
            // Continuar para próxima estratégia
        }
        
        // Estratégia 2: Buscar a pasta SnakeJava no caminho atual
        try {
            String currentDir = System.getProperty("user.dir");
            File projectRoot = findProjectRoot(new File(currentDir));
            
            if (projectRoot != null) {
                File viewDir = new File(projectRoot, "view");
                File imageFile = new File(viewDir, fileName);
                
                if (imageFile.exists()) {
                    System.out.println("✓ Carregando " + fileName + " de: " + imageFile.getAbsolutePath());
                    return ImageIO.read(imageFile);
                }
            }
        } catch (Exception e) {
            // Continuar para próxima estratégia
        }
        
        // Estratégia 3: Caminho relativo direto
        try {
            File file = new File("view/" + fileName);
            if (file.exists()) {
                System.out.println("✓ Carregando " + fileName + " via caminho relativo");
                return ImageIO.read(file);
            }
        } catch (Exception e) {
            // Continuar para próxima estratégia
        }
        
        // Estratégia 4: Subir um nível
        try {
            File file = new File("../view/" + fileName);
            if (file.exists()) {
                System.out.println("✓ Carregando " + fileName + " via ../view/");
                return ImageIO.read(file);
            }
        } catch (Exception e) {
            // Continuar para próxima estratégia
        }
        
        // Estratégia 5: Subir dois níveis (caso esteja em bin ou similar)
        try {
            File file = new File("../../view/" + fileName);
            if (file.exists()) {
                System.out.println("✓ Carregando " + fileName + " via ../../view/");
                return ImageIO.read(file);
            }
        } catch (Exception e) {
            // Continuar para próxima estratégia
        }
        
        // Estratégia 6: Buscar no diretório do código compilado
        try {
            String classPath = getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
            // Decodificar URL encoding (ex: %20 -> espaço)
            classPath = java.net.URLDecoder.decode(classPath, "UTF-8");
            
            File classFile = new File(classPath);
            File projectRoot = findProjectRoot(classFile);
            
            if (projectRoot != null) {
                File viewDir = new File(projectRoot, "view");
                File imageFile = new File(viewDir, fileName);
                
                if (imageFile.exists()) {
                    System.out.println("✓ Carregando " + fileName + " via classPath: " + imageFile.getAbsolutePath());
                    return ImageIO.read(imageFile);
                }
            }
        } catch (Exception e) {
            // Falhou
        }
        
        System.err.println("✗ Não foi possível encontrar: " + fileName);
        System.err.println("  Diretório atual: " + System.getProperty("user.dir"));
        System.err.println("  DICA: Abra a pasta 'SnakeJava' no VS Code, não a pasta pai!");
        return null;
    }
    
    /**
     * Busca recursivamente pela pasta do projeto (que contém a pasta 'view')
     */
    private File findProjectRoot(File startDir) {
        File current = startDir;
        
        // Subir até 5 níveis procurando pela pasta 'view'
        for (int i = 0; i < 5; i++) {
            if (current == null || !current.exists()) {
                break;
            }
            
            // Verificar se este diretório contém a pasta 'view'
            File viewDir = new File(current, "view");
            if (viewDir.exists() && viewDir.isDirectory()) {
                // Verificar se contém os arquivos esperados
                File testFile = new File(viewDir, "Corpo.png");
                if (testFile.exists()) {
                    return current;
                }
            }
            
            // Verificar se há uma subpasta 'SnakeJava' aqui
            File snakeJavaDir = new File(current, "SnakeJava");
            if (snakeJavaDir.exists() && snakeJavaDir.isDirectory()) {
                File viewDir2 = new File(snakeJavaDir, "view");
                if (viewDir2.exists() && viewDir2.isDirectory()) {
                    return snakeJavaDir;
                }
            }
            
            // Subir um nível
            current = current.getParentFile();
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
        BufferedImage currentFoodSprite = null;
        if (food instanceof GoldenFood) {
            currentFoodSprite = goldenMacaSprite;
        } else if (food instanceof GreenFood) {
            currentFoodSprite = greenMacaSprite;
        } else { // Default para NormalFood
            currentFoodSprite = macaSprite;
        }

        if (currentFoodSprite != null) {
            g.drawImage(currentFoodSprite, foodPos.x * 20, foodPos.y * 20, 20, 20, null);
        } else {
            // Fallback: desenhar círculo baseado no tipo de maçã se a imagem não foi carregada
            if (food instanceof GoldenFood) {
                g.setColor(Color.YELLOW);
            } else if (food instanceof GreenFood) {
                g.setColor(Color.GREEN.darker());
            } else {
                g.setColor(Color.RED);
            }
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

    // O método main foi removido desta classe e centralizado em controller/main.java
}

