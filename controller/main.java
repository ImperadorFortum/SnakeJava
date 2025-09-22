public class Main {
    public static void main(String[] args) {
        Renderer renderer = new SwingRenderer();
        Game game = new SnakeGame(renderer);
        game.start();
    }
}