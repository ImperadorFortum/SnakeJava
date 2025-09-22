public class SnakeGameApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Renderer renderer = new SwingRenderer();
            SnakeGame game = new SnakeGame(renderer, 120); // 120 = tempo de atualização em ms
            game.start();
        });
    }

    public class SnakeGame extends Game {
    private Snake snake;
    private Food food;
    private Renderer renderer;
    private boolean running = true;

    public SnakeGame(Renderer renderer) {
        this.renderer = renderer;
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
        snake.move();
        if (snake.getHead().equals(food.getPosition())) {
            snake.grow();
            food.respawn();
        }
    }

    protected void render() {
        renderer.draw(snake, food);
    }

    protected boolean isGameOver() {
        return !running;
    }

    protected void gameOver() {
        System.out.println("Fim de jogo!");
    }
}