public enum Difficulty {
    FACIL(100),
    NORMAL(50),
    MEDIO(20),
    DIFICIL(5);

    private final int delay;

    Difficulty(int delay) {
        this.delay = delay;
    }

    public int getDelay() {
        return delay;
    }
}