
public class GreenFood extends Food {
    public GreenFood() {
        super();
    }

    @Override
    public void applyEffect(Snake snake, SnakeGame game) {
        snake.resetSize(); // Reseta a cobra para o tamanho inicial (apenas cabeça)
        game.increaseScore(5); // Pontuação menor por maçã verde
    }

    @Override
    public void respawn() {
        super.respawn();
    }
}

