import java.awt.Graphics;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Random;

public abstract class SnakeGame extends Game {
    private Snake snake;
    private Food food;
    private Renderer renderer;
    private boolean running = true;
    private int updateInterval;
    private int normalUpdateInterval;
    private Random random = new Random();
    private int score = 0;

    public SnakeGame(Renderer renderer) {
        this.renderer = renderer;
        this.normalUpdateInterval = 120;
        this.updateInterval = normalUpdateInterval;
    }
    
    public SnakeGame(Renderer renderer, int updateInterval) {
        this.renderer = renderer;
        this.normalUpdateInterval = updateInterval;
        this.updateInterval = normalUpdateInterval;
    }
    
    protected void setup() {
        snake = new Snake();
        spawnFood();
    }

    private void spawnFood() {
        int foodType = random.nextInt(100);
        if (foodType < 70) {
            food = new NormalFood();
        } else if (foodType < 90) {
            food = new GoldenFood();
        } else {
            food = new GreenFood();
        }
    }

    protected void handleInput() { 
    }

    protected void update() {
        try {
            Thread.sleep(updateInterval);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        snake.move();
        if (snake.getHead().equals(food.getPosition())) {
            main.playEatingSound();
            food.applyEffect(snake, this);
            spawnFood();
        }
    }

    public void paintComponent(Graphics g) {
        renderer.draw(snake, food, g);
    }

    protected boolean isGameOver() {
        return !running;
    }

    protected void gameOver() {
        System.out.println("Fim de jogo!");
    }

    public void activateSpeedBoost(long durationMillis) {
        this.updateInterval = normalUpdateInterval / 2;
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                deactivateSpeedBoost();
            }
        }, durationMillis);
    }

    public void deactivateSpeedBoost() {
        this.updateInterval = normalUpdateInterval;
    }

    public Snake getSnake() {
        return snake;
    }

    public Food getFood() {
        return food;
    }

    public int getScore() {
        return score;
    }

    protected void increaseScore(int points) {
        this.score += points;
    }
}