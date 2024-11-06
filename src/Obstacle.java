/**
 * Obstacle class that blocks the player's path
 */
public class Obstacle extends Entity {
    public Obstacle(String name, int hp) {
        super(name, hp, 0);  // Obstacles don't have attack power
    }
}
