public abstract class Game {

    private Difficulty difficulty = Difficulty.NORMAL; 

    public final void start() {
        setup();
        while (!isGameOver()) {
            long inicio = System.currentTimeMillis();
            handleInput();
            update();
            render();

            long tempoPassado = System.currentTimeMillis() - inicio;
            long delay = difficulty.getDelay() - tempoPassado;

            if (delay > 0) {
                try {
                    Thread.sleep(delay);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }
        gameOver();
    }
    
   
    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }


    protected abstract void setup();
    protected abstract void handleInput();
    protected abstract void update();
    protected abstract void render();
    protected abstract boolean isGameOver();
    protected abstract void gameOver();
}