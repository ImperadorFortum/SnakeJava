import java.awt.Graphics;

public abstract class SnakeGame extends Game {
    private Snake snake;
    private Food food;
    private Renderer renderer;
    private boolean running = true;
    private int updateInterval;

    public SnakeGame(Renderer renderer) {
        this.renderer = renderer;
        this.updateInterval = 120; // valor padrão, se desejar
    }
    public SnakeGame(Renderer renderer, int updateInterval) {
        this.renderer = renderer;
        this.updateInterval = updateInterval;
    }
    protected void setup() {
        snake = new Snake();
        food = new Food();
    }

    protected void handleInput() { 
        // Lógica para capturar e processar entrada do usuário
    }

    protected void update() {
        try {
            Thread.sleep(updateInterval); // controla o tempo de atualização
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        snake.move();
        if (snake.getHead().equals(food.getPosition())) {
            snake.grow();
            food.respawn();
        }
    }

    public void paintComponent(Graphics g) {
    super.paintComponent(g);
    renderer.draw(snake, food, g); //
    }

    protected boolean isGameOver() {
        return !running;
    }

    protected void gameOver() {
        System.out.println("Fim de jogo!");
    }
}