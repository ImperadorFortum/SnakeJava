

public class NormalFood extends Food {
    public NormalFood() {
        super();
    }

    @Override
    public void applyEffect(Snake snake, SnakeGame game) {
        snake.grow();
        game.increaseScore(10); // Aumenta a pontuação ao comer maçã normal
    }

    @Override
    public void respawn() {
        super.respawn();
    }
}

