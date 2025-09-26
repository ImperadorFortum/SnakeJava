public class SwingRenderer implements Renderer {
    public void draw(Snake snake, Food food) {
        // Exemplo simples: imprime posições no console
        System.out.println("Cobra: " + snake.getHead());
        System.out.println("Comida: " + food.getPosition());
    }
}