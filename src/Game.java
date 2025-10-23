<<<<<<< HEAD

=======
>>>>>>> 17d71ef8e30d69f281b2293c5bf3db3eaccd58c5

public abstract class Game {
    public final void start() {
        setup();
        while (!isGameOver()) {
            handleInput();
            update();
            render();
        }
        gameOver();
    }

    protected abstract void setup();
    protected abstract void handleInput();
    protected abstract void update();
    protected abstract void render();
    protected abstract boolean isGameOver();
    protected abstract void gameOver();
}

