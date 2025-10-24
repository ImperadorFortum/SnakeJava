
public class GoldenFood extends Food {
    public GoldenFood() {
        super();
    }

    @Override
    public void applyEffect(Snake snake, SnakeGame game) {
        game.activateSpeedBoost(20000); // 5 segundos de boost de velocidade
        game.increaseScore(20); // Aumenta mais a pontuação
    }

    @Override
    public void respawn() {
        super.respawn();
    }
}

