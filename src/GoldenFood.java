
<<<<<<< HEAD

=======
>>>>>>> 17d71ef8e30d69f281b2293c5bf3db3eaccd58c5
public class GoldenFood extends Food {
    public GoldenFood() {
        super();
    }

    @Override
    public void applyEffect(Snake snake, SnakeGame game) {
        game.activateSpeedBoost(5000); // 5 segundos de boost de velocidade
        game.increaseScore(20); // Aumenta mais a pontuação
    }

    @Override
    public void respawn() {
        super.respawn();
    }
}

