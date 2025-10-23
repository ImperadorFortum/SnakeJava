
public class GreenFood extends Food {
    public GreenFood() {
        super();
    }

    @Override
    public void applyEffect(Snake snake, SnakeGame game) {
        snake.shrink(2); // Diminui a cobra em 2 segmentos
        game.increaseScore(5); // Pontuação menor por maçã verde
    }

    @Override
    public void respawn() {
        super.respawn();
    }
}

