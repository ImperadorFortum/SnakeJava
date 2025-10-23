
<<<<<<< HEAD

=======
>>>>>>> 17d71ef8e30d69f281b2293c5bf3db3eaccd58c5
public class GreenFood extends Food {
    public GreenFood() {
        super();
    }

    @Override
    public void applyEffect(Snake snake, SnakeGame game) {
<<<<<<< HEAD
        snake.resetSize(); // Reseta a cobra para o tamanho inicial (apenas cabeça)
=======
        snake.shrink(2); // Diminui a cobra em 2 segmentos
>>>>>>> 17d71ef8e30d69f281b2293c5bf3db3eaccd58c5
        game.increaseScore(5); // Pontuação menor por maçã verde
    }

    @Override
    public void respawn() {
        super.respawn();
    }
}

